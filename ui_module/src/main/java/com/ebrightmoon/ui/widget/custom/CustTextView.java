package com.ebrightmoon.ui.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ebrightmoon.ui.R;

/**
 * Time: 2019-08-23
 * Author:wyy
 * Description:
 */
public class CustTextView extends View {

    // 画笔
    private Paint mPaint;
    // 内容大小
    private String mText;
    // 文字大小
    private int mTextSize;
    // 文字颜色
    private int mTextColor;
    // 绘制范围
    private Rect mBound;
    //绘制坐标
    private float width,height;

    public CustTextView(Context context) {
        this(context,null);
    }

    public CustTextView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.v("CustTextView","CustTextView");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustTextView, defStyleAttr, 0);
        mText=typedArray.getString(R.styleable.CustTextView_text);
        mTextColor=typedArray.getColor(R.styleable.CustTextView_textColor, Color.BLACK);
        mTextSize= (int) typedArray.getDimension(R.styleable.CustTextView_textSize,100);
        typedArray.recycle();
        init();
    }

    /**
     * 初始化画笔等参数
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        mBound=new Rect();
        mPaint.getTextBounds(mText,0,mText.length(),mBound);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v("CustTextView","onDraw");
        width=getWidth()/2-mBound.width()/2;
        height=getHeight()/2+mBound.height()/2;
        canvas.drawText(mText,width,height,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.v("CustTextView","onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);


        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width=10,height=10; //计算自定义View最终的宽和高

        if (widthMode==MeasureSpec.EXACTLY)
        {
            width=widthSize;
        }else if (widthMode==MeasureSpec.AT_MOST)
        {
            float textWidth=mBound.width();
            width= (int) (getPaddingLeft()+textWidth+getPaddingRight());
        }

        if (heightMode==MeasureSpec.EXACTLY)
        {
            height=heightSize;
        }else if (heightMode==MeasureSpec.AT_MOST)
        {
            float textHeight=mBound.height();
            height= (int) (getPaddingTop()+textHeight+getPaddingBottom());
        }
        setMeasuredDimension(width,height);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.v("CustTextView","onFinishInflate");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.v("CustTextView","dispatchDraw");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.v("CustTextView","onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.v("CustTextView","onSizeChanged");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.v("CustTextView","onAttachedToWindow");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.v("CustTextView","onWindowFocusChanged");
    }
}
