package com.ebrightmoon.common.service;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ebrightmoon.common.widget.CustomDialog;


/**
 * Created by wyy on 2017/4/25.
 */

public class UpdateManager {
    /**
     * 自定义升级弹窗
     */
    protected void showUpdateDialog(final Context mContext, final String url) {
        final CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle("发现新版本");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(mContext, UpdataService.class);
                Bundle bundle = new Bundle();
                intent.putExtra("downloadLink", url);
                intent.putExtra("version",bundle);
                mContext.startService(intent);
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    public void checkVersion(final Context context)
    {
//        VersionRequest<Version> versionRequest=new VersionRequest<Version>(RRHPConfig.URL_APK_UPDATE,Version.class, new Response.Listener<Version>() {
//            @Override
//            public void onResponse(Version response) {
//                if (response.getVersionCode()> AppInfoUtils.getVersionCode(context))
//                {
//                    showUpdateDialog(context,response);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        versionRequest.setTag("version");
//        RequestQueueManager.getInstance(context).addToRequestQueue(versionRequest);
    }
}
