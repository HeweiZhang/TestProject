package com.toolbardemo;

import android.os.Bundle;
import android.view.Menu;

/**
 * Created by Administrator on 2016/7/19.
 */
public class AActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_a);
        super.onCreate(savedInstanceState);
        super.setTitle("AActivity");
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}
