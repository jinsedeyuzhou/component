package com.ebrightmoon.common.presenter;

import android.provider.ContactsContract;

/**
 * Time: 2019-08-26
 * Author:wyy
 * Description:
 *
 */
public class CommonPresenter implements CommonContract.Presenter {

    private CommonContract.View view;

    public CommonPresenter(CommonContract.View view) {
        this.view=view;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    /**
     * 注册事件
     */
    @Override
    public void subscribe() {

    }

    /**
     * 解决注册
     */
    @Override
    public void unsubscribe() {

    }
}
