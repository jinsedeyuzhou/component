package com.ebrightmoon.ui.page.databinding;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.data.UserInfo;
import com.ebrightmoon.ui.databinding.UiActivityDatabindingBinding;

/**
 * Time: 2019/6/21
 * Author:wyy
 * Description:
 */
public class UIDataBindingActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        UiActivityDatabindingBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.ui_activity_databinding);
        final UserInfo userInfo=new UserInfo("li","san");
        dataBinding.setUserInfo(userInfo);

        findViewById(R.id.btn_databing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.setFirstName("zlkjadfkdaf");

            }
        });

        findViewById(R.id.btn_databing_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.setAge(10);
                userInfo.setLastName("测试字段");
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }


}
