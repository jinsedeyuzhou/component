package com.ebrightmoon.retrofitrx.common;

import com.ebrightmoon.retrofitrx.callback.UCallback;
import com.ebrightmoon.retrofitrx.mode.MediaTypes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestOptions<R extends RequestOptions> {

    protected List<Interceptor> interceptors = new ArrayList<>();//局部请求的拦截器
    protected List<Interceptor> networkInterceptors = new ArrayList<>();//局部请求的网络拦截器
    protected String baseUrl;//基础域名
    protected Object tag;//请求标签
    protected long readTimeOut;//读取超时时间
    protected long writeTimeOut;//写入超时时间
    protected long connectTimeOut;//连接超时时间
    protected boolean isHttpCache;//是否使用Http缓存
    protected UCallback uploadCallback;//上传进度回调

    protected String suffixUrl = "";//链接后缀
    protected int retryDelayMillis;//请求失败重试间隔时间
    protected int retryCount;//重试次数
    protected boolean isLocalCache;//是否使用本地缓存
    protected String cacheKey;//本地缓存Key
    protected long cacheTime;//本地缓存时间
    protected Map<String, String> params = new LinkedHashMap<>();//请求参数

    protected Map<String, Object> forms = new LinkedHashMap<>();
    protected StringBuilder stringBuilder = new StringBuilder();
    protected RequestBody requestBody;
    protected MediaType mediaType;
    protected String content;

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
     * 设置请求标签
     *
     * @param tag
     * @return
     */
    public R tag(Object tag) {
        this.tag = tag;
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

    public RequestOptions<R> addUrlParam(String paramKey, String paramValue) {
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

    public RequestOptions<R> addForm(String formKey, Object formValue) {
        if (formKey != null && formValue != null) {
            forms.put(formKey, formValue);
        }
        return this;
    }

    public RequestOptions<R> setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public RequestOptions<R> setString(String string) {
        this.content = string;
        this.mediaType = MediaTypes.TEXT_PLAIN_TYPE;
        return this;
    }

    public RequestOptions<R> setString(String string, MediaType mediaType) {
        this.content = string;
        this.mediaType = mediaType;
        return this;
    }

    public RequestOptions<R> setJson(String json) {
        this.content = json;
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public RequestOptions<R> setJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public RequestOptions<R> setJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }
}
