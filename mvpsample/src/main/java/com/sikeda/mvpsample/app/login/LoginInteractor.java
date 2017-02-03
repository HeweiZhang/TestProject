package com.sikeda.mvpsample.app.login;

import android.content.DialogInterface;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface LoginInteractor {
    interface OnLoginFinishInteractor{
        void onPasswordError();
        void onUserNameError();
        void success();

    }
    void gologin(String userName, String psw, OnLoginFinishInteractor listener);
}
