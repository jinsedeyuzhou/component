package com.ebrightmoon.ui.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 */
public class ContentView extends ViewGroup {
    private static final String TAG = ContentView.class.getSimpleName();

    public ContentView(Context context) {
        super(context);
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension(sizeWidth,sizeHeight);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if (layoutWidth < getWidth()) {
                //如果一行没有排满，继续往右排列
                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            } else {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            }

            layoutWidth += childMeasureWidth;  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        View child = getChildAt(0);
        Log.e(TAG, "onInterceptTouchEvent-----getX():" + this.getX() + "==getY():" + getY() + "====moveY-downY:" + (moveX - downX));

        Log.e(TAG, "onInterceptTouchEvent-----getTranslationX():" + child.getTranslationX() + "==getTranslationY():" + child.getTranslationY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                moveY = event.getRawY();
                float y = moveY - downY;
                float x = moveX - downX;

//                Log.e(TAG,"getTop:"+this.getTop()+"==getLeft():"+getLeft()+"==getRight():"+getRight()+"==getBottom():"+getBottom()+"====moveY-downY:"+(moveY-downY));
                Log.e(TAG, "getX():" + this.getX() + "==getY():" + getY() + "====moveY-downY:" + (moveX - downX));
                Log.e(TAG, "getTranslationX():" + child.getTranslationX() + "==getTranslationY():" + child.getTranslationY());
                downY = moveY;
                downX = moveX;
                if (child.getTranslationY() < 0 && y < 0) {
                    return true;
                }
                if (child.getTranslationY() + child.getHeight() > getHeight() && y > 0) {
                    return true;
                }
                if (child.getTranslationX() < 0 && x < 0) {
                    return true;
                }
                if (child.getTranslationX() + child.getWidth() > getWidth() && x > 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

//        View child = getChildAt(0);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downX = event.getRawX();
//                downY = event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveX = event.getRawX();
//                moveY = event.getRawY();
//                float y = moveY - downY;
//                float x = moveX - downX;
//
////                Log.e(TAG,"getTop:"+this.getTop()+"==getLeft():"+getLeft()+"==getRight():"+getRight()+"==getBottom():"+getBottom()+"====moveY-downY:"+(moveY-downY));
//                Log.e(TAG, "getX():" + this.getX() + "==getY():" + getY() + "====moveY-downY:" + (moveX - downX));
//                Log.e(TAG, "getTranslationX():" + child.getTranslationX() + "==getTranslationY():" + child.getTranslationY());
//                downY = moveY;
//                downX = moveX;
//                if (child.getTranslationY() < 0 && y < 0) {
//                    return onTouchEvent(event);
//                }
//                if (child.getTranslationY() + child.getHeight() > getHeight() && y > 0) {
//                    return onTouchEvent(event);
//                }
//                if (child.getTranslationX() < 0 && x < 0) {
//                    return onTouchEvent(event);
//                }
//                if (child.getTranslationX() + child.getWidth() > getWidth() && x > 0) {
//                    return onTouchEvent(event);
//                }
//                return super.dispatchTouchEvent(event);
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return super.dispatchTouchEvent(event);
    }

    float downX, downY;
    float moveX, moveY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View child = getChildAt(0);


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                moveY = event.getRawY();
                Log.e(TAG, "onTouchEvent====getX():" + this.getX() + "==getY():" + getY() + "====moveY-downY:" + (moveY - downY));
                Log.e(TAG, "onTouchEvent====getTranslationX():" + child.getTranslationX() + "==getTranslationY():" + child.getTranslationY());

                setY(getY() + moveY - downY);
                downY = moveY;

                setX(getX() + moveX - downX);
                downX = moveX;


                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }
}
