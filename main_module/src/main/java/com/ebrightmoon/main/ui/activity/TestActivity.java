package com.ebrightmoon.main.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.main.R;
import com.ebrightmoon.main.ui.fragment.FragmentTest1;
import com.ebrightmoon.main.ui.fragment.FragmentTest2;

/**
 * Time: 2020-04-09
 * Author:wyy
 * Description:
 *  .add(R.id.inquiry_contrainer, inquriySearchFragment!!, seaTag)
 *                     .hide(inquriyFragment)
 *                     .show(inquriySearchFragment!!)
 *                     .addToBackStack(seaTag)
 *                     .commit()
 */
public class TestActivity extends BaseActivity {


    private FragmentTest1 fragmentTest1;
    private FragmentTest2 fragmentTest2;

    @Override
    public void initData() {
        fragmentTest1 = new FragmentTest1();
        fragmentTest2 = new FragmentTest2();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.inquiry_contrainer,fragmentTest1,"test1")
                .add(R.id.inquiry_contrainer,fragmentTest2,"test2")
                .hide(fragmentTest2)
                .show(fragmentTest1)
                .commit();
    }

    @Override
    public void initView() {

        findViewById(R.id.btn_fragment1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(fragmentTest2)
                        .show(fragmentTest1)
                        .commit();
            }
        });

        findViewById(R.id.btn_fragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(fragmentTest1)
                        .show(fragmentTest2)
                        .commit();
            }
        });

    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_test);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
