package com.ebrightmoon.retrofitrx.retrofit;


import android.content.Context;

import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.callback.UCallback;
import com.ebrightmoon.retrofitrx.common.AppConfig;
import com.ebrightmoon.retrofitrx.common.HttpConfig;
import com.ebrightmoon.retrofitrx.common.HttpUtils;
import com.ebrightmoon.retrofitrx.core.ApiCache;
import com.ebrightmoon.retrofitrx.core.ApiCookie;
import com.ebrightmoon.retrofitrx.core.ApiTransformer;
import com.ebrightmoon.retrofitrx.func.ApiFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.interceptor.HeadersInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.OfflineCacheInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.OnlineCacheInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.UploadProgressInterceptor;
import com.ebrightmoon.retrofitrx.mode.ApiHost;
import com.ebrightmoon.retrofitrx.mode.CacheMode;
import com.ebrightmoon.retrofitrx.mode.CacheResult;
import com.ebrightmoon.retrofitrx.mode.HttpHeaders;
import com.ebrightmoon.retrofitrx.mode.MediaTypes;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by wyy on 2017/9/6.
 */

public class HttpClient<R extends HttpClient> {
    protected static  Context mContext;
    protected static OkHttpClient.Builder okHttpBuilder;
    protected static Retrofit retrofit;
    protected static Retrofit.Builder retrofitBuilder;
    protected static ApiCache.Builder apiCacheBuilder;
    protected static OkHttpClient okHttpClient;
    protected String suffixUrl = "";//链接后缀
    protected List<Interceptor> interceptors = new ArrayList<>();//局部请求的拦截器
    protected List<Interceptor> networkInterceptors = new ArrayList<>();//局部请求的网络拦截器
    protected HttpHeaders headers = new HttpHeaders();//请求头
    protected HttpConfig httpConfig;//全局配置
    protected   String baseUrl = AppConfig.BASE_URL;
    protected long readTimeOut;//读取超时时间
    protected long writeTimeOut;//写入超时时间
    protected long connectTimeOut;//连接超时时间
    protected boolean isHttpCache;//是否使用Http缓存
    protected UCallback uploadCallback;//上传进度回调
    protected ApiService apiService;//通用接口服务
    protected int retryDelayMillis;//请求失败重试间隔时间
    protected int retryCount;//重试次数
    protected boolean isLocalCache;//是否使用本地缓存
    protected CacheMode cacheMode;//本地缓存类型
    protected String cacheKey;//本地缓存Key
    protected long cacheTime;//本地缓存时间
    protected Map<String, String> params = new LinkedHashMap<>();//请求参数
    protected Map<String, Object> forms = new LinkedHashMap<>();
    protected StringBuilder stringBuilder = new StringBuilder();
    protected RequestBody requestBody;
    protected MediaType mediaType;
    protected String content;

    private HttpClient(Context context, String suffixUrl, Map<String, String> headers) {
        okHttpBuilder = new OkHttpClient.Builder();
        retrofitBuilder = new Retrofit.Builder();
        if (mContext == null && context != null) {
            mContext = mContext.getApplicationContext();
            apiCacheBuilder = new ApiCache.Builder(context);
        }

    }
    private HttpClient() {
        this(null, AppConfig.BASE_URL, null);
    }

    private HttpClient(Context context) {
        this(context, AppConfig.BASE_URL, null);
    }

    private HttpClient(Context context, String url) {
        this(context, url, null);
    }

    private static HttpClient instance;

    public static HttpClient getInstance(Context context) {
        if (instance == null) {
            synchronized (AppClient.class) {
                if (instance == null) {
                    instance = new HttpClient(context);
                }
            }
        }
        return instance;
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }


    public static HttpClient getInstance(Context context, String url) {
        if (context == null) {
            mContext = context;
        }
        return instance = new HttpClient(context, url);
    }

    public static HttpClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context == null) {
            mContext = context;
        }
        return instance = new HttpClient(context, url, headers);
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

    protected <T> Observable<T> execute(Type type) {
        return apiService.get(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(apiCacheBuilder.build().<T>transformer(cacheMode, type));
    }

    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
//        if (super.tag != null) {
//            ApiManager.get().add(super.tag, disposableObserver);
//        }
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }


    public <T> T execute(Observable<T> observable, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        observable.compose(ApiTransformer.<T>norTransformer())
                .subscribe(disposableObserver);
        return null;
    }

    protected <T> Observable<T> executePost(Type type) {
        if (stringBuilder.length() > 0) {
            suffixUrl = suffixUrl + stringBuilder.toString();
        }
        if (forms != null && forms.size() > 0) {
            if (params != null && params.size() > 0) {
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                Map.Entry<String, String> entry;
                while (entryIterator.hasNext()) {
                    entry = entryIterator.next();
                    if (entry != null) {
                        forms.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return apiService.postForm(suffixUrl, forms).compose(this.<T>norTransformer(type));
        }
        if (requestBody != null) {
            return apiService.postBody(suffixUrl, requestBody).compose(this.<T>norTransformer(type));
        }
        if (content != null && mediaType != null) {
            requestBody = RequestBody.create(mediaType, content);
            return apiService.postBody(suffixUrl, requestBody).compose(this.<T>norTransformer(type));
        }
        return apiService.post(suffixUrl, params).compose(this.<T>norTransformer(type));
    }






    public HttpClient addUrlParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append("?");
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(paramKey).append("=").append(paramValue);
        }
        return this;
    }

    public HttpClient addForm(String formKey, Object formValue) {
        if (formKey != null && formValue != null) {
            forms.put(formKey, formValue);
        }
        return this;
    }

    public HttpClient setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public HttpClient setString(String string) {
        this.content = string;
        this.mediaType = MediaTypes.TEXT_PLAIN_TYPE;
        return this;
    }

    public HttpClient setString(String string, MediaType mediaType) {
        this.content = string;
        this.mediaType = mediaType;
        return this;
    }

    public HttpClient setJson(String json) {
        this.content = json;
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public HttpClient setJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public HttpClient setJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }


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

    /**
     * 初始化局部参数
     */
    public void initLocalConfig() {
        if (httpConfig.getGlobalHeaders() != null) {
            headers.put(httpConfig.getGlobalHeaders());
        }
        if (!interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                okHttpBuilder.addInterceptor(interceptor);
            }
        }

        if (!networkInterceptors.isEmpty()) {
            for (Interceptor interceptor : networkInterceptors) {
                okHttpBuilder.addNetworkInterceptor(interceptor);
            }
        }

        if (headers.headersMap.size() > 0) {
            okHttpBuilder.addInterceptor(new HeadersInterceptor(headers.headersMap));
        }

        if (uploadCallback != null) {
            okHttpBuilder.addNetworkInterceptor(new UploadProgressInterceptor(uploadCallback));
        }

        if (readTimeOut > 0) {
            okHttpBuilder.readTimeout(readTimeOut, TimeUnit.SECONDS);
        }

        if (writeTimeOut > 0) {
            okHttpBuilder.readTimeout(writeTimeOut, TimeUnit.SECONDS);
        }

        if (connectTimeOut > 0) {
            okHttpBuilder.readTimeout(connectTimeOut, TimeUnit.SECONDS);
        }

        if (isHttpCache) {
            try {
                if (httpConfig.getHttpCache() == null) {
                    httpConfig.httpCache(new Cache(httpConfig.getHttpCacheDirectory(), HttpUtils.CACHE_MAX_SIZE));
                }
                okHttpBuilder.addNetworkInterceptor(new OnlineCacheInterceptor());
                if (mContext != null) {
                    okHttpBuilder.addNetworkInterceptor(new OfflineCacheInterceptor(mContext));
                }
            } catch (Exception e) {
            }
            okHttpBuilder.cache(httpConfig.getHttpCache());
        }

        if (baseUrl != null) {
            Retrofit.Builder newRetrofitBuilder = new Retrofit.Builder();
            newRetrofitBuilder.baseUrl(baseUrl);
            if (httpConfig.getConverterFactory() != null) {
                newRetrofitBuilder.addConverterFactory(httpConfig.getConverterFactory());
            }
            if (httpConfig.getCallAdapterFactory() != null) {
                newRetrofitBuilder.addCallAdapterFactory(httpConfig.getCallAdapterFactory());
            }
            if (httpConfig.getCallFactory() != null) {
                newRetrofitBuilder.callFactory(httpConfig.getCallFactory());
            }
            okHttpBuilder.hostnameVerifier(new SSL.UnSafeHostnameVerifier(baseUrl));
            newRetrofitBuilder.client(okHttpBuilder.build());
            retrofit = newRetrofitBuilder.build();
        } else {
            retrofitBuilder.client(okHttpBuilder.build());
            retrofit = retrofitBuilder.build();
        }


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

    /**
     * 初始化全局参数
     */
    public void initGlobalConfig() {
        httpConfig = HttpConfig.getInstance();

        if (httpConfig.getBaseUrl() == null) {
            httpConfig.baseUrl(ApiHost.getHost());
        }
        retrofitBuilder.baseUrl(httpConfig.getBaseUrl());

        if (httpConfig.getConverterFactory() == null) {
            httpConfig.converterFactory(GsonConverterFactory.create());
        }
        retrofitBuilder.addConverterFactory(httpConfig.getConverterFactory());

        if (httpConfig.getCallAdapterFactory() == null) {
            httpConfig.callAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        retrofitBuilder.addCallAdapterFactory(httpConfig.getCallAdapterFactory());

        if (httpConfig.getCallFactory() != null) {
            retrofitBuilder.callFactory(httpConfig.getCallFactory());
        }

        if (httpConfig.getHostnameVerifier() == null) {
            httpConfig.hostnameVerifier(new SSL.UnSafeHostnameVerifier(httpConfig.getBaseUrl()));
        }
        okHttpBuilder.hostnameVerifier(httpConfig.getHostnameVerifier());

        if (httpConfig.getSslSocketFactory() == null) {
            httpConfig.SSLSocketFactory(SSL.getSslSocketFactory(null, null, null));
        }
        okHttpBuilder.sslSocketFactory(httpConfig.getSslSocketFactory());

        if (httpConfig.getConnectionPool() == null) {
            httpConfig.connectionPool(new ConnectionPool(HttpUtils.DEFAULT_MAX_IDLE_CONNECTIONS,
                    HttpUtils.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS));
        }
        okHttpBuilder.connectionPool(httpConfig.getConnectionPool());

        if (httpConfig.isCookie() && httpConfig.getApiCookie() == null) {
            httpConfig.apiCookie(new ApiCookie(mContext));
        }
        if (httpConfig.isCookie()) {
            okHttpBuilder.cookieJar(httpConfig.getApiCookie());
        }

        if (httpConfig.getHttpCacheDirectory() == null) {
            httpConfig.setHttpCacheDirectory(new File(mContext.getCacheDir(), HttpUtils.CACHE_HTTP_DIR));
        }
        if (httpConfig.isHttpCache()) {
            try {
                if (httpConfig.getHttpCache() == null) {
                    httpConfig.httpCache(new Cache(httpConfig.getHttpCacheDirectory(), HttpUtils.CACHE_MAX_SIZE));
                }
                okHttpBuilder.addNetworkInterceptor(new OnlineCacheInterceptor());
                if (mContext != null)
                    okHttpBuilder.addNetworkInterceptor(new OfflineCacheInterceptor(mContext));
            } catch (Exception e) {
            }
        }
        if (httpConfig.getHttpCache() != null) {
            okHttpBuilder.cache(httpConfig.getHttpCache());
        }
        okHttpBuilder.connectTimeout(HttpUtils.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(HttpUtils.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(HttpUtils.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 设置基础域名，当前请求会替换全局域名
     *
     * @param baseUrl
     * @return
     */
    public R baseUrl(String baseUrl) {
        if (baseUrl != null) {
            this.baseUrl = baseUrl;
        }
        return (R) this;
    }

    /**
     * 添加请求头
     *
     * @param headerKey
     * @param headerValue
     * @return
     */
    public R addHeader(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
        return (R) this;
    }

    /**
     * 添加请求头
     *
     * @param headers
     * @return
     */
    public R addHeaders(Map<String, String> headers) {
        this.headers.put(headers);
        return (R) this;
    }

    /**
     * 移除请求头
     *
     * @param headerKey
     * @return
     */
    public R removeHeader(String headerKey) {
        this.headers.remove(headerKey);
        return (R) this;
    }

    /**
     * 设置请求头
     *
     * @param headers
     * @return
     */
    public R headers(HttpHeaders headers) {
        if (headers != null) {
            this.headers = headers;
        }
        return (R) this;
    }


    /**
     * 设置连接超时时间（秒）
     *
     * @param connectTimeOut
     * @return
     */
    public R connectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
        return (R) this;
    }

    /**
     * 设置读取超时时间（秒）
     *
     * @param readTimeOut
     * @return
     */
    public R readTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    /**
     * 设置写入超时时间（秒）
     *
     * @param writeTimeOut
     * @return
     */
    public R writeTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    /**
     * 设置是否进行HTTP缓存
     *
     * @param isHttpCache
     * @return
     */
    public R setHttpCache(boolean isHttpCache) {
        this.isHttpCache = isHttpCache;
        return (R) this;
    }

    /**
     * 局部设置拦截器
     *
     * @param interceptor
     * @return
     */
    public R interceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
        return (R) this;
    }

    /**
     * 局部设置网络拦截器
     *
     * @param interceptor
     * @return
     */
    public R networkInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            networkInterceptors.add(interceptor);
        }
        return (R) this;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public long getWriteTimeOut() {
        return writeTimeOut;
    }

    public long getConnectTimeOut() {
        return connectTimeOut;
    }

    public boolean isHttpCache() {
        return isHttpCache;
    }

    /**
     * 获取第一级type
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 获取次一级type(如果有)
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getSubType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            if (type instanceof ParameterizedType) {
                finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                finalNeedType = type;
            }
        }
        return finalNeedType;
    }

}
