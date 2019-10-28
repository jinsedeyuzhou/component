package com.ebrightmoon.ui.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Time: 2019-08-24
 * Author:wyy
 * Description:
 */
public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        super(context);
        Log.v("CustomViewGroup","CustomViewGroup");
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v("CustomViewGroup","CustomViewGroup");
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.v("CustomViewGroup","CustomViewGroup");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v("CustomViewGroup","onMeasure");
        //测量 所有的子控件的宽和高
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v("CustomViewGroup","onLayout");
        int mViewGroupWidth=getMeasuredWidth(); // 当前ViewGroup的总宽度

        int mPainterPosX=l; // 当前绘制光标X坐标
        int mPainterPosY=t; // 当前绘制光标Y坐标

        int childCount=getChildCount(); //子控件的数量
        //遍历所有子控件，并在其位置上绘制子控件
        for (int i=0;i<childCount;i++)
        {
            View childView=getChildAt(i);
            // 子控件的宽和高
            int width=childView.getMeasuredWidth();
            int height=childView.getMeasuredHeight();

            CustomViewGroup.LayoutParams params= (LayoutParams) childView.getLayoutParams();


            //如果剩余控件不够，则移到下一行开始位置
            if (mPainterPosX+width+params.leftMargin+params.rightMargin>mViewGroupWidth)
            {
                mPainterPosX=l;
                mPainterPosY+=height+params.topMargin+params.bottomMargin;
            }
            // 执行childView 的绘制
            childView.layout(mPainterPosX+params.leftMargin,mPainterPosY+params.topMargin,mPainterPosX+width+params.leftMargin+params.rightMargin,mPainterPosY+height+params.topMargin+params.bottomMargin);

            // 下一次绘制的X坐标
            mPainterPosX+=width+params.leftMargin+params.rightMargin;
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.v("CustomViewGroup","onFinishInflate");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.v("CustomViewGroup","dispatchDraw");
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.v("CustomViewGroup","onSizeChanged");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v("CustomViewGroup","onDraw");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.v("CustomViewGroup","onAttachedToWindow");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.v("CustomViewGroup","onWindowFocusChanged");
    }

    public static class LayoutParams extends  ViewGroup.MarginLayoutParams
    {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new  CustomViewGroup.LayoutParams(getContext(),attrs);
    }
}
