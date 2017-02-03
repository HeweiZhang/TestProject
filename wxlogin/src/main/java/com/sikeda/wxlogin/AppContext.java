package com.sikeda.wxlogin;

import android.app.Application;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/10/13.
 */

public class AppContext extends Application {

    public IWXAPI api;

    public static String APP_ID = "wx9aedeb5dcdc19f2d";
    public static String SECRET = "69b3e604440ae98c1822e268ce323506";

    private static AppContext instance;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initWechatLogin();
    }



    private void initWechatLogin() {
        //注册到微信

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        //将应用的appId注册到微信
        api.registerApp(APP_ID);
    }
}
