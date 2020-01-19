package com.ebrightmoon.user;

import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.user.page.LoginUserActivity;
import com.ebrightmoon.user.page.RegisterUserActivity;
import com.ebrightmoon.user.page.SettingUserActivity;

public class UserMainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toOtherActivity(LoginUserActivity.class, null, false);
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toOtherActivity(RegisterUserActivity.class, null, false);

            }
        });

        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toOtherActivity(SettingUserActivity.class, null, false);

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
