package com.ebrightmoon.webviewlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.ebrightmoon.webviewlib.R;

/**
 * Time: 2020-04-13
 * Author:wyy
 * Description:
 */
public class ToolsBar extends FrameLayout {
    private OnTitleBarClickListener mListener;
    private TextView mTitle;
    private ImageView mIcon;

    public ToolsBar(@NonNull Context context) {
        this(context,null);
    }

    public ToolsBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ToolsBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.title_toolsbar, this, true);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ToolsBar);
        int icon = a.getResourceId(R.styleable.ToolsBar_icon, 0);
        String title = a.getString(R.styleable.ToolsBar_title);
        a.recycle();

        mIcon = findViewById(R.id.icon);
        mTitle = findViewById(R.id.title);

        mIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick();
                }
            }
        });

        setTitle(title);
        setIcon(icon);
    }

    /**
     * TitleBar 点击事件回调
     */
    public interface OnTitleBarClickListener {
        void onRightClick();
    }

    public void setTitle(@StringRes int title) {
        setTitle(getResources().getString(title));
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTitle.setText("");
        } else {
            mTitle.setText(title);
            mTitle.setAlpha(0);
            mTitle.setVisibility(View.VISIBLE);
            mTitle.animate().alpha(1).start();
        }
    }

    public void setIcon(@DrawableRes int id) {
        if (id == 0) {
            return;
        }
        mIcon.setImageResource(id);
        mIcon.setVisibility(View.VISIBLE);
    }

    public void setListener(OnTitleBarClickListener listener) {
        mListener = listener;
    }
}
