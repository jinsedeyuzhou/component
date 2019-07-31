package com.ebrightmoon.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebrightmoon.common.common.CommonBaseFragment;
import com.ebrightmoon.common.ebus.BusManager;
import com.ebrightmoon.common.util.LogUtils;


public abstract class BaseFragment
        extends CommonBaseFragment implements View.OnClickListener {
    private static final String TAG = "BaseFragment";
    protected View mConvertView;
    protected Context mContext;
    protected Resources mResources;
    protected LayoutInflater mInflater;
    private boolean mIsRegisterEvent = false;
    protected boolean mIsFirstVisible = true;
    protected Activity mActivity;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity= (Activity) activity;
        mResources = mContext.getResources();
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View getRootView() {
        return mConvertView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreateView");
        mConvertView = inflater.inflate(getLayoutID(), container, false);
        return mConvertView;
    }

    /**
     * 布局的LayoutID
     * @return
     */
    protected abstract int getLayoutID();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindEvent();
        initData(savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化子View
     * @param view
     * @return
     */
    protected abstract void initView(View view);


    @Override
    public void onStart() {
        if (isRegisterEvent()) {
            BusManager.getBus().register(this);
        }
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (isRegisterEvent()) {
            BusManager.getBus().unregister(this);
        }
    }

    /**
     * @param clazz   目标类
     * @param bundle  参数
     * @param isLogin 是否需要判断登录状态  true需要  false 不需要
     */
    protected void toOtherActivity(Class clazz, Bundle bundle, boolean isLogin) {
        if (!isLogin) {
            Intent intent = new Intent(mActivity, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            mActivity.startActivity(intent);
        } else {
            if (isLogin()) {
                Intent intent = new Intent(mActivity, clazz);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                mActivity.startActivity(intent);
            }
        }

    }

    /**
     * 判断是否需要登录
     *
     * @return
     */
    private boolean isLogin() {

        return false;
    }

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();
    /**
     * 填充数据
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);
    /**
     * 点击事件
     * @param v
     */
    protected abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:

                break;

            default:
                break;
        }
        processClick(v);
    }

    /**
     * 数据懒加载
     */
    protected  void lazyLoad(){

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }



    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }

    protected <E extends View> E F(@IdRes int viewId) {
        if (mConvertView == null) {
            return null;
        }
        return (E) mConvertView.findViewById(viewId);
    }

    protected <E extends View> E F(@NonNull View view, @IdRes int viewId) {
        return (E) view.findViewById(viewId);
    }

    protected <E extends View> void C(@NonNull E view) {
        view.setOnClickListener(this);
    }


    public boolean isRegisterEvent() {
        return mIsRegisterEvent;
    }

    public BaseFragment setRegisterEvent(boolean mIsRegisterEvent) {
        this.mIsRegisterEvent = mIsRegisterEvent;
        return this;
    }

}
