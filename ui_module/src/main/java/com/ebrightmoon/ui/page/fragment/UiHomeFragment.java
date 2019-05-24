package com.ebrightmoon.ui.page.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebrightmoon.common.base.BaseFragment;
import com.ebrightmoon.common.widget.CustomDialogFragment;
import com.ebrightmoon.common.widget.dialogfragment.IDialog;
import com.ebrightmoon.common.widget.dialogfragment.SystemDialog;
import com.ebrightmoon.common.widget.dialogfragment.manager.DialogWrapper;
import com.ebrightmoon.common.widget.dialogfragment.manager.SystemDialogsManager;
import com.ebrightmoon.common.widget.popwindow.CommonPopupWindow;
import com.ebrightmoon.common.widget.popwindow.CustomPopWindow;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.page.WebViewActivity;

import java.io.IOException;
import java.io.InputStream;

public class UiHomeFragment extends BaseFragment {

    private ProgressBar mPb;
    private TextView mTvShowPercent;
    private Handler handler = new Handler();
    private int i = 1;
    private TextView mTvShowCount;
    private DownProgress downProgress;
    private String fileName;
    private CommonPopupWindow popupWindow;
    private Button mBtnPop;
    private View content;
    private Button mBtnDialog;
    private CustomDialogFragment customDialogFragment;

    public static UiHomeFragment newInstance() {
        UiHomeFragment uiHomeFragment = new UiHomeFragment();
        Bundle bundle = new Bundle();
        uiHomeFragment.setArguments(bundle);
        return uiHomeFragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.ui_fragment_home;
    }

    @Override
    protected void initView(View view) {
        content = view;
        mPb = view.findViewById(R.id.pb);
        mPb.setMax(100);
        mPb.setProgress(0);
        mTvShowPercent = view.findViewById(R.id.tv_show_percent);
        mTvShowCount = view.findViewById(R.id.tv_show_count);
        mBtnPop = view.findViewById(R.id.btn_pop);
        mBtnDialog = view.findViewById(R.id.btn_dialog);
        view.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = 1;
                fileName = System.currentTimeMillis() + ".zip";
                String xml = readAssetsTxt(mContext, "XML");
                AppClient.getInstance(mContext, "http://10.10.68.180:8001").downloadFile("/IDPBootstrap/IDPServletDataProvider", xml, "PZFG201844010000021546.zip", mContext, new ACallback<DownProgress>() {
                    @Override
                    public void onSuccess(DownProgress data) {
                        downProgress = data;
                        i++;
                        mTvShowCount.setText(i + "");
                        if (downProgress != null) {
                            mPb.setProgress((int) (downProgress.getDownloadSize() * 1.0 / downProgress.getTotalSize() * 100));
                            mTvShowPercent.setText("进度：" + downProgress.getPercent());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
            }
        });


        view.findViewById(R.id.btn_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "测试");
                bundle.putString("url", "http://192.168.8.18:8087/EditUrl.html");
                toOtherActivity(WebViewActivity.class, bundle, false);
            }
        });

        view.findViewById(R.id.btn_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemDialog.Builder builder = new SystemDialog.Builder(mContext);
                    builder.setTitle("标题")
                            .setContent("这是内容")
                            .setNegativeButton("取消", new IDialog.OnClickListener() {
                                @Override
                                public void onClick(IDialog dialog) {
                                    dialog.dismiss();

                                }
                            }).setPositiveButton("确认", new IDialog.OnClickListener() {
                        @Override
                        public void onClick(IDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();

            }
        });

        view.findViewById(R.id.btn_dialog_fragment1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SystemDialog.Builder(mContext)
                        .setDialogView(R.layout.layout_dialog)//设置dialog布局
                        .setAnimStyle(R.style.translate_style)//设置动画 默认没有动画
                        .setScreenWidthP(0.85f) //设置屏幕宽度比例 0.0f-1.0f
                        .setGravity(Gravity.CENTER)//设置Gravity
                        .setWindowBackgroundP(0.2f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                        .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                        .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失
                        .setOnDismissListener(new IDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(IDialog dialog) {
                                //监听dialog dismiss的回调
                            }
                        })
                        .setBuildChildListener(new IDialog.OnBuildListener() {
                            //设置子View
                            @Override
                            public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                                //dialog: IDialog
                                //view： DialogView
                                //layoutRes :Dialog的资源文件 如果一个Activity里有多个dialog 可以通过layoutRes来区分
                                final EditText editText = view.findViewById(R.id.et_content);
                                Button btn_ok = view.findViewById(R.id.btn_ok);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String editTextStr = null;
                                        if (!TextUtils.isEmpty(editText.getText())) {
                                            editTextStr = editText.getText().toString();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }).show();
            }
        });

        view.findViewById(R.id.btn_dialog_fragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Build第一个Dialog
                SystemDialog.Builder builder1 = new SystemDialog.Builder(mContext)
                        .setDialogView(R.layout.layout_ad_dialog)
                        .setWindowBackgroundP(0.5f)
                        .setBuildChildListener(new IDialog.OnBuildListener() {
                            @Override
                            public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                                ImageView iv_close = view.findViewById(R.id.iv_close);
                                iv_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                ImageView iv_ad = view.findViewById(R.id.iv_ad);
                                iv_ad.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });

                SystemDialog.Builder builder2 = new SystemDialog.Builder(mContext);
                builder2.setTitle("标题")
                        .setContent("这是内容拉")
                        .setNegativeButton("取消", new IDialog.OnClickListener() {
                            @Override
                            public void onClick(IDialog dialog) {
                                dialog.dismiss();

                            }
                        }).setPositiveButton("确认", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        dialog.dismiss();

                    }
                });

                SystemDialogsManager.getInstance().requestShow(new DialogWrapper(builder1));
                //添加第二个Dialog
                SystemDialogsManager.getInstance().requestShow(new DialogWrapper(builder2));
            }
        });

        customDialogFragment = CustomDialogFragment.newInstance();

        view.findViewById(R.id.btn_dialog_fragment3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogFragment.setContext(mContext)
                        .setTitle("标题")
                        .setContent("这是内容")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialogFragment.dismiss();
                            }
                        }).setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialogFragment.dismiss();
                    }
                }).show();

            }
        });
    }

    /**
     * 点击弹框
     *
     * @param view
     */
    public void btClick1(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_child_menu, null);
        TextView tvLike = (TextView) contentView.findViewById(R.id.tv_like);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        popupWindow = new CommonPopupWindow.Builder(mContext)
                .setView(contentView)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
                .setOutsideTouchable(true)
                .create();
        //根据view的位置设置
        popupWindow.showAsDropDown(view, -popupWindow.getWidth(), -view.getHeight());
    }

    //建议使用
    public void btClick2(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_product_detail_video, null);
        //创建并显示popWindow
        final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .setAnimationStyle(R.style.pop_bottom_anim)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAtLocation(content, Gravity.BOTTOM, 0, 0);

    }

    @Override
    protected void bindEvent() {
        mBtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btClick2(mBtnPop);
            }
        });
        mBtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogFragment newFragment = CustomDialogFragment.newInstance();
                newFragment.show(getChildFragmentManager(), "dialog");
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void processClick(View v) {

    }

    /*

     **
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }


}
