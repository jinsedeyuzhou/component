package com.ebrightmoon.common.ebus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import rx.Scheduler;


/**
 * 无背压处理（Backpressure）的 Rxbus
 */
public class RxBusSubject {

    private final Subject<Object> mBus;

    private RxBusSubject() {
        // toSerialized method made bus thread safe
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBusSubject get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass)
                .subscribeOn(Schedulers.io())  // 不是必须的
                .unsubscribeOn(Schedulers.io())// 不是必须的
                .observeOn(AndroidSchedulers.mainThread())// 不是必须的
                ;
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBusSubject BUS = new RxBusSubject();
    }
}

