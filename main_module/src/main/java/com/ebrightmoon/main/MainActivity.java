package com.ebrightmoon.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.main.arouter.RouterCenter;
import com.ebrightmoon.main.ui.activity.HomeMainActivity;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.retrofit.GetRequest;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DbHelper.getInstance().init(this.getApplicationContext());

        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, HomeMainActivity.class));
            }
        });
        findViewById(R.id.tv_ui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterCenter.toUiHome();
            }
        });
        new GetRequest(mContext,"").request(new ACallback<Object>() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFail(int errCode, String errMsg) {

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
