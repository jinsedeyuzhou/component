package com.ebrightmoon.ui.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Time: 2019-08-23
 * Author:wyy
 * Description:
 */
public class CustomViewOnMeasure extends View {
    //设置默认的宽和高
    private static final int DEFUALT_VIEW_WIDTH = 100;
    private static final int DEFUALT_VIEW_HEIGHT = 100;

    public CustomViewOnMeasure(Context context) {
        super(context);
    }

    public CustomViewOnMeasure(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewOnMeasure(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomViewOnMeasure(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 子类测量 主要告诉父类需要的空间
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=measureDimension(DEFUALT_VIEW_WIDTH,widthMeasureSpec);
        int height=measureDimension(DEFUALT_VIEW_HEIGHT,heightMeasureSpec);
        setMeasuredDimension(width,height);

    }

    public int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //1,layout中自定义组件给出来确定的值，比如100dp
        //2,layout中自定义组件使用的是match_parent，但父控件的size已经可以确定了，比如设置的具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;

        //layout中自定义组件使用的wrap_content
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else if (specMode == MeasureSpec.UNSPECIFIED) {
            result = defaultSize;
        }


        return result;

    }
}
