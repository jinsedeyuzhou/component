package com.ebrightmoon.ui.page.test;

import android.os.Bundle;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.retrofitrx.util.GsonUtil;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.data.ProductBean;
import com.ebrightmoon.ui.utils.AssetsUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Time: 2019/6/13
 * Author:wyy
 * Description:
 */
public class UIJsonActivity extends BaseActivity {

    String json="";
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ui_activity_json);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        Type type = new TypeToken<ProductBean>() {
        }.getType();
        GsonUtil.gson().fromJson(AssetsUtil.getJson(mContext,"test.json"),type);

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
