package com.ebrightmoon.retrofitrx.expand;


import android.content.Context;

import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.func.ApiResultFunc;
import com.ebrightmoon.retrofitrx.mode.CacheResult;
import com.ebrightmoon.retrofitrx.mode.MediaTypes;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Description: 返回APIResult的POST请求类
 */
public class ApiPostRequest extends ApiBaseRequest<ApiPostRequest> {
    protected Map<String, Object> forms = new LinkedHashMap<>();
    protected StringBuilder stringBuilder = new StringBuilder();
    protected RequestBody requestBody;
    protected MediaType mediaType;
    protected String content;

    protected ApiPostRequest(Context context) {
        super(context);
    }

    public ApiPostRequest(Context context, String suffixUrl) {
        super(context, suffixUrl);
    }


    @Override
    protected <T> Observable<T> execute(Type type) {
        if (stringBuilder.length() > 0) {
            suffixUrl = suffixUrl + stringBuilder.toString();
        }
        if (forms != null && forms.size() > 0) {
            if (params != null && params.size() > 0) {
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                Map.Entry<String, String> entry;
                while (entryIterator.hasNext()) {
                    entry = entryIterator.next();
                    if (entry != null) {
                        forms.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return apiService.postForm(suffixUrl, forms)
                    .map(new ApiResultFunc<T>(type))
                    .compose(this.<T>apiTransformer());
        }
        if (requestBody != null) {
            return apiService.postBody(suffixUrl, requestBody)
                    .map(new ApiResultFunc<T>(type))
                    .compose(this.<T>apiTransformer());
        }
        if (content != null && mediaType != null) {
            requestBody = RequestBody.create(mediaType, content);
            return apiService.postBody(suffixUrl, requestBody)
                    .map(new ApiResultFunc<T>(type))
                    .compose(this.<T>apiTransformer());
        }
        return apiService.post(suffixUrl, params)
                .map(new ApiResultFunc<T>(type))
                .compose(this.<T>apiTransformer());
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

    public ApiPostRequest addUrlParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append("?");
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(paramKey).append("=").append(paramValue);
        }
        return this;
    }

    public ApiPostRequest addForm(String formKey, Object formValue) {
        if (formKey != null && formValue != null) {
            forms.put(formKey, formValue);
        }
        return this;
    }

    public ApiPostRequest setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public ApiPostRequest setString(String string) {
        this.content = string;
        this.mediaType = MediaTypes.TEXT_PLAIN_TYPE;
        return this;
    }

    public ApiPostRequest setString(String string, MediaType mediaType) {
        this.content = string;
        this.mediaType = mediaType;
        return this;
    }

    public ApiPostRequest setJson(String json) {
        this.content = json;
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public ApiPostRequest setJson(JSONObject jsonObject) {
        this.content = jsonObject.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }

    public ApiPostRequest setJson(JSONArray jsonArray) {
        this.content = jsonArray.toString();
        this.mediaType = MediaTypes.APPLICATION_JSON_TYPE;
        return this;
    }
}
