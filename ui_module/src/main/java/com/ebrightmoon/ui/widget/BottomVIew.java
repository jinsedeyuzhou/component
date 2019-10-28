package com.ebrightmoon.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 */
public class BottomVIew extends View {
    public static final String TAG = BottomVIew.class.getSimpleName();

    public float currentX = 80;
    public float currentY = 60;
    private int width; //  测量宽度 FreeView的宽度
    private int height; // 测量高度 FreeView的高度
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




    float downX, downY;
    float moveX, moveY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downX=event.getRawX();
                downY=event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX=event.getRawX();
                moveY=event.getRawY();
                Log.e(TAG,"getTop:"+this.getTop()+"==getLeft():"+getLeft()+"==getRight():"+getRight()+"==getBottom():"+getBottom()+"====moveY-downY:"+(moveY-downY));
                Log.e(TAG,"getX():"+this.getX()+"==getY():"+getY()+"====moveY-downY:"+(moveX-downX));
                Log.e(TAG,"getTranslationX():"+this.getTranslationX()+"==getTranslationY():"+getTranslationY());
                setY(getY()+moveY-downY);
                downY=moveY;
                setX(getX()+moveX-downX);
                downX=moveX;
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }
}
