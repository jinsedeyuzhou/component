package com.ebrightmoon.retrofitrx.common;


import com.ebrightmoon.retrofitrx.core.ApiCookie;
import com.ebrightmoon.retrofitrx.interceptor.GzipRequestInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.OfflineCacheInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.OnlineCacheInterceptor;
import com.ebrightmoon.retrofitrx.mode.ApiHost;

import java.io.File;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Call.Factory;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * @Description: 请求全局配置
 */
public class HttpConfig {
    private CallAdapter.Factory callAdapterFactory;//Call适配器工厂
    private Converter.Factory converterFactory;//转换工厂
    private Factory callFactory;//Call工厂
    private SSLSocketFactory sslSocketFactory;//SSL工厂
    private HostnameVerifier hostnameVerifier;//主机域名验证
    private ConnectionPool connectionPool;//连接池
    private Map<String, String> globalParams = new LinkedHashMap<>();//请求参数
    private Map<String, String> globalHeaders = new LinkedHashMap<>();//请求头
    private boolean isHttpCache;//是否使用Http缓存
    private File httpCacheDirectory;//Http缓存路径
    private Cache httpCache;//Http缓存对象
    private boolean isCookie;//是否使用Cookie
    private ApiCookie apiCookie;//Cookie配置
    private String baseUrl;//基础域名
    private int retryDelayMillis;//请求失败重试间隔时间
    private int retryCount;//请求失败重试次数
    private Proxy proxy;

    private static HttpConfig instance;

    private HttpConfig() {
    }

    public static HttpConfig getInstance() {
        if (instance == null) {
            synchronized (HttpConfig.class) {
                if (instance == null) {
                    instance = new HttpConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 设置CallAdapter工厂
     *
     * @param factory
     * @return
     */
    public HttpConfig callAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    /**
     * 设置转换工厂
     *
     * @param factory
     * @return
     */
    public HttpConfig converterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    /**
     * 设置Call的工厂
     *
     * @param factory
     * @return
     */
    public HttpConfig callFactory(Factory factory) {
        this.callFactory = checkNotNull(factory, "factory == null");
        return this;
    }

    /**
     * 设置SSL工厂
     *
     * @param sslSocketFactory
     * @return
     */
    public HttpConfig SSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    /**
     * 设置主机验证机制
     *
     * @param hostnameVerifier
     * @return
     */
    public HttpConfig hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    /**
     * 设置连接池
     *
     * @param connectionPool
     * @return
     */
    public HttpConfig connectionPool(ConnectionPool connectionPool) {
        this.connectionPool = checkNotNull(connectionPool, "connectionPool == null");
        return this;
    }

    /**
     * 设置请求头部
     *
     * @param globalHeaders
     * @return
     */
    public HttpConfig globalHeaders(Map<String, String> globalHeaders) {
        if (globalHeaders != null) {
            this.globalHeaders = globalHeaders;
        }
        return this;
    }

    /**
     * 设置请求参数
     *
     * @param globalParams
     * @return
     */
    public HttpConfig globalParams(Map<String, String> globalParams) {
        if (globalParams != null) {
            this.globalParams = globalParams;
        }
        return this;
    }

    /**
     * 设置是否添加HTTP缓存
     *
     * @param isHttpCache
     * @return
     */
    public HttpConfig setHttpCache(boolean isHttpCache) {
        this.isHttpCache = isHttpCache;
        return this;
    }

    /**
     * 设置HTTP缓存路径
     *
     * @param httpCacheDirectory
     * @return
     */
    public HttpConfig setHttpCacheDirectory(File httpCacheDirectory) {
        this.httpCacheDirectory = httpCacheDirectory;
        return this;
    }

    /**
     * 设置HTTP缓存
     *
     * @param httpCache
     * @return
     */
    public HttpConfig httpCache(Cache httpCache) {
        this.httpCache = httpCache;
        return this;
    }

    /**
     * 设置是否添加Cookie
     *
     * @param isCookie
     * @return
     */
    public HttpConfig setCookie(boolean isCookie) {
        this.isCookie = isCookie;
        return this;
    }

    /**
     * 设置Cookie管理
     *
     * @param cookie
     * @return
     */
    public HttpConfig apiCookie(ApiCookie cookie) {
        this.apiCookie = checkNotNull(cookie, "cookieManager == null");
        return this;
    }

    /**
     * 设置请求baseUrl
     *
     * @param baseUrl
     * @return
     */
    public HttpConfig baseUrl(String baseUrl) {
        this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
        ApiHost.setHost(this.baseUrl);
        return this;
    }

    /**
     * 设置请求失败重试间隔时间
     *
     * @param retryDelayMillis
     * @return
     */
    public HttpConfig retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return this;
    }

    /**
     * 设置请求失败重试次数
     *
     * @param retryCount
     * @return
     */
    public HttpConfig retryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    /**
     * 设置代理
     *
     * @param proxy
     * @return
     */
    public HttpConfig proxy(Proxy proxy) {
        this.proxy=proxy;
        return this;
    }

    public Proxy getProxy() {
        return proxy;
    }


    /**
     * 设置连接超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpConfig connectTimeout(int timeout) {
        return connectTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置读取超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpConfig readTimeout(int timeout) {
        return readTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置写入超时时间（秒）
     *
     * @param timeout
     * @return
     */
    public HttpConfig writeTimeout(int timeout) {
        return writeTimeout(timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置连接超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpConfig connectTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
//            ViseHttp.getOkHttpBuilder().connectTimeout(timeout, unit);
        } else {
//            ViseHttp.getOkHttpBuilder().connectTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpConfig writeTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
//            ViseHttp.getOkHttpBuilder().writeTimeout(timeout, unit);
        } else {
//            ViseHttp.getOkHttpBuilder().writeTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     *
     * @param timeout
     * @param unit
     * @return
     */
    public HttpConfig readTimeout(int timeout, TimeUnit unit) {
        if (timeout > -1) {
//            ViseHttp.getOkHttpBuilder().readTimeout(timeout, unit);
        } else {
//            ViseHttp.getOkHttpBuilder().readTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }




    /**
     * 使用POST方式是否需要进行GZIP压缩，服务器不支持则不设置
     *
     * @return
     */
    public HttpConfig postGzipInterceptor() {
//        interceptor(new GzipRequestInterceptor());
        return this;
    }







    public CallAdapter.Factory getCallAdapterFactory() {
        return callAdapterFactory;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public Factory getCallFactory() {
        return callFactory;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public Map<String, String> getGlobalParams() {
        return globalParams;
    }

    public Map<String, String> getGlobalHeaders() {
        return globalHeaders;
    }

    public boolean isHttpCache() {
        return isHttpCache;
    }

    public boolean isCookie() {
        return isCookie;
    }

    public ApiCookie getApiCookie() {
        return apiCookie;
    }

    public Cache getHttpCache() {
        return httpCache;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getRetryDelayMillis() {
        if (retryDelayMillis < 0) {
            retryDelayMillis = HttpUtils.DEFAULT_RETRY_DELAY_MILLIS;
        }
        return retryDelayMillis;
    }

    public int getRetryCount() {
        if (retryCount < 0) {
            retryCount = HttpUtils.DEFAULT_RETRY_COUNT;
        }
        return retryCount;
    }

    public File getHttpCacheDirectory() {
        return httpCacheDirectory;
    }

    private <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
