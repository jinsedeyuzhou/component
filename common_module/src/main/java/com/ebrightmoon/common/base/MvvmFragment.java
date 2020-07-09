package com.ebrightmoon.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ebrightmoon.common.ebus.BusManager;
import com.ebrightmoon.common.ebus.Messenger;
import com.ebrightmoon.common.util.LogUtils;
import com.ebrightmoon.common.widget.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


public abstract class MvvmFragment<V extends ViewDataBinding, VM extends BaseViewModel>
        extends RxFragment implements View.OnClickListener {
    private static final String TAG = "BaseFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected View mConvertView;
    protected Context mContext;
    protected Resources mResources;
    protected LayoutInflater mInflater;
    private boolean mIsRegisterEvent = false;
    protected boolean mIsFirstVisible = true;
    protected Activity mActivity;
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private LoadingDialog mLoadingDialog;

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
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        initParam();
    }

    protected void initParam() {

    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewDataBinding();
        registorUIChangeLiveDataCallBack();
        initData(savedInstanceState);
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }

    protected void initViewObservable() {
    }


    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }


    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                show();
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismiss();
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return new ViewModelProvider(fragment).get(cls);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 初始化View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    protected abstract int initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public boolean isBackPressed() {
        return false;
    }

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

    protected void show() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mActivity, "");
            mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mLoadingDialog.setCancelable(false);
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }

    }

    protected void dismiss() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
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
        if (v.getId() == 0) {
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
        LogUtils.d(TAG, "onHiddenChanged" + hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(TAG, "setUserVisibleHint" + isVisibleToUser);
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

    protected void base() {

    }

    public boolean isRegisterEvent() {
        return mIsRegisterEvent;
    }

    public MvvmFragment setRegisterEvent(boolean mIsRegisterEvent) {
        this.mIsRegisterEvent = mIsRegisterEvent;
        return this;
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

}
