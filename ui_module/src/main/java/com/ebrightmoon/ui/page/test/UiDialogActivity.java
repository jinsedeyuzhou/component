package com.ebrightmoon.ui.page.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebrightmoon.common.base.BaseActivity;
import com.ebrightmoon.common.widget.CustomDialogFragment;
import com.ebrightmoon.common.widget.dialogfragment.IDialog;
import com.ebrightmoon.common.widget.dialogfragment.SystemDialog;
import com.ebrightmoon.common.widget.dialogfragment.manager.DialogWrapper;
import com.ebrightmoon.common.widget.dialogfragment.manager.SystemDialogsManager;
import com.ebrightmoon.common.widget.popwindow.CommonPopupWindow;
import com.ebrightmoon.common.widget.popwindow.CustomPopWindow;
import com.ebrightmoon.ui.R;
import com.ebrightmoon.ui.page.WebViewActivity;

/**
 * Time: 2019/6/11
 * Author:wyy
 * Description:
 */
public class UiDialogActivity extends BaseActivity {


    private CustomDialogFragment customDialogFragment;
    private CommonPopupWindow popupWindow;
    private Button mBtnPop;
    private Button mBtnDialog;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.ui_activity_dialog);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        mBtnPop = findViewById(R.id.btn_pop);
        mBtnDialog = findViewById(R.id.btn_dialog);
        findViewById(R.id.btn_dialog_fragment).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.btn_dialog_fragment1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SystemDialog.Builder(mContext)
                        .setDialogView(R.layout.ui_layout_dialog)//设置dialog布局
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

       findViewById(R.id.btn_dialog_fragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Build第一个Dialog
                SystemDialog.Builder builder1 = new SystemDialog.Builder(mContext)
                        .setDialogView(R.layout.ui_layout_ad_dialog)
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

      findViewById(R.id.btn_dialog_fragment3).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.btn_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "测试");
                bundle.putString("url", "https://www.baidu.com");
                toOtherActivity(WebViewActivity.class, bundle, false);
            }
        });

    }
    /**
     * 点击弹框
     *
     * @param view
     */
    public void btClick1(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.ui_popup_child_menu, null);
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
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.ui_pop_product_detail_video, null);
        //创建并显示popWindow
        final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.7f)
                .setAnimationStyle(R.style.pop_bottom_anim)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAtLocation(mBtnDialog, Gravity.BOTTOM, 0, 0);

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
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void processClick(View paramView) {

    }
}
