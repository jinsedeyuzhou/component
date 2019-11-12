package com.ebrightmoon.retrofitrx.retrofit;


import android.content.Context;
import android.text.TextUtils;

import com.ebrightmoon.retrofitrx.body.UploadProgressRequestBody;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.callback.UCallback;
import com.ebrightmoon.retrofitrx.common.AppConfig;
import com.ebrightmoon.retrofitrx.common.HttpUtils;
import com.ebrightmoon.retrofitrx.convert.GsonConverterFactory;
import com.ebrightmoon.retrofitrx.core.ApiManager;
import com.ebrightmoon.retrofitrx.core.ApiTransformer;
import com.ebrightmoon.retrofitrx.func.ApiDownloadFunc;
import com.ebrightmoon.retrofitrx.func.ApiResultFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.interceptor.HeadersInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.HttpResponseInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.LoggingInterceptor;
import com.ebrightmoon.retrofitrx.mode.MediaTypes;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;
import com.ebrightmoon.retrofitrx.subscriber.DownCallbackSubscriber;
import com.ebrightmoon.retrofitrx.temp.SSLUtils;
import com.ebrightmoon.retrofitrx.util.GsonUtil;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by wyy on 2017/9/6.
 * 利用Recycle 管理生命周期  利用返回DisposableObserver 和RxApiManager，取消网络请求
 */

public class HttpClient {
    private List<MultipartBody.Part> multipartBodyParts;
    private Map<String, RequestBody> params;
    private static Context mContext;
    private final int DEFAULT_TIMEOUT = 5;
    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit retrofit;
    private ApiService apiService;
    private Retrofit.Builder retrofitBuilder;
    public static String baseUrl = AppConfig.BASE_URL;

    private HttpClient(Context context, String url, Map<String, String> headers) {
        if (context != null)
            mContext = context;
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder
                .addNetworkInterceptor(new LoggingInterceptor())
                .addNetworkInterceptor(new HttpResponseInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

//        headers.put("","");  设置全局header
        if (headers != null)
            okHttpBuilder.addInterceptor(new HeadersInterceptor(headers));

        if (url != null)
            baseUrl = url;
        okHttpBuilder.connectionPool(new ConnectionPool(HttpUtils.DEFAULT_MAX_IDLE_CONNECTIONS,
                HttpUtils.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS));
//        okHttpBuilder.hostnameVerifier(new SSL.UnSafeHostnameVerifier(baseUrl));
//        okHttpBuilder.sslSocketFactory(SSL.getSslSocketFactory(null,null,null));
//        okHttpBuilder.sslSocketFactory(SSL.getSSlFactory(mContext,"server.cer"));
//        okHttpBuilder.sslSocketFactory(new SSLUtils(trustAllCert), trustAllCert);
        okHttpBuilder.certificatePinner(new CertificatePinner.Builder()
                .add("com.ebrightmoon", "sha256/AqUGPVqg5Rdcq3cLU4yXtC+BsbsvFcVFcPNJA13AUIA=")
                .add("com.ebrightmoon", "sha256/zUIraRNo+4JoAYA7ROeWjARtIoN4rIEbCpfCRQT6N6A=")
                .build());
        okHttpBuilder.sslSocketFactory(new SSLUtils(trustAllCert), trustAllCert);

//        设置代理
//        okHttpBuilder .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.185", 82)));
//        okHttpBuilder.dns(new OkHttpDns());

        retrofitBuilder = new Retrofit.Builder();

        retrofit = retrofitBuilder
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpBuilder.build())
                .build();


    }


    //定义一个信任所有证书的TrustManager
    final X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };


    private static HttpClient instance;

    public static HttpClient getInstance(Context context) {
        if (instance == null) {
            synchronized (HttpClient.class) {
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

    private HttpClient() {
        this(null, baseUrl, null);
    }

    private HttpClient(Context context) {

        this(context, baseUrl, null);
    }

    private HttpClient(Context context, String url) {

        this(context, url, null);
    }

    public OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpBuilder;
    }

    public void setOkHttpBuilder(OkHttpClient.Builder okHttpBuilder) {
        this.okHttpBuilder = okHttpBuilder;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }

    public void setRetrofitBuilder(Retrofit.Builder retrofitBuilder) {
        this.retrofitBuilder = retrofitBuilder;
    }

    public ApiService CreateApiService() {
        return apiService = create(ApiService.class);
    }


    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


    /**
     * Get 返回数据  无模型无校验
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> HttpClient get(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().get(url, params)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
//                .compose(RxHelper.<T>bindUntilEvent(recycle))
                .subscribe(disposableObserver);

        return this;
    }

    /**
     * Get 返回数据  无模型无校验
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver getd(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().get(url, params)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
//                .compose(RxHelper.<T>bindUntilEvent(recycle))
                .subscribe(disposableObserver);
        return disposableObserver;
    }

    /**
     * Post 返回数据  无模型无校验
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver post(String url, Map<String, String> params, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaTypes.APPLICATION_JSON_TYPE, GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().post(url, body)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);

        return disposableObserver;
    }

    /**
     * Json 返回数据  无模型无校验
     *
     * @param url
     * @param jsonObject
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver json(String url, String jsonObject, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaTypes.APPLICATION_JSON_TYPE, jsonObject);
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().post(url, body)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);
        return disposableObserver;
    }


    /**
     * Api通用Get  返回模型中data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver executeGet(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().executeGet(url, params, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

        return disposableObserver;

    }

    /**
     * Api通用post  返回模型中T  data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver executePost(String url, Map<String, String> params, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaTypes.APPLICATION_JSON_TYPE, GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().executePost(url, params, body)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

        return disposableObserver;
    }

    public <T> T execute(Observable<T> observable, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        observable.compose(ApiTransformer.<T>norTransformer())
                .subscribe(disposableObserver);
        return null;
    }


    /**
     * Api通用put  返回模型中 T data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver executePut(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().executePut(url, params, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

        return disposableObserver;
    }


    /**
     * Api通用Get  返回ResponseResult数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver getResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().executeGet(url, params, params)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);
        return disposableObserver;
    }

    /**
     * Api通用post  返回ResponseResult数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver postResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {

        RequestBody body = RequestBody.create(MediaTypes.APPLICATION_JSON_TYPE, GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().executePost(url, HttpUtils.excute(params), body)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);

        return disposableObserver;
    }

    /**
     * Api通用put  返回ResponseResult  数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> DisposableObserver putResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().executePut(url, params, params)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);

        return disposableObserver;
    }


    /**
     * 上传文件不带参数校验 返回数据 可以控制文件上传进度
     *
     * @param url
     * @param <T>
     */
    public <T> void uploadFiles(String url, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().uploadFiles(url, multipartBodyParts)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }


    /**
     * 上传文件不带参数校验  返回数据模型 ResponseResult中 T数据
     *
     * @param url
     * @param <T>
     */
    public <T> void uploadFilesV(String url, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().uploadFiles(url, multipartBodyParts)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

    }


    /**
     * 上传文件 无头文件 返回 返回ResponseResult  数据 不能监控文件上传进度
     *
     * @param url
     * @param <T>
     */
    public <T> void uploadFiles(String url, Map<String, File> files, ACallback<T> callback) {
        params = new HashMap<>();
        for (Map.Entry<String, File> entry : files.entrySet()) {
            RequestBody requestBody = RequestBody.create(MediaTypes.APPLICATION_FORM_URLENCODED_TYPE, entry.getValue());
            params.put("file\"; filename=\"" + entry.getValue() + "", requestBody);
        }
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().uploadFiles(url, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);


    }

    /**
     * 下载文件
     *
     * @param url
     * @param params
     * @param fileName
     * @param context
     * @param callback
     */
    public <T> void downloadFile(String url, Map<String, String> params, String fileName, Context context, ACallback<T> callback) {
        DisposableObserver disposableObserver = new DownCallbackSubscriber(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().downFile(url, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(new ApiDownloadFunc(context, fileName))
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .retryWhen(new ApiRetryFunc(0, 60))
                .subscribe(disposableObserver);


    }

    /**
     * 下载文件
     *
     * @param url
     * @param params
     */
    public <T> void downloadFile(String url, Map<String, String> params, Context context, ACallback<T> callback) {
        DisposableObserver disposableObserver = new DownCallbackSubscriber(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().downFile(url, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(new ApiDownloadFunc(context))
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .retryWhen(new ApiRetryFunc(0, 60))
                .subscribe(disposableObserver);


    }


    /**
     * 下载文件
     *
     * @param url
     * @param xml
     */
    public <T> void downloadFile(String url, String xml, String fileName, Context context, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaTypes.APPLICATION_XML_TYPE, xml);
        DisposableObserver disposableObserver = new DownCallbackSubscriber(callback);
        ApiManager.get().add(url, disposableObserver);
        CreateApiService().downFile(url, body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(new ApiDownloadFunc(context, fileName))
                .sample(1, TimeUnit.SECONDS)  //如果碰见小文件一秒之内下载完成会看不到进度，可以注掉也可以改变发送次数
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .retryWhen(new ApiRetryFunc(0, 60))
                .subscribe(disposableObserver);


    }


    /**
     * @param params
     * @param callback
     */
    public void getMobileCode(HashMap<String, String> params, ACallback<ResponseResult<String>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<String>>(callback);
        RequestBody body = RequestBody.create(MediaTypes.APPLICATION_JSON_TYPE, GsonUtil.gson().toJson(params));
        CreateApiService().getMobileCode(params, body)
                .compose(ApiTransformer.<ResponseResult<String>>norTransformer())
                .subscribe(disposableObserver);
    }


    /**
     * @param fileMap
     * @return
     */
    public HttpClient addFiles(Map<String, File> fileMap) {
        if (fileMap == null) {
            return this;
        }
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            addFile(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * @param key
     * @param file
     * @return
     */
    public HttpClient addFile(String key, File file) {
        return addFile(key, file, null);
    }

    /**
     * 返回ResponseResult  数据
     *
     * @param key
     * @param file
     * @param callback
     * @return
     */
    public HttpClient addFile(String key, File file, UCallback callback) {
        if (key == null || file == null) {
            return this;
        }
        if (multipartBodyParts == null) {
            multipartBodyParts = new ArrayList<>();
        }
        RequestBody requestBody = RequestBody.create(MediaTypes.APPLICATION_OCTET_STREAM_TYPE, file);
        if (callback != null) {
            UploadProgressRequestBody uploadProgressRequestBody = new UploadProgressRequestBody(requestBody, callback);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), uploadProgressRequestBody);
            multipartBodyParts.add(part);
        } else {
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            multipartBodyParts.add(part);
        }
        return this;
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

    /**
     * 全局添加参数添加参数
     *
     * @param params
     * @return
     */
    private Map<String, String> addParams(Map<String, String> params) {
        if (params == null)
            params = new HashMap<>();
        int userId = 0;
        if (userId != -1 && userId != 0) {
            params.put("UserId", userId + "");
        }
        if (!TextUtils.isEmpty("")) {
            params.put("Token", "");
        }
        return params;

    }

}
