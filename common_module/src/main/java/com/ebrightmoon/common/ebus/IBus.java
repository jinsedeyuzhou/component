package com.ebrightmoon.common.ebus;

/**
 * @Description: 事件总线接口
 *
 * @date: 2016-12-19 15:06
 */
public interface IBus {
    void register(Object object);

    void unregister(Object object);

    void post(IEvent event);

    void postSticky(IEvent event);
}
