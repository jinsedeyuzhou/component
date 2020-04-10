package com.ebrightmoon.common.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.ebrightmoon.common.R;
import com.ebrightmoon.common.common.CommonBaseActivity;
import com.ebrightmoon.common.ebus.BusManager;
import com.ebrightmoon.common.ebus.IEvent;
import com.ebrightmoon.retrofitrx.recycle.ActivityLifeCycleEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;


public abstract class BaseActivity
        extends CommonBaseActivity {
    private static final String TAG = "BaseActivity";
    protected BaseApplication app;
    protected Context mContext;
    protected Activity mActivity;
    protected int screenWidth, screenHeight;
    private Toolbar mToolbar;

    public abstract void initData();

    public abstract void initView();

    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public boolean isScreenLocked() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();

        if (isScreenOn)
            return false;
        else
            return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 0:

                break;

            default:
                break;
        }
        processClick(view);
    }


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        if (isNeedAnimation()) {
            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
        }

        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);


        // 软件盘模式
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //默认取消所有title，可使用自定义title。
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        this.mContext = this;
        this.mActivity = this;
        this.app = ((BaseApplication) getApplication());

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 初始化titlebar
     */
    protected void initToolsBar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (mToolbar!=null) {
            mToolbar.setTitle(title);
            //设置标题后需要调用 如下方法 否则不显示
            setSupportActionBar(mToolbar);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
                onBackPressed();
        } else if (id == R.id.share) {//TODO search
            actionShare();
        } else if (id == R.id.settings) {
            actionSettings();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 分享跳转
     */
    protected void actionSettings() {

    }

    /**
     * 设置跳转
     */
    protected void actionShare() {

    }

    private boolean isNeedAnimation() {
        return true;
    }

//    protected abstract boolean isNeedAnimation();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolsBar();
        initView();
        bindEvent();
        initData();

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolsBar();
        initView();
        bindEvent();
        initData();
    }

    @Override
    public void finish() {
        super.finish();
        if (isNeedAnimation()) {
            overridePendingTransition(R.anim.close_enter_anim, R.anim.close_exit_anim);
        }

    }

    //Rxbus注解
    @com.ebrightmoon.common.ebus.Subscribe(threadMode = com.ebrightmoon.common.ebus.inner.ThreadMode.MAIN_THREAD)
    //eventBus 注解 选择对应的否则不生效
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(IEvent event) {

        /* Do something */

    }

    protected abstract void bindEvent();

    @Override
    protected void onStart() {
        if (isRegisterEvent()) {
            BusManager.getBus().register(this);
        }
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START);

        super.onStart();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 也可以在OnDestory 中解除注册
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

    public abstract void processClick(View paramView);

    public boolean isRegisterEvent() {
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


    public static final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();


    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }


    public static <T> ObservableTransformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
                            @Override
                            public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) throws Exception {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });


                return upstream.takeUntil(compareLifecycleObservable);
            }


        };
    }

}
