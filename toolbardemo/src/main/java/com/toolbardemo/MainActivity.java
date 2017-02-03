package com.toolbardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        toolbar.setSubtitle("副标题");
        //设置导航图标要在setSupportActionBar方法之后
        toolbar.setLogo(R.mipmap.lay_icn_alb);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.lay_icn_alias);
//        toolbar.setTitleTextColor();//设置标题字体颜色
//        toolbar.setTitleTextAppearance();//设置标题字体大小
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_notifications){
                    Toast.makeText(MainActivity.this,"你点击了我",Toast.LENGTH_SHORT).show();
                }
                return true;//改为true
            }
        });
    }
    public void setTitle(String title){
        toolbar.setTitle(title);
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }*/
}
