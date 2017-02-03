package com.david.cavasdemo.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.david.cavasdemo.R;
import com.david.cavasdemo.utils.PixelUtil;

/**
 * Created by Administrator on 2016/9/30.
 */
public class CanvasBitmapView extends View {
    private Bitmap bitmap;
    private Context context;

    public CanvasBitmapView(Context context) {
        super(context);
        this.context = context;
    }

    public CanvasBitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_app);
        bitmap = BitmapFactory.decodeFile("/system/media/Pre-loaded/Pictures/Picture_09_Stone.jpg");

    }

    public CanvasBitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // 将画布坐标系移动到画布中央
//        canvas.translate(getWidth() / 2,getHeight() / 2);
//        canvas.drawBitmap(bitmap,new Matrix(),new Paint());
//        canvas.drawBitmap(bitmap,200,500,new Paint());

        // 指定图片绘制区域(左上角的四分之一)
    /*    Rect src = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(0, 0, 200, 400);
        //绘制图片
        canvas.drawBitmap(bitmap, src, dst, new Paint());*/
        canvas.drawBitmap(bitmap,0, 0, new Paint());

        bitmap = BitmapFactory.decodeFile("/system/media/Pre-loaded/Pictures/Picture_06_Space.jpg");


    }

}
