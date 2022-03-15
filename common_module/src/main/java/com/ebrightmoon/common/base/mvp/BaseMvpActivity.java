package com.ebrightmoon.common.base.mvp;

import androidx.annotation.Nullable;

import com.ebrightmoon.common.base.mvc.BaseActivity;

/**
 * Time: 2019-12-12
 * Author:wyy
 * Description:
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    @Nullable
    protected P mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();//释放资源
        }
        this.mPresenter = null;
    }
}
