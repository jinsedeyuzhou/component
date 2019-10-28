package com.ebrightmoon.common.presenter;


import com.ebrightmoon.common.base.BasePresenter;
import com.ebrightmoon.common.base.BaseView;

/**
 * Time: 2019-08-26
 * Author:wyy
 * Description:
 */
public interface CommonContract {

   interface View extends BaseView<Presenter> {

    }

   interface Presenter extends BasePresenter {


    }


}
