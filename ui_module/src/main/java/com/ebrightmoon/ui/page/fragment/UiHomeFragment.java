package com.ebrightmoon.ui.page.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.widget.CustomDialogFragment;
import com.ebrightmoon.common.widget.dialogfragment.IDialog;
import com.ebrightmoon.common.widget.dialogfragment.SystemDialog;
import com.ebrightmoon.common.widget.dialogfragment.manager.DialogWrapper;
import com.ebrightmoon.common.widget.dialogfragment.manager.SystemDialogsManager;
import com.ebrightmoon.common.widget.popwindow.CommonPopupWindow;
import com.ebrightmoon.common.widget.popwindow.CustomPopWindow;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.page.WebViewActivity;
import com.ebrightmoon.ui.page.test.DialogActivity;
import com.ebrightmoon.ui.page.test.HttpActivity;
import com.ebrightmoon.ui.page.test.ToastyActivity;

import java.io.IOException;
import java.io.InputStream;

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

    @Override
    protected void initView(View view) {
        contentView=view;

        view.findViewById(R.id.btn_http).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, HttpActivity.class));
            }
        });

        view.findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, DialogActivity.class));
            }
        });

        view.findViewById(R.id.btn_toasty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, ToastyActivity.class));
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
