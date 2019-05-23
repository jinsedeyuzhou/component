package com.ebrightmoon.main.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ebrightmoon.data.router.RouterURLS;

/**
 * @Created by TOME .
 * @时间 2018/4/26 10:19
 * @描述 ${路由中心}
 * 所有的跳转
 */
//ARouter 提供了大量的参数类型 跳转携带 https://blog.csdn.net/zhaoyanjun6/article/details/76165252
public class RouterCenter {


    public static void toUiHome() {
        ARouter.getInstance().build(RouterURLS.UI_HOME).navigation();

    }
    
    public static void toLogin()
    {
        ARouter.getInstance().build(RouterURLS.USER_LOGIN).navigation();
    }
    
    public static void toRegister()
    {
        ARouter.getInstance().build(RouterURLS.USER_REGISTER).navigation();
    }

    public static void toSetting()
    {
        ARouter.getInstance().build(RouterURLS.USER_SETTING).navigation();

    }
}
