package com.david.cavasdemo.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/9/28.
 * <p/>
 * 相关方法	简介
 * public int getWidth ()	获取宽度
 * public int getHeight ()	获取高度
 * public Canvas beginRecording (int width, int height)	开始录制 (返回一个Canvas，在Canvas中所有的绘制都会存储在Picture中)
 * public void endRecording ()	结束录制
 * public void draw (Canvas canvas)	将Picture中内容绘制到Canvas中
 * public static Picture createFromStream (InputStream stream)	(已废弃)通过输入流创建一个Picture
 * public void writeToStream (OutputStream stream)	(已废弃)将Picture中内容写出到输出流中
 */
public class CanvasPictureBitmap extends View {

    private Paint mPaint;
    // 1.创建Picture
    private Picture mPicture ;


    public CanvasPictureBitmap(Context context) {
        super(context);
    }

    public CanvasPictureBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("info","init");
        recording();  // 调用录制

    }

    public CanvasPictureBitmap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 2.录制内容方法
    private void recording() {
        if (isInEditMode()){ // 预览模式直接返回
            return;
        }

        mPicture = new Picture();
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(500, 500);
        // 创建一个画笔
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

        // 在Canvas中具体操作
        // 位移
        canvas.translate(250,250);
        // 绘制一个圆
        canvas.drawCircle(0,0,100,mPaint);

        mPicture.endRecording();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.e("info","draw");
        if (isInEditMode()){ // 预览模式,提供静态显示
            canvas.translate(250, 250);
            canvas.drawCircle(0, 0, 250, mPaint);
            return;
        }
//        mPicture.draw(canvas);
        canvas.drawPicture(mPicture,new RectF(0,0,mPicture.getWidth(),200));
    }
}
