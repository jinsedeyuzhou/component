package com.ebrightmoon.common.base.mvvm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ebrightmoon.common.R;
import com.ebrightmoon.common.base.mvc.ContainerActivity;
import com.ebrightmoon.common.base.mvvm.BaseViewModel;
import com.ebrightmoon.common.ebus.BusManager;
import com.ebrightmoon.common.ebus.IEvent;
import com.ebrightmoon.common.ebus.Messenger;
import com.ebrightmoon.common.util.AppManager;
import com.ebrightmoon.common.util.ScreenAdapter;
import com.ebrightmoon.common.util.StatusBarUtils;
import com.ebrightmoon.common.widget.LoadingDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


public abstract class MvvmActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView,View.OnClickListener {
    private static final String TAG = "BaseActivity";
    protected Context mContext;
    protected Activity mActivity;
    protected int screenWidth, screenHeight;
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private LoadingDialog mLoadingDialog;



    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        AppManager.getAppManager().addActivity(this);
        ScreenAdapter.setCustomDensity(getApplication());
        if (isNeedAnimation()) {
            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
        }
        // 软件盘模式
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //默认取消所有title，可使用自定义title。
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = this;
        this.mActivity = this;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        initParam();
        initViewDataBinding(paramBundle);
        registorUIChangeLiveDataCallBack();
        initViewObservable();
        initData();
        viewModel.registerRxBus();
    }




    protected void show() {
        if (mLoadingDialog==null) {
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
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                show();
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
               dismiss();
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });
    }
    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, getLayoutId());
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
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return new ViewModelProvider(activity).get(cls);
    }


    /**
     * 点击事件
     * @param view
     */
    public void onClick(View view) {
        if (view.getId() == 0) {
        }
        processClick(view);
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

    protected abstract int getLayoutId();


    protected boolean isNeedAnimation() {
        return true;
    }


    @Override
    public void finish() {
        super.finish();
        if (isNeedAnimation()) {
            overridePendingTransition(R.anim.close_enter_anim, R.anim.close_exit_anim);
        }

    }

    //Rxbus注解
    //eventBus 注解 选择对应的否则不生效
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IEvent event) {

        /* Do something */

    }


    @Override
    protected void onStart() {
        if (isRegisterEvent()) {
            BusManager.getBus().register(this);
        }
        super.onStart();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isRegisterEvent()) {
            BusManager.getBus().unregister(this);
        }

    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideSoftKeyBoard(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return super.dispatchTouchEvent(ev);
            }
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            } else {
                return onTouchEvent(ev);
            }
        } catch (Exception e) {
            return super.dispatchTouchEvent(ev);
        }
    }
    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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

    public abstract void processClick(View paramView);

    protected boolean isRegisterEvent() {
        return false;
    }

    protected <E extends View> E F(@IdRes int viewId) {
        return (E) super.findViewById(viewId);
    }

    protected <E extends View> E F(@NonNull View view, @IdRes int viewId) {
        return (E) view.findViewById(viewId);
    }

    protected <E extends View> void C(@NonNull E view) {
        view.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void base() {
        StatusBarUtils.darkMode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if(binding != null){
            binding.unbind();
        }
        AppManager.getAppManager().finishActivity(this);
    }
    /**
     * 判断在什么情况下隐藏软键盘，点击EditText视图显示软键盘
     *
     * @param view  Incident event
     * @param event
     * @return
     */
    public static boolean isShouldHideSoftKeyBoard(View view, MotionEvent event) {
        if (view instanceof EditText) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left
                    + view.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // if the focus is EditText,ignore it;
        return false;
    }

}
