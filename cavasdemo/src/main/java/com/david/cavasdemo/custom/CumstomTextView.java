package com.david.cavasdemo.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.david.cavasdemo.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class CumstomTextView extends View {
    private Paint textPaint;          // 创建画笔
    private int mWidth;
    private int mHeight;
    private String text = "old";
    private int mTextSize;

    /**
     * 距离顶部距离
     */
    private int marginTop;


    public CumstomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public CumstomTextView(Context context) {
        super(context);
    }

    public CumstomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CumstomTextView);
        text = typedArray.getString(R.styleable.CumstomTextView_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CumstomTextView_textSize,dp2px(context,13));
        Log.e("info", "mWidth");
        Log.e("info", "mHeight");
        Log.e("info", "mTextSize" + mTextSize);
        Log.e("info", "dp2px((Activity) context,13):" + dp2px(context,30));

        Log.e("info", "init getHeight():" + getHeight());
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(mTextSize);
    }

    public double getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();//http://mikewang.blog.51cto.com/3826268/871765/
        return Math.ceil(fm.descent - fm.ascent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("info", "onSizeChanged w:" + w + "  h:" + h);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("info", "onMeasure ");
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {// match_parent , accurate
            Log.e("xxx", "width EXACTLY");
            mWidth = specSize;
        } else {

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mWidth = (int) textPaint.measureText(text);
                Log.e("xxx", "width AT_MOST");
            }

        }
        /***
         * 设置高度
         */
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY){// match_parent , accurate
            mHeight = specSize;
        }else{

            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = (int) getTxtHeight(textPaint);
                Log.e("info","height wrap_content");
            }

        }
        setMeasuredDimension(mWidth, mHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("info", "onLayout ");
        marginTop = top;
        Log.e("info","marginTop" + marginTop);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.i("info", "draw ");
        canvas.drawColor(Color.YELLOW);//用于绘制背景色
        Log.e("info", "draw getWidth()" + getWidth());
        Log.e("info", "draw getHeight()" + getHeight());
        Log.e("info", "draw getTop()" + getMeasuredHeight());
        canvas.drawText(text, 0,mHeight - 1, textPaint);
//        canvas.drawText(text, getWidth() / 2 - textPaint.measureText(text) / 2,mHeight, textPaint);
    }


    //DP转PX
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //PX转DP
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
