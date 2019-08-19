package com.ebrightmoon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 */
public class BottomVIew extends View {
    private int width; //  测量宽度 FreeView的宽度
    private int height; // 测量高度 FreeView的高度
    private int maxWidth; // 最大宽度 window 的宽度
    private int maxHeight; // 最大高度 window 的高度
    private float downX;
    private float downY;
    private Rect rect;
    private String text;
    private Paint paint;

    public BottomVIew(Context context) {
        super(context);
        init();
    }

    public BottomVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BottomVIew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        text = "A";
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(18f);
        rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

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
                break;
        }
        return true;
    }
}
