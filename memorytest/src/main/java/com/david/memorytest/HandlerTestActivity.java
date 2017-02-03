package com.david.memorytest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/2/3.
 */

public class HandlerTestActivity extends Activity {

    /**
     * 如果一个内部类实例的生命周期比Activity更长，那么我们千万不要使用非静态的内部类。最好的做法是，使用静态内部类，然后在该类里使用弱引用来指向所在的Activity。
     */
    private static class MyHandler extends Handler {

        private final WeakReference<HandlerTestActivity> mActivity;

        private MyHandler(HandlerTestActivity activity) {
            mActivity = new WeakReference<HandlerTestActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }

    }

    private final MyHandler myHandler = new MyHandler(this);

    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() { /* ... */ }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myHandler.postDelayed(sRunnable,1000 * 60);
        finish();
    }
}
