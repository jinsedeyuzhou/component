package com.ebrightmoon.retrofitrx.retrofit;


import android.content.Context;

import com.ebrightmoon.retrofitrx.callback.UCallback;
import com.ebrightmoon.retrofitrx.common.AppConfig;
import com.ebrightmoon.retrofitrx.common.HttpConfig;
import com.ebrightmoon.retrofitrx.common.HttpUtils;
import com.ebrightmoon.retrofitrx.core.ApiCache;
import com.ebrightmoon.retrofitrx.core.ApiCookie;
import com.ebrightmoon.retrofitrx.interceptor.HeadersInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.OfflineCacheInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.OnlineCacheInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.UploadProgressInterceptor;
import com.ebrightmoon.retrofitrx.mode.ApiHost;
import com.ebrightmoon.retrofitrx.mode.HttpHeaders;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by wyy on 2017/9/6.
 */

public abstract class BaseRequest<R extends BaseRequest> {
    protected Context mContext;
    protected static OkHttpClient.Builder okHttpBuilder;
    protected static Retrofit retrofit;
    protected static Retrofit.Builder retrofitBuilder;
    protected static ApiCache.Builder apiCacheBuilder;
    protected static OkHttpClient okHttpClient;
    protected List<Interceptor> interceptors = new ArrayList<>();//局部请求的拦截器
    protected List<Interceptor> networkInterceptors = new ArrayList<>();//局部请求的网络拦截器
    protected HttpHeaders headers = new HttpHeaders();//请求头
    protected HttpConfig httpConfig;//全局配置
    protected String baseUrl = AppConfig.BASE_URL;
    protected long readTimeOut;//读取超时时间
    protected long writeTimeOut;//写入超时时间
    protected long connectTimeOut;//连接超时时间
    protected boolean isHttpCache;//是否使用Http缓存
    protected UCallback uploadCallback;//上传进度回调

    protected BaseRequest(Context context) {
        if (mContext == null && context != null) {
            mContext = mContext.getApplicationContext();
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
            apiCacheBuilder = new ApiCache.Builder(context);
        }
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
