package com.sikeda.mvpsample.app.login;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginPresenterImpl  implements LoginPresenter,LoginInteractor.OnLoginFinishInteractor{
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void goLogin(String userName, String psw) {
        if(loginView != null){
            loginView.showProgress();
            loginInteractor.gologin(userName,psw,this);
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onPasswordError() {
        if(loginView != null){
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onUserNameError() {
        if(loginView != null){
            loginView.setUserNameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void success() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
