package com.david.okgodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.david.okgodemo.callback.JsonDialogCallback;
import com.david.okgodemo.callback.StringDialogCallback;
import com.david.okgodemo.custom.NumberProgressBar;
import com.david.okgodemo.domain.HttpResult;
import com.david.okgodemo.domain.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.david.okgodemo.R.id.tv_get;

public class MainActivity extends AppCompatActivity {
    TextView tv_get;
    private String TAG;
    private NumberProgressBar pbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_get = (TextView) findViewById(R.id.tv_get);
        pbProgress = (NumberProgressBar)findViewById(R.id.pbProgress) ;
        TAG = this.getClass().getName();

        //Get请求
        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get请求
                String url = "http://15h0l63133.iask.in/newthink/public/index.php/myservice/index/getRequest";
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", "david");
                map.put("gender", "1");
                OkGo.get(url)
                        .tag(TAG)
                        .params(map)
                        .cacheKey(MainActivity.this.getClass().getName())
                        .cacheMode(CacheMode.DEFAULT)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                // s 即为所需要的结果
                                Log.i("info", "get请求成功：" + s);
                                tv_get.setText("get请求成功：" + s);
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.i("info", "e:" + e);
                                tv_get.setText(e.toString());
                            }
                        });

            }
        });

        //Post请求
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get请求
                String url = "http://app.all-wifi.cn/Service.php?a=test";
//                String url = "http://15h0l63133.iask.in/newthink/public/index.php/myservice/index/postRequest";
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", "david");
                map.put("gender", "1");
                OkGo.post(url)
                        .tag(TAG)
                        .params(map)
                        .cacheKey(MainActivity.this.getClass().getName())
                        .cacheMode(CacheMode.DEFAULT)
                        .execute(new JsonDialogCallback<List<Test>>(MainActivity.this) {
                            @Override
                            public void onSuccess(List<Test> test, Call call, Response response) {
                                tv_get.setText("post请求成功：" + test.get(0).getMessage() + "  ,  "+ test.get(1).getMessage());
                            }
                        });

            }
        });

        //图片上传
        findViewById(R.id.btn_image_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get请求
                String url = "http://15h0l63133.iask.in/newthink/public/index.php/myservice/index/imageUpLoad";
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", "david");
                map.put("gender", "1");
                OkGo.post(url)
                        .tag(TAG)
                        .params(map)
//                        .addFileParams("photo",list)//多个文件上传
                        .params("photo",new File("/storage/emulated/0/all_wifi/head/head.jpg"))//单个单个添加
                        .params("photo",new File("/storage/emulated/0/all_wifi/head/head.jpg"))
                        .params("photo",new File("/storage/emulated/0/all_wifi/head/head.jpg"))
                        .cacheKey(MainActivity.this.getClass().getName())
                        .cacheMode(CacheMode.DEFAULT)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                // s 即为所需要的结果
                                tv_get.setText("图片上传成功：" + s);
                            }

                            @Override
                            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                                Log.i("info", "progress：" + progress +"  currentSize:" + currentSize + "   totalSize：" + totalSize);
                                pbProgress.setMax(100);
                                pbProgress.setProgress((int) (progress * 100));
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Log.i("info", "e:" + e);
                                tv_get.setText(e.toString());
                            }
                        });
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(TAG);
    }
}
