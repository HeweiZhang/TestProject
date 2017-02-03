package com.sikeda.mvpsample.app.main;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class FindItemsInteractorImpl implements FindItemsInteractor {
    @Override
    public void findItems(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createData());
            }

        }, 2000);
    }

    private List<String> createData() {
        List<String> l = new ArrayList();
        for (int i = 0; i < 10; i++) {
            l.add("Item" + i);
        }
        return l;
    }
}
