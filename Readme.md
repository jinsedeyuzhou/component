# 项目介绍

### 项目架构
[doc/511590744551_.pic_hd.jpg]
### 项目功能介绍
### 1、网络请求
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
### 2、主线通信 (RxJava/EventBus)
1. 实体类（CommonEvent）实现接口IEvent
2. 继承BaseActivity或者BaseFragment 覆盖isRegisterEvent 方法返回true或者设置isRegisterEvent值
3. 接收事件 注解
```
 @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IEvent event) {

        /* Do something */

    }
```
4. 发送事件 
> BusManager.getBus().postSticky(new CommonEvent(true));

### 3、请求权限
```
 PermissionManager.instance().request(mActivity, new OnPermissionCallback() {
            @Override
            public void onRequestAllow(String permissionName) {
              
            }

            @Override
            public void onRequestRefuse(String permissionName) {
             
            }

            @Override
            public void onRequestNoAsk(String permissionName) {

            }
        }, Manifest.permission.CALL_PHONE);
    }
```
### 4、工具类
1. AppManager Activity管理
2. ArithmeticUtils 精确度计算(金融)
3. ByteUtils  字节转对象
4. DecimalFormatUtil 格式化输入百分数字符
5. DensityUtils px dp转换
6. EndeCodes 加密
7. FileUtils 文件存储
8. FontUtils 设置全局默认字体样式
9. HexUtils 进制转换
10. JsonUtils json转对象
11. KeyBoardUtils 软键盘处理
12. MD5
13. NetUtils 判断网络
14. PrefUtils xml数据存储
15. ScreenAdapter 屏幕适配
16. SDCardUtils
17. StatusBarUtils 状态栏透明
18. StringUtils 字符串处理
19. SystemUtils 系统参数
20. TimeUtil 时间格式化
21. ToastUtils Toast控制重复字段弹出
22. Tools 特定工具类
23. Validator 正则表达式验证密码，手机号，邮箱，身份证
24. ViewPager动画
### 5、图片加载
可根据需要替换Glide/Fresco
LoaderManager.getLoader().loadNet(imageView,url,options);
### 6、数据库
GreenDao : https://github.com/greenrobot/greenDAO
Room
### 7、webview
封装通用的js 和原生交互
支持BridgeJs
### 8、通用组件
1. flowlayout
2. 高斯模糊
3. 测滑删除
4. 测滑回退
5. 进度菊花
6. TabLayout 
7. PopWindow
8. Dialog/FragmentDialog
9. CustomToast
10. 菊花Dialog
11. 仿IOS开关
12. EditText 监听
13. ClearEditText 
### 9、资源

### 10、Arouter

### 11、三方库使用介绍
1. 下拉刷新

2. banner

3. adapter

4. pickerView

### 混淆
1. 在app module中统一配置混淆规则
> proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
2. 各个module单独配置混淆规则（推荐）
> 在各个module中配置  consumerProguardFiles 'consumer-rules.pro'