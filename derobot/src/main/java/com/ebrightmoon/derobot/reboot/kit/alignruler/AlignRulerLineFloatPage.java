package com.ebrightmoon.derobot.reboot.kit.alignruler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ebrightmoon.derobot.R;
import com.ebrightmoon.derobot.reboot.constant.PageTag;
import com.ebrightmoon.derobot.reboot.ui.alignruler.AlignLineView;
import com.ebrightmoon.derobot.reboot.ui.base.BaseFloatPage;
import com.ebrightmoon.derobot.reboot.ui.base.FloatPageManager;

/**
 *  on 2018/11/13.
 */

public class AlignRulerLineFloatPage extends BaseFloatPage implements AlignRulerMarkerFloatPage.OnAlignRulerMarkerPositionChangeListener {
    private AlignRulerMarkerFloatPage mMarker;
    private AlignLineView mAlignInfoView;

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mMarker = (AlignRulerMarkerFloatPage) FloatPageManager.getInstance().getFloatPage(PageTag.PAGE_ALIGN_RULER_MARKER);
        mMarker.addPositionChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMarker.removePositionChangeListener(this);
    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_align_ruler_line, view, false);
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mAlignInfoView = findViewById(R.id.info_view);
    }

    @Override
    public void onPositionChanged(int x, int y) {
        mAlignInfoView.showInfo(x, y);
    }

    @Override
    public void onEnterForeground() {
        super.onEnterForeground();
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onEnterBackground() {
        super.onEnterBackground();
        getRootView().setVisibility(View.GONE);
    }
}