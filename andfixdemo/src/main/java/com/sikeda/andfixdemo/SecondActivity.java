package com.sikeda.andfixdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/20.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg.setText("New version");
    }
}
