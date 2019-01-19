package com.ebrightmoon.retrofitrx.expand;

import android.content.Context;

import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.func.ApiResultFunc;
import com.ebrightmoon.retrofitrx.mode.CacheResult;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: 返回APIResult的GET请求类
 */
public class ApiGetRequest extends ApiBaseRequest<ApiGetRequest> {

    protected ApiGetRequest(Context context) {
        super(context);
    }

    public ApiGetRequest(Context context, String suffixUrl) {
        super(context, suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.get(suffixUrl, params).map(new ApiResultFunc<T>(type)).compose(this.<T>apiTransformer());
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
