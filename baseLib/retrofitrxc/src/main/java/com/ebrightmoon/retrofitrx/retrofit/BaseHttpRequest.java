package com.ebrightmoon.retrofitrx.retrofit;

import android.content.Context;

import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.common.HttpUtils;
import com.ebrightmoon.retrofitrx.func.ApiFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.mode.ApiHost;
import com.ebrightmoon.retrofitrx.mode.CacheMode;
import com.ebrightmoon.retrofitrx.mode.CacheResult;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 作者：create by  Administrator on 2019/1/18
 * 邮箱：2315813288@qq.com
 */
public  abstract class BaseHttpRequest<R extends BaseHttpRequest> extends BaseRequest<R>{
    protected ApiService apiService;//通用接口服务
    protected String suffixUrl = "";//链接后缀
    protected int retryDelayMillis;//请求失败重试间隔时间
    protected int retryCount;//重试次数
    protected boolean isLocalCache;//是否使用本地缓存
    protected CacheMode cacheMode;//本地缓存类型
    protected String cacheKey;//本地缓存Key
    protected long cacheTime;//本地缓存时间
    protected Map<String, String> params = new LinkedHashMap<>();//请求参数

    protected BaseHttpRequest(Context context) {
        super(context);

    }

    public BaseHttpRequest(Context context, String suffixUrl) {
        super(context);
        this.suffixUrl = suffixUrl;
    }


    public <T> Observable<T> request(Type type) {
        initGlobalConfig();
        initLocalConfig();
        return execute(type);
    }

    public <T> Observable<CacheResult<T>> cacheRequest(Type type) {
        initGlobalConfig();
        initLocalConfig();
        return cacheExecute(type);
    }

    public <T> void request(ACallback<T> callback) {
        initGlobalConfig();
        initLocalConfig();
        execute(callback);
    }

    @Override
    public void initLocalConfig() {
        super.initLocalConfig();
        if (httpConfig.getGlobalParams() != null) {
            params.putAll(httpConfig.getGlobalParams());
        }
        if (retryCount <= 0) {
            retryCount = httpConfig.getRetryCount();
        }
        if (retryDelayMillis <= 0) {
            retryDelayMillis = httpConfig.getRetryDelayMillis();
        }
        if (isLocalCache) {
            if (cacheKey != null) {
                apiCacheBuilder.cacheKey(cacheKey);
            } else {
                apiCacheBuilder.cacheKey(ApiHost.getHost());
            }
            if (cacheTime > 0) {
                apiCacheBuilder.cacheTime(cacheTime);
            } else {
                apiCacheBuilder.cacheTime(HttpUtils.CACHE_NEVER_EXPIRE);
            }
        }
        if (baseUrl != null && isLocalCache && cacheKey == null) {
            apiCacheBuilder.cacheKey(baseUrl);
        }
        apiService = retrofit.create(ApiService.class);
    }

    protected abstract <T> Observable<T> execute(Type type);

    protected abstract <T> Observable<CacheResult<T>> cacheExecute(Type type);

    protected abstract <T> void execute(ACallback<T> callback);

    protected <T> ObservableTransformer<ResponseBody, T> norTransformer(final Type type) {
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .map(new ApiFunc<T>(type))
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }

    /**
     * 添加请求参数
     *
     * @param paramKey
     * @param paramValue
     * @return
     */
    public R addParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            this.params.put(paramKey, paramValue);
        }
        return (R) this;
    }

    /**
     * 添加请求参数
     *
     * @param params
     * @return
     */
    public R addParams(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
        return (R) this;
    }

    /**
     * 移除请求参数
     *
     * @param paramKey
     * @return
     */
    public R removeParam(String paramKey) {
        if (paramKey != null) {
            this.params.remove(paramKey);
        }
        return (R) this;
    }

    /**
     * 设置请求参数
     *
     * @param params
     * @return
     */
    public R params(Map<String, String> params) {
        if (params != null) {
            this.params = params;
        }
        return (R) this;
    }

    /**
     * 设置请求失败重试间隔时间（毫秒）
     *
     * @param retryDelayMillis
     * @return
     */
    public R retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return (R) this;
    }

    /**
     * 设置请求失败重试次数
     *
     * @param retryCount
     * @return
     */
    public R retryCount(int retryCount) {
        this.retryCount = retryCount;
        return (R) this;
    }

    /**
     * 设置是否进行本地缓存
     *
     * @param isLocalCache
     * @return
     */
    public R setLocalCache(boolean isLocalCache) {
        this.isLocalCache = isLocalCache;
        return (R) this;
    }

    /**
     * 设置本地缓存类型
     *
     * @param cacheMode
     * @return
     */
    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (R) this;
    }

    /**
     * 设置本地缓存Key
     *
     * @param cacheKey
     * @return
     */
    public R cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return (R) this;
    }

    /**
     * 设置本地缓存时间(毫秒)，默认永久
     *
     * @param cacheTime
     * @return
     */
    public R cacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
        return (R) this;
    }

    public String getSuffixUrl() {
        return suffixUrl;
    }

    public int getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public boolean isLocalCache() {
        return isLocalCache;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
