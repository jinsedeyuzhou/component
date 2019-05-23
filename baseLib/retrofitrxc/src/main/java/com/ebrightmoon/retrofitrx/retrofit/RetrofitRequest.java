package com.ebrightmoon.retrofitrx.retrofit;

import android.content.Context;

/**
 * @Description: 传入自定义Retrofit接口的请求类型
 */
public class RetrofitRequest extends BaseRequest<RetrofitRequest> {


    protected RetrofitRequest(Context context) {
        super(context);
    }

    public <T> T create(Class<T> cls) {
        initGlobalConfig();
        initLocalConfig();
        return retrofit.create(cls);
    }

}
