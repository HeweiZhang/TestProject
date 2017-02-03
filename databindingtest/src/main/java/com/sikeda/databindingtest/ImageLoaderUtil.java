package com.sikeda.databindingtest;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2016/8/9.
 */
public class ImageLoaderUtil {
    public static void setImageWithCache(String imgUrl, ImageView imageView) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(null)//加载中显示的图片
                .showImageOnFail(null)//加载失败时显示的图片
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
    }

    public static void setImageNotCache(String imgUrl, ImageView imageView) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(null)//加载中显示的图片
                .showImageOnFail(null)//加载失败时显示的图片
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imgUrl, imageView, options);

    }
    public static void setImageFromPath(String path, ImageView imageView){
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(null)//加载中显示的图片
                .showImageOnFail(null)//加载失败时显示的图片
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        String imageUrl = ImageDownloader.Scheme.FILE.wrap(path);//转换url
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    public static Bitmap loadImageFromUriASync(String url){
//        ImageLoader.getInstance().loadImageSync(url);
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        return null;
    }
    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId
     * @param imageView
     */
    public static void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView);
    }

}
