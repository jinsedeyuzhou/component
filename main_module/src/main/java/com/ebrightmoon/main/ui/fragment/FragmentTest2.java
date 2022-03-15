package com.ebrightmoon.main.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.mvc.BaseFragment;
import com.ebrightmoon.commonlogic.widget.SimpleMultiStateView;
import com.ebrightmoon.main.R;

/**
 * Time: 2020-04-09
 * Author:wyy
 * Description:
 */
public class FragmentTest2 extends BaseFragment {
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_test2;
    }

    @Override
    protected void initView(View view) {

        final SimpleMultiStateView stateView = view.findViewById(R.id.simple_stateView);
        stateView.setStateView(SimpleMultiStateView.ViewState.ERROR);
        stateView.setOnStateViewListener(new SimpleMultiStateView.OnStateViewListener() {
            @Override
            public void onClick(View view) {
                stateView.setStateView(SimpleMultiStateView.ViewState.NORMAL);
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }
}
