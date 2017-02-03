package com.sikeda.wxlogin.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sikeda.wxlogin.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/13.
 */

public class PayActivity extends AppCompatActivity {
    private String unionid = "";
    private TextView tv_show_result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        unionid = getIntent().getStringExtra("unionid");
        findViewById(R.id.ll_goPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        tv_show_result = (TextView) findViewById(R.id.tv_show_result);
    }

    private StringRequest wxOpidGet;

    private void upload() {
        Log.e("info", "openID:" + unionid);
        String url = "http://app.all-wifi.cn/Service.php?a=test_wx_send&unionid=" + unionid;
        Log.e("info", "url:" + url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        /**
         * 从微信服务器获取openId ,AccessToken
         * http请求方式: GET
         * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
         */
        wxOpidGet = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Log.e("info", "wxOpidGet onResponse： " + s.toString());
                try {
                    JSONObject jsonObject = new JSONObject(s.toString());
                    String msg = jsonObject.getString("data");
                    tv_show_result.setText(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv_show_result.setText("抱歉，与服务器断开连接");
            }
        }
        );
        requestQueue.add(wxOpidGet);
    }
}
