package com.ebrightmoon.ui.page.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.ebrightmoon.common.base.mvc.BaseFragment;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.page.databinding.UIDataBindingActivity;
import com.ebrightmoon.ui.page.kotlin.KotlinTestActivity;
import com.ebrightmoon.ui.page.test.CustomViewActivity;
import com.ebrightmoon.ui.page.test.DebugActivity;
import com.ebrightmoon.ui.page.test.UISideBarActivity;
import com.ebrightmoon.ui.page.test.UITouchActivity;
import com.ebrightmoon.ui.page.test.UiDialogActivity;
import com.ebrightmoon.ui.page.test.UiHttpActivity;
import com.ebrightmoon.ui.page.test.UIToastyActivity;

public class UiHomeFragment extends BaseFragment {

    private View contentView;

    public static UiHomeFragment newInstance() {
        UiHomeFragment uiHomeFragment = new UiHomeFragment();
        Bundle bundle = new Bundle();
        uiHomeFragment.setArguments(bundle);
        return uiHomeFragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.ui_fragment_home;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(View view) {
        contentView=view;

        view.findViewById(R.id.btn_http).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, UiHttpActivity.class));
            }
        });

        view.findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, UiDialogActivity.class));
            }
        });

        view.findViewById(R.id.btn_toasty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, UIToastyActivity.class));
            }
        });
        view.findViewById(R.id.btn_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, UITouchActivity.class));
            }
        });

        view.findViewById(R.id.btn_databing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, UIDataBindingActivity.class));
            }
        });

        view.findViewById(R.id.btn_sidebar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, UISideBarActivity.class));
            }
        });
        view.findViewById(R.id.btn_kotlin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, KotlinTestActivity.class));
            }
        });
        view.findViewById(R.id.btn_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, CustomViewActivity.class));
            }
        });
        view.findViewById(R.id.btn_debug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, DebugActivity.class));
            }
        });

//        EditText etNum = view.findViewById(R.id.etNum);
//        etNum.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });





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
