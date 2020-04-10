package com.ebrightmoon.common.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebrightmoon.common.R;


/**
 * 加载中Dialog
 *
 *      dialog = new LoadingDialog(mActivity,
 *                 ProgressDialog.STYLE_SPINNER, "数据加载中");
 *         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
 *         dialog.setCanceledOnTouchOutside(false);
 *
 *         //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
 *         dialog.setCancelable(false);
 *         dialog.setMessage("数据加载中");
 * @author wyy
 */
public class LoadingDialog extends Dialog {


    private TextView tips_loading_msg;
    private int layoutResId;
    private String message = null;
    Context context;
    private Activity mActivity;
    View view = null;
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    String contentmsg;
    private ImageView mIvProgress;
    private ProgressDrawable mProgressDrawable;

    public LoadingDialog(Context context, int themeResId, String content) {
        super(context, R.style.LoadDialog);
        this.context = context;
        mActivity = (Activity) context;
        this.contentmsg = content;
        view = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.setContentView(R.toolbar.dialog_loading);
        this.setContentView(view);
        mIvProgress = (ImageView) findViewById(R.id.iv_progress);
        mProgressDrawable = new ProgressDrawable();
        mProgressDrawable.setColor(0xffffffff);
        mIvProgress.setImageDrawable(mProgressDrawable);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.contentmsg);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mActivity.isFinishing())
            return;
        if (mProgressDrawable != null) {
            mProgressDrawable.stop();
        }

    }

    @Override
    public void show() {
        if (!this.isShowing()) {
            super.show();
        }
        if (tips_loading_msg != null) {
            tips_loading_msg.setText(contentmsg);
        }
        if (mProgressDrawable != null) {
            mProgressDrawable.start();
        }
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    public void setMessage(String _message) {
        contentmsg = _message;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mProgressDrawable.stop();
        mProgressDrawable = null;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mProgressDrawable==null)
        {
            mProgressDrawable = new ProgressDrawable();
            mProgressDrawable.setColor(0xffffffff);
            mIvProgress.setImageDrawable(mProgressDrawable);
        }
    }
}
