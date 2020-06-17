package com.ebrightmoon.common.ebus;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * 有背压处理（Backpressure）的 Rxbus
 * RxBus.get()
 * .toFlowable(String::class.java)
 * .bindToLifecycle(this)
 * .subscribe { str_ ->
 * }
 */
public class RxBus {

    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass)
                .subscribeOn(Schedulers.io())  // 不是必须的
                .unsubscribeOn(Schedulers.io())// 不是必须的
                .observeOn(AndroidSchedulers.mainThread());// 不是必须的;
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }
}
