package com.david.permissioncheck;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callPhone();
    }

    public void callPhone() {
        if (hasPermission(Manifest.permission.CALL_PHONE)) {
            doCallPhone();
        } else {
            requestPermission(CALL_PHONE_CODE, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void doCallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10010");
        intent.setData(data);
        this.startActivity(intent);

    }
}
