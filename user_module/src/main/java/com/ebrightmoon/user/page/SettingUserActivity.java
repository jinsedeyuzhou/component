package com.ebrightmoon.user.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ebrightmoon.common.base.mvc.BaseActivity;
import com.ebrightmoon.data.router.RouterURLS;
import com.ebrightmoon.user.R;

@Route(path = RouterURLS.USER_SETTING)
public class SettingUserActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_user_setting);
    }

    @Override
    public void initData() {
        invalidateOptionsMenu();
    }

    @Override
    public void initView() {
        setTitle("设置");

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.share).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }

    @Override
    protected void actionSettings() {

    }
}
