package com.ebrightmoon.common.permission;

import android.app.Activity;
import android.os.Build;

import io.reactivex.functions.Consumer;

public class PermissionManager {

    private static PermissionManager permissionManager;
    private Activity activity;

    private PermissionManager() {

    }

    public static PermissionManager instance() {
        if (permissionManager == null) {
            synchronized (PermissionManager.class) {
                if (permissionManager == null) {
                    permissionManager = new PermissionManager();
                }
            }
        }
        return permissionManager;
    }

    public PermissionManager with(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void request(Activity mActivity,final OnPermissionCallback onPermissionCallback, final String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.activity != null && onPermissionCallback != null) {
            RxPermissions rxPermissions = new RxPermissions(this.activity);
            rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        onPermissionCallback.onRequestAllow(permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        onPermissionCallback.onRequestRefuse(permission.name);
                    } else {
                        onPermissionCallback.onRequestNoAsk(permission.name);
                    }
                }
            });
        }
    }
}
