package com.ebrightmoon.ui.page.test;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.dialog.Toasty;
import com.ebrightmoon.ui.R;

import static android.graphics.Typeface.BOLD_ITALIC;

/**
 * Time: 2019/6/11
 * Author:wyy
 * Description:
 */
public class UIToastyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ui_activity_toasty);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.error(mContext, R.string.error_message, Toasty.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(mActivity, R.string.success_message, Toasty.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(mActivity, R.string.info_message, Toasty.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.warning(mActivity, R.string.warning_message, Toasty.LENGTH_SHORT, true).show();
            }
        });
        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.normal(mActivity, R.string.normal_message_without_icon).show();
            }
        });
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.laptop512);
                Toasty.normal(mActivity, R.string.normal_message_with_icon, icon).show();
            }
        });
        findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(mActivity, getFormattedMessage()).show();
            }
        });
        findViewById(R.id.button_custom_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.Config.getInstance()
                        .setToastTypeface(Typeface.createFromAsset(mActivity.getAssets(), "PCap Terminal.otf"))
                        .allowQueue(false)
                        .apply();
                Toasty.custom(mContext, R.string.custom_message, getResources().getDrawable(R.drawable.laptop512),
                        android.R.color.black, android.R.color.holo_green_light, Toasty.LENGTH_SHORT, true, true).show();
                Toasty.Config.reset(); // Use this if you want to use the configuration above only once
            }
        });
    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }
}
