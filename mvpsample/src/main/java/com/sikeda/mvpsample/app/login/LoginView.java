package com.sikeda.mvpsample.app.login;

/**
 * Created by Administrator on 2016/11/14.
 */

public interface LoginView {
    void showProgress();
    void hideProgress();

    void setUserNameError();
    void setPasswordError();
    void navigateToHome();
}
