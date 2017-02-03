package com.david.permissioncheck;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/9/30.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 为子类提供权限检查方法
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * 为子类提供一个权限申请方法
     * @param code
     * @param permissions
     */
    public void requestPermission(int code,String... permissions){
        ActivityCompat.requestPermissions(this,permissions,code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_CODE:
                doSDCardPermission();
                break;
            case CALL_PHONE_CODE:
                doCallPhone();
                break;

        }
    }

    /**
     * 默认的写sdCard权限处理
     */
    public void doSDCardPermission(){}


    /**
     * 默认的打电话权限处理
     */
    public void doCallPhone(){}

    public static final int WRITE_EXTERNAL_CODE = 0x01;
    public static final int CALL_PHONE_CODE = 0x02;
}
