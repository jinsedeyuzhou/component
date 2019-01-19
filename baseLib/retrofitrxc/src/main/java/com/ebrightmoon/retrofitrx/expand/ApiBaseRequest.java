package com.ebrightmoon.retrofitrx.expand;


import android.content.Context;

import com.ebrightmoon.retrofitrx.func.ApiDataFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.retrofit.BaseHttpRequest;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 返回APIResult的基础请求类
 */
public abstract class ApiBaseRequest<R extends ApiBaseRequest> extends BaseHttpRequest<R> {


    protected ApiBaseRequest(Context context) {
        super(context);
    }

    public ApiBaseRequest(Context context, String suffixUrl) {
        super(context, suffixUrl);
    }

    protected <T> ObservableTransformer<ResponseResult<T>, T> apiTransformer() {
        return new ObservableTransformer<ResponseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .map(new ApiDataFunc<T>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }
}
