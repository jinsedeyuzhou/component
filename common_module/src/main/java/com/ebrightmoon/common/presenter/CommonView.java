package com.ebrightmoon.common.presenter;

/**
 * Time: 2019-08-26
 * Author:wyy
 * Description:
 * 相当于Activity/Fragment
 */
public class CommonView implements CommonContract.View {

    public CommonView() {
       new CommonPresenter(this);
    }

    private CommonContract.Presenter commonPresenter;

    @Override
    public void setPresenter(CommonContract.Presenter presenter) {
        this.commonPresenter=presenter;
    }
}
