package com.ebrightmoon.main.arouter;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ebrightmoon.data.RouterURLS;

/**
 * @Created by TOME .
 * @时间 2018/4/26 10:19
 * @描述 ${路由中心}
 */
//ARouter 提供了大量的参数类型 跳转携带 https://blog.csdn.net/zhaoyanjun6/article/details/76165252
public class RouterCenter {


    public static void toUiHome() {
        ARouter.getInstance().build(RouterURLS.UI_HOME).navigation();

    }
}
