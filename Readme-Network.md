# 网络库封装
### 1、基础请求
**Request**
```
params = new LinkedHashMap<>();
        forms = new LinkedHashMap<>();
        params.put("member_id", "1502");
        params.put("loginAccount", "hetong001");
        params.put("account_type", "10");
        params.put("device_id", "b25e8eb903401c72e0175589");
        params.put("cartId", "16126");
        params.put("os_version", "8.0.0");
        params.put("version_code", "17");
        params.put("channel", "anzhi");
        params.put("productCount", "1");
        params.put("token", "1f41c45931205bb9d8b65f945ba0d811");
        params.put("network", "wifi");
        params.put("device_brand", "Xiaomi");
        params.put("device_platform", "android");
        params.put("timestamp", System.currentTimeMillis() + "");
        forms.putAll(params);

 Request.Builder request = new Request.Builder()
                .setSuffixUrl("api/mobile/cart/updateCartCount")
                .setParams(params)
                .setHttpCache(true);

        AppClient.getInstance().get(request, new ACallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (data == null ) {
                    return;
                }
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });

```
**Post**
```
 Request.Builder request = new Request.Builder()
                .setSuffixUrl("api/mobile/cart/updateCartCount")
                .setBaseUrl("https://t3.fsyuncai.com/")
                .setForms(forms)
                .setHttpCache(true)
                ;
        AppClient.getInstance().post(request, new ACallback<String>() {

            @Override
            public void onSuccess(String data) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });
```
**Json**
```
 Request.Builder request = new Request.Builder()
                .setSuffixUrl("api/mobile/cart/getShoppingCartList")
                .setContent(json)
                ;
        AppClient.getInstance().post(request, new ACallback<String>() {

            @Override
            public void onSuccess(String data) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });
```
**download**
```
 Request.Builder request = new Request.Builder()
                .setSuffixUrl("16891/1A8EA15110A5DA113EBD2F955615C7EC.apk?fsname=com.moji.mjweather_7.0103.02_7010302.apk&csr=1bbd")
                .setBaseUrl("http://imtt.dd.qq.com/")
                .setFileName("weixin.apk")
                .setDirName("");

        upload_progress.setMax(100);
        AppClient.getInstance().download(request, new ACallback<DownProgress>() {
            @Override
            public void onSuccess(DownProgress downProgress) {
                if (downProgress == null) {
                    return;
                }
                Logger.e("down progress currentLength:" + downProgress.getDownloadSize() + ",totalLength:" + downProgress.getTotalSize());
                upload_progress.setProgress((int) (downProgress.getDownloadSize() * 100 / downProgress.getTotalSize()));
                download_progress_desc.setText(downProgress.getPercent());
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });
```
**upload**
```
 Request.Builder request = new Request.Builder()
                .setSuffixUrl("16891/1A8EA15110A5DA113EBD2F955615C7EC.apk?fsname=com.moji.mjweather_7.0103.02_7010302.apk&csr=1bbd")
                .setBaseUrl("http://imtt.dd.qq.com/")
                .setFileName("weixin.apk")
                .setDirName("");

        upload_progress.setMax(100);
        AppClient.getInstance().download(request, new ACallback<DownProgress>() {
            @Override
            public void onSuccess(DownProgress downProgress) {
                if (downProgress == null) {
                    return;
                }
                Logger.e("down progress currentLength:" + downProgress.getDownloadSize() + ",totalLength:" + downProgress.getTotalSize());
                upload_progress.setProgress((int) (downProgress.getDownloadSize() * 100 / downProgress.getTotalSize()));
                download_progress_desc.setText(downProgress.getPercent());
            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });
```

### 2、在业务模块使用
1. 新建接口类
```
public interface IHttp {
    /**
     * get 请求
     * @param suffixUrl
     * @param params
     * @param callback
     * @param <T>
     */
    <T> void get(String suffixUrl, Map<String, String> params, ACallback<T> callback);

    /**
     * post 请求
     * @param suffixUrl
     * @param params
     * @param callback
     * @param <T>
     */
    <T> void post(String suffixUrl, Map<String, String> params, ACallback<T> callback);

    /**
     * post 表单
     * @param suffixUrl
     * @param params
     * @param callback
     * @param <T>
     */
    <T> void postForm(String suffixUrl, Map<String, Object> params, ACallback<T> callback);

    /**
     * post json
     * @param suffixUrl
     * @param json
     * @param callback
     * @param <T>
     */
    <T> void postJson(String suffixUrl, String json, ACallback<T> callback);
    /**
     * post json
     * @param baseUrl
     * @param suffixUrl
     * @param json
     * @param callback
     * @param <T>
     */
    <T> void requestJson(String baseUrl,String suffixUrl, String json, ACallback<T> callback);

    /**
     * 带缓存post 请求
     * @param suffixUrl
     * @param json
     * @param callback
     * @param <T>
     */
    <T> void postCache(String suffixUrl, String json, ACallback<CacheResult<T>> callback);

    /**
     * 上传图片
     * @param suffixUrl
     * @param params
     * @param paths
     * @param uploadCallback
     * @param callback
     * @param <T>
     */
    <T> void upload(String suffixUrl, Map<String, String> params, List<String> paths, UCallback uploadCallback, ACallback<T> callback);

    /**
     * 上传文件，视频和音频，文件
     * @param suffixUrl
     * @param params
     * @param paths
     * @param uploadCallback
     * @param callback
     * @param <T>
     */
    <T> void uploadFile(String suffixUrl, Map<String, String> params, List<String> paths, UCallback uploadCallback, ACallback<T> callback);

    /**
     * 下载文件
     * @param suffixUrl
     * @param params
     * @param callback
     * @param <T>
     */
    <T> void download(String suffixUrl, Map<String, Object> params, ACallback<T> callback);
}
```
2.  实现接口类调用网络库封装
```
public class HttpRequest implements IHttp {

    /**
     * @param suffixUrl
     * @param params
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void get(String suffixUrl, Map<String, String> params, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setParams(params);
        AppClient.getInstance().get(request, callback);
    }

    /**
     * @param suffixUrl
     * @param params
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void post(String suffixUrl, Map<String, String> params, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setParams(params);
        AppClient.getInstance().post(request, callback);
    }

    /**
     * @param suffixUrl
     * @param forms
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void postForm(String suffixUrl, Map<String, Object> forms, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setForms(forms);
        AppClient.getInstance().post(request, callback);
    }

    /**
     * @param suffixUrl
     * @param json
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void postJson(String suffixUrl, String json, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setContent(json);
        AppClient.getInstance().post(request, callback);
    }


    /**
     * @param suffixUrl
     * @param suffixUrl
     * @param json
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void requestJson(String baseUrl, String suffixUrl, String json, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setBaseUrl(baseUrl)
                .setSuffixUrl(suffixUrl)
                .setContent(json);
        AppClient.getInstance().post(request, callback);
    }

    /**
     * @param suffixUrl
     * @param json
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void postCache(String suffixUrl, String json, ACallback<CacheResult<T>> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setContent(json)
                .setCacheMode(CacheMode.FIRST_CACHE)
                .setLocalCache(true)
                .setCacheTime(2000);
        AppClient.getInstance().post(request, callback);
    }

    /**
     * 上传图片
     *
     * @param suffixUrl
     * @param params
     * @param paths
     * @param uploadCallback
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void upload(String suffixUrl, Map<String, String> params, List<String> paths, UCallback uploadCallback, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setParams(params)
                .setBaseUrl(ApiConfig.BASE_UPLOAD_FILE_URL)
                .setUCallback(uploadCallback);
//        for (String path : paths) {
//            File file = new File(path);
//            if (!file.exists()) {
//                continue;
//            }
//            request.addImageFile("file", file);
//        }
        ImageUtils.compressWithRx(paths, new Observer<File>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(File file) {
                request.addImageFile("file", file);
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                AppClient.getInstance().upload(request, callback);
            }
        }, ConApp.getApp());


    }

    /**
     * 上传文件
     *
     * @param suffixUrl
     * @param params
     * @param paths
     * @param uploadCallback
     * @param callback
     * @param <T>
     */
    @Override
    public <T> void uploadFile(String suffixUrl, Map<String, String> params, List<String> paths, UCallback uploadCallback, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl)
                .setBaseUrl(ApiConfig.BASE_UPLOAD_FILE_URL)
                .setUCallback(uploadCallback);
        for (String path : paths) {
            File file = new File(path);
            if (!file.exists()) {
                continue;
            }
            request.addFile("file", file);
        }

        AppClient.getInstance().upload(request, callback);

    }


    @Override
    public <T> void download(String suffixUrl, Map<String, Object> params, ACallback<T> callback) {
        Request.Builder request = new Request.Builder()
                .setSuffixUrl(suffixUrl);
//                .setFileName("xfs.apk")
//                .setDirName("/sdcard/");
        AppClient.getInstance().download(request, callback);
    }
}

```
### 3. 新建请求管理类（单例）

```
public class HttpManager {
    private static IHttp httpRequest;
    private static IHttp externalRequest;

    public static void setHttpRequest(IHttp request) {
        if (httpRequest == null && request != null) {
            httpRequest = request;
        }
    }

    private HttpManager() {
        throw new UnsupportedOperationException("can't support operate");
    }

    public static IHttp getLoader() {
        if (httpRequest == null) {
            synchronized (HttpManager.class) {
                if (httpRequest == null) {
                    if (externalRequest != null) {
                        httpRequest = externalRequest;
                    } else {
                        httpRequest = new HttpRequest();
                    }
                }
            }
        }
        return httpRequest;
    }


}
```

### 4、使用方法
```
  HttpManager.getLoader().postJson<ResponseResult<UnReadBean>>(
            ApiConfig.URL_UNREAD_COUNT,
            "",
            object : ACallback<ResponseResult<UnReadBean>>() {
                override fun onFail(errCode: Int, errMsg: String) {
                    unreadCount.value = 0
                }

                override fun onSuccess(data: ResponseResult<UnReadBean>?) {
                    unreadCount.value = data!!.data.hasUnread
                }
            })
```