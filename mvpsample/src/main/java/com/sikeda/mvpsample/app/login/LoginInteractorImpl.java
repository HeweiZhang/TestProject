package com.sikeda.mvpsample.app.login;

import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void gologin(final String userName, final String psw, final OnLoginFinishInteractor listener) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(userName)) {
                    listener.onUserNameError();
                    return;
                }
                if (TextUtils.isEmpty(psw)) {
                    listener.onPasswordError();
                    return;
                }
                if (userName.equals("123") && psw.equals("123")) {
                    listener.success();
                } else
                    listener.onPasswordError();
            }
        }, 2000);
    }
}
