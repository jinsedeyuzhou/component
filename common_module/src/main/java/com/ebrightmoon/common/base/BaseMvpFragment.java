package com.ebrightmoon.common.base;

import androidx.annotation.Nullable;

/**
 * Time: 2019-12-12
 * Author:wyy
 * Description:
 */
public  abstract  class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    @Nullable
    protected  P mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();//释放资源
        }
        this.mPresenter = null;
    }
}
