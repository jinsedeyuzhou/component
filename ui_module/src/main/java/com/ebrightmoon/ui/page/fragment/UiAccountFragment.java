package com.ebrightmoon.ui.page.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.widget.CustomToast;
import com.ebrightmoon.ui.R;


public class UiAccountFragment extends BaseFragment {


    @Override
    protected int getLayoutID() {
        return R.layout.ui_fragment_account;
    }

    @Override
    protected void initView(View view) {

        view.findViewById(R.id.btn_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToast.makeText(mContext.getApplicationContext(),"customer", Toast.LENGTH_SHORT).show();
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
