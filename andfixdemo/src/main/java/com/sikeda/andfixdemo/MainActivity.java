package com.sikeda.andfixdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private PatchManager patchManager;
    private Button btn_hot_fix, btn_goto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_hot_fix = (Button) findViewById(R.id.btn_hot_fix);
        btn_goto = (Button) findViewById(R.id.btn_goto);
        patchManager = new PatchManager(this);
        patchManager.init(getVersionName(this));//current version  注意每次appversion变更都会导致所有补丁被删除,如果appversion没有改变，则会加载已经保存的所有补丁。
        patchManager.loadPatch();
        btn_hot_fix.setText("修复之后版本");
        btn_hot_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String path = "/sdcard/fix.apatch";
                    patchManager.addPatch(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本名称
     */
    public String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            Log.e("info", "versionName" + versionName);
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
