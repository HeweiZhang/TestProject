package com.sikeda.mvpsample.app.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sikeda.mvpsample.app.main.MainActivity;
import com.sikeda.mvpsample.R;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginActivity extends Activity implements LoginView ,View.OnClickListener{

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_pasw);
        findViewById(R.id.btn_go).setOnClickListener(this);
        presenter = new LoginPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUserNameError() {
        username.setHint("用户名不能为空");
    }

    @Override
    public void setPasswordError() {
        password.setHint("密码错误！");
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        presenter.goLogin(username.getText().toString(),password.getText().toString());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
