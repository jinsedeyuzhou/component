package com.ebrightmoon.common.permission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;

import io.reactivex.functions.Consumer;
/**
 *   PermissionManager.instance().request(
 *             this,
 *             object : OnPermissionCallback {
 *                 override fun onRequestAllow(permissionName: String) {
 *
 *                 }
 *                 override fun onRequestRefuse(permissionName: String) {
 *
 *                 }
 *                 override fun onRequestNoAsk(permissionName: String) {
 *
 *                 }
 *             },
 *             Manifest.permission.ACCESS_FINE_LOCATION,
 *             Manifest.permission.ACCESS_COARSE_LOCATION
 *         )
 */
public class PermissionManager {

    private static PermissionManager permissionManager;


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



    @SuppressLint("CheckResult")
    public void request(Activity activity, final OnPermissionCallback onPermissionCallback, final String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity != null && onPermissionCallback != null) {
            RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                @SuppressLint("CheckResult")
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
