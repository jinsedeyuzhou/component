package com.ebrightmoon.common.base.mvc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        mActivity = (Activity) activity;
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
        mConvertView = inflater.inflate(getLayoutID(), container, false);
        return mConvertView;
    }

    /**
     * 布局的LayoutID
     *
     * @return
     */
    protected abstract int getLayoutID();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindEvent();
        initData(savedInstanceState);
        // 暂时没有用
//        boolean isVis = isHidden() || getUserVisibleHint();
//        if (isVis && mIsFirstVisible) {
//            lazyLoad();
//            mIsFirstVisible = false;
//        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化子View
     *
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
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 点击事件
     *
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
    protected void lazyLoad() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d(TAG, "onHiddenChanged"+hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(TAG, "setUserVisibleHint"+isVisibleToUser);
        if (isResumed())
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

    @Override
    public void onResume() {
        super.onResume();
        // onResume并不代表fragment可见
        // 如果是在viewpager里，就需要判断getUserVisibleHint，不在viewpager时，getUserVisibleHint默认为true
        // 如果是其它情况，就通过isHidden判断，因为show/hide时会改变isHidden的状态
        // 所以，只有当fragment原来是可见状态时，进入onResume就回调onVisible
        if (getUserVisibleHint() && !isHidden()) {
            onVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // onPause时也需要判断，如果当前fragment在viewpager中不可见，就已经回调过了，onPause时也就不需要再次回调onInvisible了
        // 所以，只有当fragment是可见状态时进入onPause才加调onInvisible
        if (getUserVisibleHint() && !isHidden()) {
            onInVisible();
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
