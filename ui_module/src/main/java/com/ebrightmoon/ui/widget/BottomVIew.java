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
    public float currentX = 80;
    public float currentY = 60;

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
        paint = new Paint();
        paint.setColor(Color.DKGRAY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(currentX,  currentY,30, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        currentX=width/2;
        currentY=height/2;
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
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
        currentX=event.getX();
        currentY=event.getY();
        invalidate();
        return true;
    }
}
