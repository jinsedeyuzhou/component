package com.ebrightmoon.retrofitrx.strategy;


import com.ebrightmoon.retrofitrx.core.ApiCache;
import com.ebrightmoon.retrofitrx.mode.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @Description: 缓存策略接口
 */
public interface ICacheStrategy<T> {
    <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type);
}
