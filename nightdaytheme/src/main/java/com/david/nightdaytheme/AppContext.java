package com.david.nightdaytheme;

import android.app.Application;
import android.content.res.Resources;
import android.support.v7.app.AppCompatDelegate;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * Created by Administrator on 2016/11/16.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
    }
}
