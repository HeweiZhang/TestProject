package com.sikeda.databindingtest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sikeda.databindingtest.databinding.ActivityMainBinding;
import com.sikeda.databindingtest.entity.User;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        /**
         * ImageLoader 初始化操作
         */
    }
    private void initImageLoader() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config.build());
    }

    void onClick(View view) {
        activityMainBinding.setUser(new User("ZhangHewei","David",24,"http://img2.cache.netease.com/auto/2016/7/28/201607282215432cd8a.jpg"));
    }
}
