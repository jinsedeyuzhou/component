package com.ebrightmoon.retrofitrx.retrofit;

import android.content.Context;

import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.mode.CacheResult;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: Head请求
 */
public class HeadRequest extends BaseHttpRequest<HeadRequest> {

    protected HeadRequest(Context context) {
        super(context);
    }

    public HeadRequest(Context context, String suffixUrl) {
        super(context, suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.head(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> Observable<CacheResult<T>> cacheExecute(Type type) {
        return this.<T>execute(type).compose(apiCacheBuilder.build().<T>transformer(cacheMode, type));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
//        if (super.tag != null) {
//            ApiManager.get().add(super.tag, disposableObserver);
//        }
        if (isLocalCache) {
            this.cacheExecute(getSubType(callback)).subscribe(disposableObserver);
        } else {
            this.execute(getType(callback)).subscribe(disposableObserver);
        }
    }
}