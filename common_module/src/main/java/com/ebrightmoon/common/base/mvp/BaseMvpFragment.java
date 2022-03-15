package com.ebrightmoon.common.base.mvp;

import androidx.annotation.Nullable;

import com.ebrightmoon.common.base.mvc.BaseFragment;

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
