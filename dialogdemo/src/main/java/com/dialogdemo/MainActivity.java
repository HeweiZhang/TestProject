package com.dialogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_show2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialogUtil.createLoadingDialog(MainActivity.this,"加载中...").show();
            }
        });
    }

}
