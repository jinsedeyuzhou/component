package com.ebrightmoon.ui.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 */
public class ContentView extends ViewGroup {
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
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);

//        setMeasuredDimension(sizeWidth,sizeHeight);
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
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
        for(int i = 0; i<count; i++){
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if(layoutWidth<getWidth()){
                //如果一行没有排满，继续往右排列
                left = layoutWidth;
                right = left+childMeasureWidth;
                top = layoutHeight;
                bottom = top+childMeasureHeight;
            } else{
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left+childMeasureWidth;
                top = layoutHeight;
                bottom = top+childMeasureHeight;
            }

            layoutWidth += childMeasureWidth;  //宽度累加
            if(childMeasureHeight>maxChildHeight){
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    private int width; //  测量宽度 FreeView的宽度
    private int height; // 测量高度 FreeView的高度
    private int maxWidth; // 最大宽度 window 的宽度
    private int maxHeight; // 最大高度 window 的高度
    private float downX;
    private float downY;
    private Rect rect;
    private String text;
    private Paint paint;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                final float moveX = event.getX() - downX;
                final float moveY = event.getY() - downY;
                int l, r, t, b; // 上下左右四点移动后的偏移量
                //计算偏移量 设置偏移量 = 3 时 为判断点击事件和滑动事件的峰值
                if (Math.abs(moveX) > 3 || Math.abs(moveY) > 3) { // 偏移量的绝对值大于 3 为 滑动时间 并根据偏移量计算四点移动后的位置
                    l = (int) (getLeft() + moveX);
                    r = l + width;
                    t = (int) (getTop() + moveY);
                    b = t + height;
                    //不划出边界判断,最大值为边界值
                    // 如果你的需求是可以划出边界 此时你要计算可以划出边界的偏移量 最大不能超过自身宽度或者是高度  如果超过自身的宽度和高度 view 划出边界后 就无法再拖动到界面内了 注意
                    if (l < 0) { // left 小于 0 就是滑出边界 赋值为 0 ; right 右边的坐标就是自身宽度 如果可以划出边界 left right top bottom 最小值的绝对值 不能大于自身的宽高
                        l = 0;
                        r = l + width;
                    } else if (r > maxWidth) { // 判断 right 并赋值
                        r = maxWidth;
                        l = r - width;
                    }
                    if (t < 0) { // top
                        t = 0;
                        b = t + height;
                    } else if (b > maxHeight) { // bottom
                        b = maxHeight;
                        t = b - height;
                    }
                    this.layout(l, t, r, b); // 重置view在layout 中位置
                } else {
                }
                break;
            case MotionEvent.ACTION_UP:
                postInvalidate();
                break;
        }
        return true;
    }
}
