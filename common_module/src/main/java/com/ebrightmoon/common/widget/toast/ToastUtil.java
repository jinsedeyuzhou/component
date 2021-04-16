package com.ebrightmoon.common.widget.toast;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 作者：create by  Administrator on 2019/3/7
 * 下图是系统INotificationManager的实现源码 当pkg = “android”时不会校验通知关闭逻辑
 所以只需要hook INotificationManager 将参数pkg始终传“android” 就行了
 */
public class ToastUtil {


        private static Object iNotificationManagerObj;

        /**
         * @param context
         * @param message
         */
        public static void show(Context context, String message) {
            show(context, message, Toast.LENGTH_SHORT, Gravity.BOTTOM);
        }

        /**
         *
         * @param context
         * @param message
         */
        public static void showCenter(Context context, String message) {
            show(context, message, Toast.LENGTH_SHORT, Gravity.CENTER);
        }

        /**
         * @param context
         * @param message
         */
        public static void show(Context context, String message, int duration, int gravity) {
            if (TextUtils.isEmpty(message)) {
                return;
            }
            //后setText 兼容小米默认会显示app名称的问题
            Toast toast = Toast.makeText(context, null, duration);
            toast.setText(message);
            toast.setGravity(gravity, 0, 0);
            if(isNotificationEnabled(context)){
                toast.show();
            }else{
                CustomToast.makeText(context,message,duration).show();
            }
        }

        /**
         * 业务相关的toast
         * @param context
         * @param message
         */
        public static void showCenterForBusiness(final Context context, final String message) {
            if (context != null) {
                if (context instanceof Activity) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show(context, message, Toast.LENGTH_SHORT, Gravity.CENTER);
                        }
                    });
                } else {
                    show(context, message, Toast.LENGTH_SHORT, Gravity.CENTER);
                }
            }
        }

        /**
         * 消息通知是否开启
         * @return
         */
        private static boolean isNotificationEnabled(Context context) {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
            return areNotificationsEnabled;
        }

}
