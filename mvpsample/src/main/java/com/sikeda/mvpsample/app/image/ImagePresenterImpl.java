package com.sikeda.mvpsample.app.image;

import android.os.Handler;

/**
 * Created by Administrator on 2016/11/15.
 */

public class ImagePresenterImpl implements ImagePresenter  {

    private ImagesView imageView;

    public ImagePresenterImpl(ImagesView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void setVisiable() {
        if (imageView != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    imageView.setVisiable();
                }
            }, 1000);
        }
    }

    @Override
    public void setInVisiable() {
        if (imageView != null) {
            imageView.setInVisiable();
        }
    }

    @Override
    public void setGone() {
        if (imageView != null) {
            imageView.setGone();
        }
    }

    @Override
    public void onDestroy() {
        if (imageView != null) {
            imageView = null;
        }
    }

}
