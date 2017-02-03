package com.sikeda.mvpsample.app.login;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface LoginPresenter {
    void goLogin(String userName,String psw);
    void onDestroy();
}
