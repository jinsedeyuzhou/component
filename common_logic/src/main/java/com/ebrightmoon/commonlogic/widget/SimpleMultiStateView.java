package com.ebrightmoon.commonlogic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ebrightmoon.commonlogic.R;

/**
 * Time: 2020-04-09
 * Author:wyy
 * Description:
 */
public class SimpleMultiStateView extends FrameLayout {

    private Context mContext;
    private ImageView iv_norder_data;
    private View view;
    private TextView tv_norder_data;
    private TextView tv_retry;
    private ViewState viewState = ViewState.NORMAL;


    public SimpleMultiStateView(@NonNull Context context) {
        this(context, null);

    }

    public SimpleMultiStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleMultiStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.icommon_empty, this, true);
        iv_norder_data = view.findViewById(R.id.iv_norder_data);
        tv_norder_data = view.findViewById(R.id.tv_norder_data);
        tv_retry = view.findViewById(R.id.tv_retry);
        tv_retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateViewListener != null) {
                    stateViewListener.onClick(v);
                }
            }
        });

        setStateView(viewState);
    }

    /**
     * 设置状态
     *
     * @param state
     */
    public void setStateView(ViewState state) {
        if (state == ViewState.EMPTY) {
            iv_norder_data.setImageResource(R.drawable.empty);
            tv_norder_data.setText("暂时没有数据");
            tv_retry.setText("重试");
            view.setVisibility(View.VISIBLE);

        } else if (state == ViewState.ERROR) {
            iv_norder_data.setImageResource(R.drawable.error);
            tv_norder_data.setText("暂时没有数据");
            tv_retry.setText("重试");
            view.setVisibility(View.VISIBLE);

        } else if (state == ViewState.NO_NetWork) {
            iv_norder_data.setImageResource(R.drawable.timeout);
            tv_norder_data.setText("没有网络");
            tv_retry.setText("重试");
            view.setVisibility(View.VISIBLE);

        } else {
            view.setVisibility(View.GONE);
        }
        viewState = state;
    }

    /**
     * 获取当前状态
     *
     * @return
     */
    public ViewState getViewState() {
        return viewState;
    }

    public enum ViewState {
        NO_NetWork,
        ERROR,
        EMPTY,
        NORMAL
    }

    private OnStateViewListener stateViewListener;

    public interface OnStateViewListener {
        void onClick(View view);
    }

    public void setOnStateViewListener(OnStateViewListener stateViewListener) {
        this.stateViewListener = stateViewListener;
    }

}
