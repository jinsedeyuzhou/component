package com.ebrightmoon.main.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebrightmoon.common.util.DensityUtils;
import com.ebrightmoon.common.view.ProgressDrawable;
import com.ebrightmoon.main.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class LoadMoreView extends RelativeLayout {
    public static String REFRESH_FOOTER_PULLUP = "正在加载中...";
    public static String REFRESH_FOOTER_FINISH = "没有更多数据了";
    private ProgressDrawable mProgressDrawable;
    private ImageView mIvProgress;
    private LinearLayout mLlContent;
    private TextView mTvDes;

    public LoadMoreView(Context context) {
        super(context);
        initView(context, null, 0, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        mLlContent = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        layoutParams.height = DensityUtils.dip2px(40);
        layoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
        addView(mLlContent, layoutParams);

        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(DensityUtils.dip2px(20), DensityUtils.dip2px(20));
        mIvProgress = new ImageView(context);
        progressParams.gravity = Gravity.CENTER_VERTICAL;
        mIvProgress.animate().setInterpolator(new LinearInterpolator());
        mIvProgress.setImageResource(R.mipmap.ic_launcher);
        mLlContent.addView(mIvProgress, progressParams);


        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xff666666);
        mIvProgress.setImageDrawable(mProgressDrawable);


        LinearLayout.LayoutParams mDesProgess = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        mDesProgess.leftMargin = DensityUtils.dip2px(12);
        mDesProgess.gravity = Gravity.CENTER_VERTICAL;
        mTvDes = new TextView(context);
        mTvDes.setTextColor(Color.parseColor("#666666"));
        mTvDes.setText(REFRESH_FOOTER_PULLUP);
        mLlContent.addView(mTvDes, mDesProgess);

    }


    /**
     * 加载完成
     */
    public void finishLoading() {
        if (mProgressDrawable != null) {
            mProgressDrawable.stop();
        } else {
            mIvProgress.animate().rotation(0).setDuration(300);
        }
        mIvProgress.setVisibility(View.GONE);
        mTvDes.setText(REFRESH_FOOTER_FINISH);
    }

    /**
     * 开始加载
     */
    public void startLoading() {
        mIvProgress.setVisibility(View.VISIBLE);
        mTvDes.setText(REFRESH_FOOTER_PULLUP);
        if (mProgressDrawable != null) {
            mProgressDrawable.start();
        }
    }

    /**
     * 开始加载
     */
    public void hideLoading() {
        if (mProgressDrawable != null) {
            mProgressDrawable.stop();
        }
        mTvDes.setText(REFRESH_FOOTER_PULLUP);
    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mProgressDrawable = null;
    }
}
