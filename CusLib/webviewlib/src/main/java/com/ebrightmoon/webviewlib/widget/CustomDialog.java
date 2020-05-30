package com.ebrightmoon.webviewlib.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebrightmoon.webviewlib.R;
import com.ebrightmoon.webviewlib.utils.WebUtil;


public class CustomDialog extends Dialog implements
        View.OnClickListener {
    private Button btn_positive;
    private Button btn_neutral;
    private Button btn_nagetive;
    private TextView tv_title;
    private TextView tv_description;
    private TsingdaParams P;
    private LinearLayout contentView;
    private EditText mEdtPwd;
    private Context mContext;


    private LinearLayout headView;

    private ImageView iv_icon;// 暂时没有icon
    private final ImageView iv_cut_rule;

    public CustomDialog(Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        setContentView(R.layout.dialog_common_view);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        P = new TsingdaParams(context);
        mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        btn_positive = (Button) findViewById(R.id.button_positive);
        btn_nagetive = (Button) findViewById(R.id.button_nagetive);
        tv_title = (TextView) findViewById(R.id.textview_title);
        iv_cut_rule = (ImageView) findViewById(R.id.iv_cut_rule);
        tv_description = (TextView) findViewById(R.id.textview_description);
        btn_positive.setOnClickListener(this);
        btn_nagetive.setOnClickListener(this);
        btn_positive.setVisibility(View.GONE);
        btn_nagetive.setVisibility(View.GONE);
    }

    public CustomDialog(Context context, int style) {
        super(context, style);
        this.mContext = context;
        setContentView(R.layout.dialog_common_view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        P = new TsingdaParams(context);
        mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        btn_positive = (Button) findViewById(R.id.button_positive);
        btn_nagetive = (Button) findViewById(R.id.button_nagetive);
        tv_title = (TextView) findViewById(R.id.textview_title);
        iv_cut_rule = (ImageView) findViewById(R.id.iv_cut_rule);
        tv_description = (TextView) findViewById(R.id.textview_description);
        btn_positive.setOnClickListener(this);
        btn_nagetive.setOnClickListener(this);
        btn_positive.setVisibility(View.GONE);
        btn_nagetive.setVisibility(View.GONE);
    }

    public CustomDialog(Context context, int cancleColor, int goColor) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        setContentView(R.layout.dialog_common_view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        P = new TsingdaParams(context);
        mEdtPwd = (EditText) findViewById(R.id.edt_pwd);
        btn_positive = (Button) findViewById(R.id.button_positive);
        btn_nagetive = (Button) findViewById(R.id.button_nagetive);
        tv_title = (TextView) findViewById(R.id.textview_title);
        iv_cut_rule = (ImageView) findViewById(R.id.iv_cut_rule);
        tv_description = (TextView) findViewById(R.id.textview_description);
        btn_positive.setOnClickListener(this);
        btn_nagetive.setOnClickListener(this);
        btn_positive.setVisibility(View.GONE);
        btn_nagetive.setVisibility(View.GONE);
        btn_positive.setTextColor(goColor);
        btn_nagetive.setTextColor(cancleColor);
        tv_description.setGravity(Gravity.LEFT);
    }

    /**
     * 设置输入框显示隐藏
     *
     * @param visible
     */
    public void setEditVisible(Boolean visible) {
        if (visible) {
            mEdtPwd.setVisibility(View.VISIBLE);
        } else {
            mEdtPwd.setVisibility(View.GONE);
        }
    }


    /**
     * 设置输入框显示隐藏
     *
     * @param
     */
    public void setEditHint(String hint) {
        mEdtPwd.setHint(hint);
    }

    public void setEditInputType(int type) {
        mEdtPwd.setInputType(type);
    }

    public void setFilters(int length) {
        mEdtPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});;
    }

    public void clearEdit() {
        mEdtPwd.setText("");
    }

    /**
     * 获取输入内容
     *
     * @return
     */
    public String getEditText() {
        if (mEdtPwd != null) {
            return mEdtPwd.getText().toString();
        }
        return "";
    }

    /**
     * @description 设置标题
     * @author xuchangheng
     * @update 2013-6-15 上午9:04:39
     */
    public void setTsingTitle(CharSequence title) {
        P.mTitle = title;
        if (tv_title != null && title != null && !TextUtils.isEmpty(title) && !title.equals("")) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        } else {
            tv_title.setVisibility(View.GONE);
        }
    }

    public void setTextSizeOrColor(float size, int color) {
        tv_description.setTextSize(WebUtil.dp2px(mContext, size));
        tv_description.setTextColor(color);
    }


    /**
     * @description 设置描述语
     * @author xuchangheng
     * @update 2013-6-15 上午9:04:58
     */
    public void setTsingDescrition(CharSequence description) {
        P.mDescription = description;
        if (tv_description != null) {
            tv_description.setVisibility(View.VISIBLE);
            tv_description.setText(description);
        } else {
            tv_description.setVisibility(View.GONE);
        }
    }

    public void setIconCut(boolean isboolean) {
        if (iv_cut_rule != null) {
            if (isboolean) {
                iv_cut_rule.setVisibility(View.VISIBLE);
            } else {
                iv_cut_rule.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @description 设置图标
     * @author xuchangheng
     * @update 2013-6-15 上午9:06:06
     */
    public void setIcon(int resId) {
        P.mIconId = resId;
        if (iv_icon != null) {
            if (resId > 0) {
                iv_icon.setImageResource(resId);
            } else if (resId == 0) {
                iv_icon.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @description 设置图标
     * @author xuchangheng
     * @update 2013-6-15 上午9:06:20
     */
    public void setIcon(Drawable icon) {
        P.mIcon = icon;
        if ((iv_icon != null) && (icon != null)) {
            iv_icon.setImageDrawable(icon);
        }
    }

    /**
     * @description 设置按钮的文字和监听
     * @author xuchangheng
     * @update 2013-6-15 上午9:26:04
     */
    public void setButton(int whichButton, CharSequence text,
                          OnClickListener listener) {

        switch (whichButton) {

            case DialogInterface.BUTTON_POSITIVE:
                P.mPositiveButtonText = text;
                btn_positive.setText(text);
                btn_positive.setVisibility(View.VISIBLE);
                P.mPositiveButtonListener = listener;
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                P.mNegativeButtonText = text;
                btn_nagetive.setText(text);
                btn_nagetive.setVisibility(View.VISIBLE);
                P.mNegativeButtonListener = listener;
                break;

            case DialogInterface.BUTTON_NEUTRAL:
                P.mNeutralButtonText = text;
                btn_neutral.setText(text);
                btn_neutral.setVisibility(View.VISIBLE);
                P.mNeutralButtonListener = listener;
                break;

            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void setHide() {
        btn_nagetive.setVisibility(View.GONE);
    }
    public void setShow() {
        btn_nagetive.setVisibility(View.VISIBLE);
    }


    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_positive) {
            if (P.mPositiveButtonListener != null) {
                P.mPositiveButtonListener.onClick(this,
                        DialogInterface.BUTTON_POSITIVE);
            }
            dismiss();
        } else if (id == R.id.button_nagetive) {
            if (P.mNegativeButtonListener != null) {
                P.mNegativeButtonListener.onClick(this,
                        DialogInterface.BUTTON_NEGATIVE);
            }
            dismiss();
        }
    }

    public void setView(View view) {
        if (view != null && contentView != null) {
            headView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            contentView.addView(view, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    public void setView(int resourceId) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        View view = factory.inflate(resourceId, null);
        setView(view);
    }

    public static class Builder {
        TsingdaParams P;


        public Builder(Context context) {
            P = new TsingdaParams(context);
        }

        public Builder setIcon(int iconId) {
            P.mIconId = iconId;
            return this;
        }

        public Builder setTsingTitle(CharSequence title) {
            P.mTitle = title;
            return this;
        }

        public Builder setTsingDescrition(CharSequence description) {
            P.mDescription = description;
            return this;
        }

        public Builder setPositiveButton(int textId,
                                         final OnClickListener listener) {
            P.mPositiveButtonText = P.mContext.getText(textId);
            P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text,
                                         final OnClickListener listener) {
            P.mPositiveButtonText = text;
            P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId,
                                         final OnClickListener listener) {
            P.mNegativeButtonText = P.mContext.getText(textId);
            P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text,
                                         final OnClickListener listener) {
            P.mNegativeButtonText = text;
            P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(int textId,
                                        final OnClickListener listener) {
            P.mNeutralButtonText = P.mContext.getText(textId);
            P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text,
                                        final OnClickListener listener) {
            P.mNeutralButtonText = text;
            P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setView(int resourceId) {
            P.mViewResourceId = resourceId;
            return this;
        }

        public Builder setView(View view) {
            P.mView = view;
            return this;
        }

        public CustomDialog create() {
            final CustomDialog dialog = new CustomDialog(P.mContext);
            P.apply(dialog);
            /*
             * dialog.setCancelable(P.mCancelable); if (P.mCancelable) {
			 * dialog.setCanceledOnTouchOutside(true); }
			 * dialog.setOnCancelListener(P.mOnCancelListener);
			 * dialog.setOnDismissListener(P.mOnDismissListener); if
			 * (P.mOnKeyListener != null) {
			 * dialog.setOnKeyListener(P.mOnKeyListener); }
			 */
            return dialog;
        }

        public CustomDialog show() {
            CustomDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }

    private static class TsingdaParams {

        public final Context mContext;

        public int mIconId = 0;
        public Drawable mIcon;
        public boolean mCancelable = true;
        public CharSequence mTitle;
        public CharSequence mDescription;
        public CharSequence mPositiveButtonText;
        public OnClickListener mPositiveButtonListener;
        public CharSequence mNegativeButtonText;
        public OnClickListener mNegativeButtonListener;
        public CharSequence mNeutralButtonText;
        public OnClickListener mNeutralButtonListener;

        public View mView;
        public int mViewResourceId;

        public TsingdaParams(Context context) {
            this.mContext = context;
        }

        public void apply(CustomDialog dialog) {
            if (mTitle != null) {
                dialog.setTsingTitle(mTitle);
            }
            if (mIcon != null) {
                dialog.setIcon(mIcon);
            }
            if (mIconId >= 0) {
                dialog.setIcon(mIconId);
            }
            if (mDescription != null) {
                dialog.setTsingDescrition(mDescription);
            }
            if (mPositiveButtonText != null) {
                dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                        mPositiveButtonText, mPositiveButtonListener);
            }
            if (mNegativeButtonText != null) {
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                        mNegativeButtonText, mNegativeButtonListener);
            }
            if (mNeutralButtonText != null) {
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL,
                        mNeutralButtonText, mNeutralButtonListener);
            }
            if (!mCancelable) {
                dialog.setOnKeyListener(new OnKeyListener() {


                    public boolean onKey(DialogInterface dialog, int keyCode,
                                         KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK
                                && event.getAction() == KeyEvent.ACTION_UP) {
                            if (mCancelable) {
                                dialog.dismiss();
                            } else {
                            }
                            return true;
                        }
                        return false;

                    }
                });
            }
            if (mViewResourceId != 0) {
                dialog.setView(mViewResourceId);
            }

            if (mView != null) {
                dialog.setView(mView);
            }

        }

    }

}

