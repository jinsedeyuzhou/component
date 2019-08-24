package com.ebrightmoon.ui.page.test;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.widget.custom.CustTextView;
import com.ebrightmoon.ui.widget.custom.CustomViewGroup;

/**
 * Time: 2019-08-23
 * Author:wyy
 * Description:
 */
public class CustomViewActivity extends BaseActivity {


    private CustTextView custTextView;
    private CustomViewGroup customViewGroup;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_custom_view);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        custTextView = findViewById(R.id.customTextView);

        custTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custTextView.invalidate();
            }
        });

        customViewGroup = findViewById(R.id.customViewGroup);

        customViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewGroup.invalidate();
            }
        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
