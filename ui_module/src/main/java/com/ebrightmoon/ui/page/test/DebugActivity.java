package com.ebrightmoon.ui.page.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.ui.R;

/**
 * Time: 2019-10-15
 * Author:wyy
 * Description:
 */
public class DebugActivity extends BaseActivity {
    private int count=3;
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ui_activity_debug);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void bindEvent() {
        count=2;
        findViewById(R.id.debug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=5;
                test(count);

            }
        });
    }

    private boolean test(int a) {
        Toast.makeText(mContext, "DebugActivity"+a, Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public void processClick(View paramView) {

    }
}
