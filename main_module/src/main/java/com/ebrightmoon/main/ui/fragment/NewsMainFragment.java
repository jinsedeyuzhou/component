package com.ebrightmoon.main.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.main.R;
import com.ebrightmoon.main.entity.Channel;

public class NewsMainFragment extends BaseFragment {


    public static NewsMainFragment newInstance(Channel courseType) {
        NewsMainFragment newsMainFragment = new NewsMainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Channel.KEY_COURSE_TYPE, courseType);
        newsMainFragment.setRegisterEvent(true);
        newsMainFragment.setArguments(bundle);
        return newsMainFragment;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_main_news;
    }

    @Override
    protected void initView(View view) {

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
