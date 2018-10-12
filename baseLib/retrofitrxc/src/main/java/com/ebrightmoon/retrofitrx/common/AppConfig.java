package com.ebrightmoon.retrofitrx.common;

/**
 * Created by wyy on 2018/1/26.
 */

public class AppConfig {

    private AppConfig()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final String BASE_URL = "http://192.168.7.148:8080";
    //请求间值
    public static final String MIDDLE_URL =BASE_URL+ "/CommonServlet";
    //注册接口 POST
    public static final String  URL_REGISTER=MIDDLE_URL+"/Register";
    //登录接口
    public static final String  URL_LOGIN=MIDDLE_URL+"/Login";
}
