package com.david.cavasdemo.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/28.
 */
public class CanvasTranslateView extends View {
    private Paint mPaint = new Paint();
    // 宽高
    private int mWidth, mHeight;

    public CanvasTranslateView(Context context) {
        super(context);
        mPaint.setStrokeWidth(30);
    }

    public CanvasTranslateView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CanvasTranslateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 我们首先将坐标系移动一段距离绘制一个圆形，之后再移动一段距离绘制一个圆形，两次移动是可叠加的。
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

/*        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);//移动画布
        // 在坐标原点绘制一个蓝色圆形
        canvas.drawCircle(0,0,100,mPaint);*/


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);//设置画笔宽度一定要放在setStyle之后,否则不会生效

        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rect = new RectF(0,-400,400,0);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rect,mPaint);


//        canvas.scale(0.5f,0.5f);//画布缩放
//        canvas.scale(0.5f,0.5f,200,0);//画布缩放并且右移200
        canvas.scale(-0.5f,0.5f);//画布缩放  负数会翻转

        mPaint.setColor(Color.BLUE);//绘制蓝色矩形
        canvas.drawRect(rect,mPaint);

        //将坐标原点移动到蓝色矩形中心
        canvas.translate(0,0);
        canvas.translate(200, - 200 );//x轴左移为+，y轴上移为-
        RectF rect2 = new RectF(-200,-200,200,200);//以原点为中心，画个大框
        for(int i= 0;i< 20;i++){
            canvas.drawRect(rect2,mPaint);
            canvas.scale(0.9f,0.9f);
        }

        canvas.scale(10,10);//缩放为原来的10倍大小
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rect,mPaint);

        canvas.rotate(180);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect,mPaint);

        canvas.translate(200,-200);//将画布移动到屏幕中心点
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(0,0,400,mPaint);
        canvas.drawCircle(0,0,360,mPaint);
        for(int i = 0;i < 360; i += 10){
            canvas.drawLine(0,360,0,400,mPaint);
            canvas.rotate(10);
        }

    }
}
