# 常用三方库

### 一、图片加载
1. 定义图片加载接口
```
public interface ILoader {
       void init(Context context);

       void loadNet(ImageView target, String url, Options options);

       void loadResource(ImageView target, int resId, Options options);

       void loadAssets(ImageView target, String assetName, Options options);

       void loadFile(ImageView target, File file, Options options);

       void clearMemoryCache(Context context);

       void clearDiskCache(Context context);

       class Options {

           public static final int RES_NONE = -1;
           public int loadingResId = RES_NONE;//加载中的资源id
           public int loadErrorResId = RES_NONE;//加载失败的资源id

           public static Options defaultOptions() {
               return new Options(SDKConfig.IL_LOADING_RES, SDKConfig.IL_ERROR_RES);
           }

           public Options(int loadingResId, int loadErrorResId) {
               this.loadingResId = loadingResId;
               this.loadErrorResId = loadErrorResId;
           }
       }
   }
```

2. 实现图片加载框架
**Glide**
```
public class GlideLoader implements ILoader {
    @Override
    public void init(Context context) {
        try {
            Class.forName("com.bumptech.glide.Glide");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Must be dependencies Glide!");
        }
    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options);
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    private RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    private void load(DrawableTypeRequest request, ImageView target, Options options) {
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        request.crossFade().into(target);
    }
}
```
**FrescoLoader**
```
public class FrescoLoader implements ILoader {

    private Context context;

    @Override
    public void init(Context context) {
        try {
            Class.forName("com.facebook.drawee.view.SimpleDraweeView");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Must be dependencies Fresco!");
        }
        if (context == null) {
            throw new NullPointerException("this context is null.");
        }
        this.context = context;
        Fresco.initialize(context.getApplicationContext());
    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        Uri uri = Uri.parse(url);
        load(target, uri, options);
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        Uri uri = Uri.parse("res://" + context.getPackageName() +  "/" + resId);
        load(target, uri, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        Uri uri = Uri.parse("asset:///" + assetName);
        load(target, uri, options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        if (file == null) {
            return;
        }
        Uri uri = Uri.parse("file://" + file.getPath());
        load(target, uri, options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    @Override
    public void clearDiskCache(Context context) {
        Fresco.getImagePipeline().clearDiskCaches();
    }

    private void load(ImageView target, Uri uri, Options options) {
        if (target == null) {
            throw new NullPointerException("this imageView is null.");
        }
        if (options == null) options = Options.defaultOptions();
        if (target instanceof SimpleDraweeView) {
//            Log.i("Fresco Load SimpleDraweeView Path:" + uri.getPath());
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) target;
            simpleDraweeView.setImageURI(uri);
            if (options.loadingResId != Options.RES_NONE) {
                simpleDraweeView.getHierarchy().setPlaceholderImage(options.loadingResId);
            }
            if (options.loadErrorResId != Options.RES_NONE) {
                simpleDraweeView.getHierarchy().setFailureImage(options.loadErrorResId);
            }
        } else {
//            Log.e("Fresco Load ImageView must be SimpleDraweeView");
        }
    }
}

```
3. 使用LoaderManager
```
/**
 * @Description: 图片加载管理，可定制图片加载框架
 * @date: 2016-12-19 15:16
 */
public class LoaderManager {
    private static ILoader innerLoader;
    private static ILoader externalLoader;

    public static void setLoader(ILoader loader) {
        if (externalLoader == null && loader != null) {
            externalLoader = loader;
        }
    }

    public static ILoader getLoader() {
        if (innerLoader == null) {
            synchronized (LoaderManager.class) {
                if (innerLoader == null) {
                    if (externalLoader != null) {
                        innerLoader = externalLoader;
                    } else {
                        innerLoader = new GlideLoader();
                    }
                }
            }
        }
        return innerLoader;
    }
}
```
4. 使用
```
 LoaderManager.getLoader().loadFile(iv_photo, file, null)
```

### 二、主线通信 (RxJava/EventBus)
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

**单独使用RxBus**
  RxBus.get()
 .toFlowable(String::class.java)
 .bindToLifecycle(this) //防止内存泄漏
 .subscribe { str_ ->
 }

### 三、权限管理
```
 PermissionManager.request(mActivity, new OnPermissionCallback() {
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

### 四、图片压缩

### 五、Webview
封装通用的js 和原生交互
支持BridgeJs

### 六、Arouter



### 三方库使用介绍
1. 下拉刷新

2. banner

3. adapter

4. pickerView

### 混淆
1. 在app module中统一配置混淆规则
> proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
2. 各个module单独配置混淆规则（推荐）
> 在各个module中配置  consumerProguardFiles 'consumer-rules.pro'