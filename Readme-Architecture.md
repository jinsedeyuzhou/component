# 项目架构
### 组件化

随着项目功能的增多，单一工程项目下的代码越来越多，编译速度越来越慢。无论采用最初的MVC,还是改进化的MVP和MVVM，都不能很好的的这个问题。从SDK演化而来的组件化，在解决这个问题的时候取得了良好的效果。先看一下组件化模式的结构图如下，分为组件化模式和集成模式
支付宝
![bf66b8d37bf335552955433a8ba278a9.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p2640)

![4d123115e1ef4258e3681b911542fdf4.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1472)

![8a7b304d7a91c8dc07e4e3ffd1a28c2a.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1517)

 组件化结构主要分为三层，基础层，业务层，宿主层。
 * 基础工具和服务
 主要包括通用工具库，三方组件库，三方功能库，通用工具库中主要有工具类，基类，列表适配器，自定义控件，通用组件、弹窗、toast、网络库，数据存储，图片处理、主线通信，service、权限处理等。三方组件库为第三方开发的组件，例如pickerView和banner等，引入第三方库的主要为了节省开发成本，有时候三方库中提供的功能可能微调或者存在bug，因为在可能的前提下，尽量引入源码。三方功能库主要是第三方提供的功能，主要提供的是arr或者jar,基本不提供源码，例如bugly,shareSDk等。此层为大部分app都通用的组件。三方组件和三方功能库可根据需求选择添加。
 * 业务层
 此层主要包括公共数据、核心业务、通用业务。公共数据为app里面全局引用的数据，例如持久化实体类，通用业务实体类，全局配置类（包括app配置和用户配置，网络配置，路由）等等。核心业务和通用业务为普通业务所引用。普通业务module根据不同的业务划分，每一个可以单独提出来作为一个app独立使用。
 * 宿主层
 部分公司可能会有多个app，各个app中可能有部分相同的业务功能。可引用业务层一个module或者多个module。减少重复业务功能开发。
 三层关系：宿主层可以引用业务层，业务层可以引用基础层，不可以逆向引用。底层不可以引用高层组件，即不可以相互引用。
 下面具体介绍组件化项目中的各个module。
##### 1、app壳工程
此工程项目下基本没有任何代码，需要在build.gradle配置一些签名信息及依赖相关模块的判断，在AndroidManifest.xml下配置集成模式下项目的一些信息例如主题，名称，图标以及自定义application的引用等。
```
implementation project(':common_module')
    // 一个字段控制所有模块，打开是集成模式，关闭时组件化模式
    if (!isModule.toBoolean()) {
        implementation project(':main_module')
        implementation project(':module_user')
        implementation project(':ui_module')
    }
    // 每个字段控制每个模块  通过字段来决定是否集成到集成模式
    if (!isMain.toBoolean()) {
        implementation project(':main_module')
    }
    if (!isUser.toBoolean())
    {
        implementation project(':module_user')

    }
    if (!isUi.toBoolean())
    {
        implementation project(':ui_module')
    }
```

##### 2、第三方库
此目录下放一些第三方功能库、通用组件库和自己封装的库，例如网络库。
引用的第三方库，尽量使用源码，以方便和项目不同的功能或者bug进行修改

![0f78bf1983c8ac10e78e6e9c2de3008e.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1482)
常见的三方库
1. banner
2. photoView
3. pickerView
4. 。。。

##### 3、公共组件
CommonModule公共组件，主要放一些基类，工具类，通用widget和一些通用的库的封住，在values目录下可以放一些常见的颜色和常见的间距字体大小以方便引用,也可以放一些通用的主题和配置文件权限等。此组件最好放所有项目都能使用的，即通用的。在不同的项目需要改变的放在CommonData目录下，因为不同的项目中数据时不一致的。将变化和稳定的分开来。build.gradle 文件引用上面的第三方库。(此模块和具体的业务无关)
![1121bc8f045942850034cb5e6d5b0fbc.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1484)


```
dependencies {
    api fileTree(dir: 'libs', include: ['*.jar'])
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    api rootProject.ext.dependencies.appcompat_v7
    api rootProject.ext.dependencies.support_v4
    api rootProject.ext.dependencies.constraint_layout
    api rootProject.ext.dependencies.design
    api rootProject.ext.dependencies.multidex


    api rootProject.ext.dependencies.retrofit
    api rootProject.ext.dependencies.retrofit_converter_gson
    api rootProject.ext.dependencies.retrofit_adapter_rxjava2
    api rootProject.ext.dependencies.urlconnection
    api rootProject.ext.dependencies.okhttp_apache
    api rootProject.ext.dependencies.okhttp
    api rootProject.ext.dependencies.logging_interceptor
    api rootProject.ext.dependencies.rxandroid
    api rootProject.ext.dependencies.rxjava

    api rootProject.ext.dependencies.gson

    api rootProject.ext.dependencies.glide
    api rootProject.ext.dependencies.fresco

    api rootProject.ext.dependencies.eventbus
    api rootProject.ext.dependencies.rxbus
    api rootProject.ext.dependencies.circleimage
    api rootProject.ext.dependencies.cardview


    api rootProject.ext.dependencies["tencent-bugly"]
    api rootProject.ext.dependencies["stetho"]
    api rootProject.ext.dependencies["stetho-okhttp3"]
    api rootProject.ext.dependencies["logger"]





    implementation project(':baseLib:banner')
    implementation project(':baseLib:magicindicator')
    implementation project(':baseLib:photoview')
    implementation project(':baseLib:pickerview')
    implementation project(':baseLib:retrofitrxc')
    implementation project(':baseLib:alertview')
    implementation project(':baseLib:kprogresshud')
    implementation project(':baseLib:viewanimations')
    //公共库导入
    implementation project(':common_data')
}
```
为了方便项目管理，我们习惯性的喜欢新建一个config.gradle文件并将这些依赖配置到此文件中，便于管理。这里需要注意的是采用的时api的方式，因为implementation指令是不对外公开的，也就是说其他业务组件依赖common_module后仍然无法引用到retrofit、gson等。
另外此组件中一个比较重要的BaseApplication类，所有的业务组件都需要实现自己的Application，并继承此类，另外初始化时尽量不要选择在主线。此类内容如下
```
public class BaseApplication extends Application {
    private static final String TAG=BaseApplication.class.getSimpleName();
    private static BaseApplication app;

    public static BaseApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        init();

    }
    private void init()
    {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                DbHelper.getInstance().init(app.getApplicationContext());
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                CommonApplication.initQDApp(app);
                LoaderFactory.getLoader().init(app);
                //路由初始化
                RouterConfig.init(app, true);
                //Stetho调试工具初始化
                Stetho.initializeWithDefaults(app);
                LogUtils.setShowLog(true);
            }
        }.start();

    }
  }
```
1. 网络库封装。可替代
2. 图片加载。可替换
3. 主线通信。EventBus,RxBus 可替换
4. 权限管理
5. 基类，适配器，service
6. 日志管理。可替换
7. 调试工具。内存泄漏，其他辅助调试工具。
8. 常见工具类。系统类，网络类，字符串，通用工具类。
9. 弹窗，toast（可替换便于扩展），进度条，通用状态显示。
##### 4、ConmonData
此模块主要存放app中的相关数据和针对项目中特定的通用数据，例如数据库，Aroute数据，各种通用实体类，全局配置类等
![0e84648c840380f5baa875d0d38f3a76.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1486)
也可以将项目中所有的图片等相关资源存放在特定目录下
##### 5、CommonLogic（公共业务模块）
此模块主要将通用的业务逻辑提出来，其他相关业务逻辑模块可以引用。

##### 6、安全模块
此模块可根据需求不同，可合并到CommonModule模块，或者独立出来。
主要放一些加解密，安全环境检测，签名校验等内容。有些项目对安全性要求比较高，相关代码可以移出来。
![47ca675316db83781b71857e7181b26a.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1488)


##### 7、业务相关模块
该模块和具体的业务相关，有些业务可能独立出来成为一个独立的app。
业务模块可根据需求不同采用不同的适配模式，MVC,MVP,MVVM,根据项目需要采用不同的组织结构

![5df4173395bdd955cb57cab80a973b34.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1480)


集成模式下是库，组件化模式下是应用，需要在build.gradle 通过判断来切换。在总的工程根目录的gradle.properties中设置开关，可以每个业务模块设置一个参数，也可以统一设置一个参数。
```
## Project-wide Gradle settings.
//# IDE (e.g. Android Studio) users:
//# Gradle settings configured through the IDE *will override*
//# any settings specified in this file.
//# For more details on how to configure your build environment visit
//# http://www.gradle.org/docs/current/userguide/build_environment.html
//# Specifies the JVM arguments used for the daemon process.
//# The setting is particularly useful for tweaking memory settings.
//org.gradle.jvmargs=-Xmx1536m
//# When configured, Gradle will run in incubating parallel mode.
//# This option should only be used with decoupled projects. More details, visit
//# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:decoupled_projects
//# org.gradle.parallel=true
//# false集成模式true组件化模式
isModule = false

```

![8630ef85347536a8cf0ecef03f63abe9.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1476)

在每个业务模块下配置build.gradle
```
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```
在每个业务模块新建debug，存放自定义application（需要集成BaseApplication）和入口Activity，在合并打包时需要删除，在build.gradle中配置如下
![7e00632ea48d6981bb59076aa07419a4.png](evernotecid://1E18C04F-DAEA-48C5-A98E-557CB1D49BF8/appyinxiangcom/12871311/ENResource/p1477)

在main包下建个module文件，里面存放AndroidMainfest.xml文件，因为在组件化模式下，项目组件也是可以运行的，所有需要有正常的清单文件。
##### 各个模块通信
为了解耦，各个模块跳转传值使用router，通信可以使用广播或者主线通信（RxBus或EventBus）。
##### 常见问题
1. 命名资源冲突。在集成模式下，各个组件中资源可能存在相同的名字，在引用的时候会报错或者导致引用混乱。解决方法（1）、提前命名约定，在资源名称前加模块名。（2）、在build里设置添加前缀。  //设置了resourcePrefix值后，所有的资源名必须以指定的字符串做前缀，否则会报错。//但是resourcePrefix这个值只能限定xml里面的资源，并不能限定图片资源，所有图片资源仍然需要手动去修改资源名。resourcePrefix "user_"
2. 混淆。组件化模式下混淆方案可以在各个模块，也可以集中在app壳工程中。
##### 总结
组件化中Activity之间的跳转和传值主要使用阿里的路由组件，不同组件之间使用主线通信。另外在组件化的时候，由于组件时库，不允许使用switch，资源命名冲突问题都需要注意（资源命名参考编程规范）。另外小项目可以采用组件化思想，以方便集成到其他应用中。，业务组件分的要合适，太大背离组件化的初衷，太小碎片化太严重。最好某一个系统模块放到一起，例如用户系统，主页系统，订单系统。