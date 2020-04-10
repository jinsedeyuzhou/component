package com.ebrightmoon.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ebrightmoon.common.R;

/**
 *   customDialogFragment = CustomDialogFragment.newInstance();
 *                 customDialogFragment.setContext(mContext)
 *                         .setTitle("标题")
 *                         .setContent("这是内容")
 *                         .setNegativeButton("取消", new View.OnClickListener() {
 *                             @Override
 *                             public void onClick(View view) {
 *                                 customDialogFragment.dismiss();
 *                             }
 *                         }).setPositiveButton("确认", new View.OnClickListener() {
 *                     @Override
 *                     public void onClick(View view) {
 *                         customDialogFragment.dismiss();
 *                     }
 *                 }).show();
 */

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String DIALOG_FRAGMENT_TITLE = "dialog_fragment_title";
    public static final String DIALOG_FRAGMENT_CONTENT = "dialog_fragment_title";
    private static final String TAG =CustomDialogFragment.class.getSimpleName() ;
    private Button btnCancel;
    private Button btnOk;
    private TextView dialogTitle;
    private TextView dialogContent;
    private String title;
    private String leftTitle;
    private String rightTitle;
    private String content;
    private Context mContext;
    private FragmentManager supportFragmentManager;
    private View.OnClickListener onneClickListener;
    private View.OnClickListener onpeClickListener;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) return;
        Window window = dialog.getWindow();
        if (window == null) return;
        //设置背景色透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置Dialog动画效果


        WindowManager.LayoutParams params = window.getAttributes();
        //设置Dialog的Width
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置Dialog的Height
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置屏幕透明度 0.0f~1.0f(完全透明~完全不透明)
        params.dimAmount = 0.2f;
        params.gravity = Gravity.CENTER;
        params.windowAnimations = R.style.translate_style;
        window.setAttributes(params);
    }

    public static CustomDialogFragment newInstance(String title, String msg) {
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString(DIALOG_FRAGMENT_TITLE, title);
        args.putString(DIALOG_FRAGMENT_CONTENT, msg);
        frag.setArguments(args);
        return frag;
    }

    public static CustomDialogFragment newInstance() {
        return new CustomDialogFragment();
    }

    /**
     * The system calls this to get the DialogFragment's toolbar, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the toolbar to use as dialog or embedded fragment
        return inflater.inflate(R.layout.layout_dialog_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //如果isCancelable()是false 则会屏蔽物理返回键
            dialog.setCancelable(isCancelable());
            //如果isCancelableOutside()为false 点击屏幕外Dialog不会消失；反之会消失
            dialog.setCanceledOnTouchOutside(true);
            //如果isCancelable()设置的是false 会屏蔽物理返回键
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && !isCancelable();
                }
            });
        }
        init(view);
    }

    /**
     *
     */
    private void init(View view) {

        if (getArguments() != null) {
            title = getArguments().getString(DIALOG_FRAGMENT_TITLE, "标题");
            content = getArguments().getString(DIALOG_FRAGMENT_CONTENT, "这是内容");
        }
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnOk = view.findViewById(R.id.btn_ok);

        if (onneClickListener!=null)
        {
            btnCancel.setOnClickListener(onneClickListener);
        }else
        {
            btnCancel.setOnClickListener(this);

        }
        if (onpeClickListener!=null)
        {
            btnOk.setOnClickListener(onpeClickListener);
        }else
        {
            btnOk.setOnClickListener(this);
        }
        dialogTitle = view.findViewById(R.id.dialog_title);
        dialogContent = view.findViewById(R.id.dialog_content);
        if (title != null)
            dialogTitle.setText(title);
        if (content != null)
            dialogContent.setText(content);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            dismiss();
        } else if (view.getId() == R.id.btn_ok) {
            dismiss();
        }
    }

    public void show() {
        if (mContext == null) {
            throw new NullPointerException("context can not null");
        }
        supportFragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        removePreDialog();
        show(supportFragmentManager, TAG);
    }

    public CustomDialogFragment setTitle(String title) {
        if (title != null)
            this.title = title;
        return this;
    }

    @NonNull
    public CustomDialogFragment setContent(String content) {
        if (content != null)
            this.content = content;
        return this;
    }

    public CustomDialogFragment setNegativeButton(String title, View.OnClickListener onClickListener) {
        if (title != null)
            this.leftTitle = title;
        if (onClickListener != null)
            onneClickListener = onClickListener;
        return this;
    }

    public CustomDialogFragment setPositiveButton(String title, View.OnClickListener onClickListener) {
        if (title != null)
            this.rightTitle = title;
        if (onClickListener != null)
            onpeClickListener = onClickListener;
        return this;
    }

    public CustomDialogFragment setContext(Context context)
    {
        this.mContext=context;
        return  this;
    }

    /**
     * 移除之前的dialog
     */
    private void removePreDialog() {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        Fragment prev = supportFragmentManager.findFragmentByTag(TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}
