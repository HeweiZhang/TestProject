package com.david.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyService.MyBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("MainActivity", "onServiceConnected()");
            myBinder = (MyService.MyBinder) iBinder;
            myBinder.startDownLoad();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("MainActivity", "onServiceDisconnected()");

        }
    };
    private boolean isBinded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity", "onCreate()");
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(MainActivity.this, MyService.class);
                startService(startIntent);
            }
        });

        findViewById(R.id.stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                stopService(stopIntent);
            }
        });

        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * 第三个参数是一个标志位，这里传入BIND_AUTO_CREATE表示在Activity和
                 * Service建立关联后自动创建Service，这会使得MyService中的onCreate()
                 * 方法得到执行，但onStartCommand()方法不会执行。
                 */
                Intent bindIntent = new Intent(MainActivity.this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                isBinded = true;
            }
        });

        findViewById(R.id.unbind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isBinded) {
                    unbindService(connection);
                    isBinded = false;
                }

            }
        });
    }
}
