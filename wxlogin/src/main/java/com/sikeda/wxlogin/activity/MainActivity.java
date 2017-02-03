package com.sikeda.wxlogin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sikeda.wxlogin.AppConfig;
import com.sikeda.wxlogin.AppContext;
import com.sikeda.wxlogin.utils.DateUtils;
import com.sikeda.wxlogin.utils.LoadingDialogUtil;
import com.sikeda.wxlogin.utils.LogUti;
import com.sikeda.wxlogin.utils.MD5Utils;
import com.sikeda.wxlogin.R;
import com.tencent.mm.sdk.modelmsg.SendAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static Dialog dialog;
    private TextView tv_result;
    private String redirectUrl = "";
    private LinearLayout btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (LinearLayout) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoginByWechat();
            }
        });

        tv_result = (TextView) findViewById(R.id.tv_result);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUti.e("Thread", Thread.currentThread().getName());
                    redirectUrl = getRedirectUrl();
                    LogUti.e("redirectUrl", redirectUrl + "");
                    if (redirectUrl != null && !redirectUrl.equals("https://www.baidu.com/")) {//网络不可用
                        LogUti.e("info", "网络不可用，开启自动认证");
                        initPortal();
                    } else {
                        LogUti.e("info", "网络可用");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_result.setText("认证结果：您已通过all-WiFi-Free接入互联网");
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getRedirectUrl() throws MalformedURLException {
        String url = "http://www.baidu.com";
        System.out.println("访问地址:" + url);
        URL serverUrl = new URL(url);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            //必须设置 false，否则会自动 redirect 到 Location 的地址
            conn.setInstanceFollowRedirects(false);
            conn.addRequestProperty("Accept-Charset", "UTF-8;");
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");
            conn.addRequestProperty("Referer", "http://www.baidu.com");
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String location = conn.getHeaderField("Location");
        System.out.println("跳转地址:" + location);
        redirectUrl = location;
        return location;
    }


    /**
     * app wifi 自动认证
     */
    private void initPortal() {
        final Map<String, String> map = new HashMap<>();
        LogUti.e("info", "redirectUrl:" + redirectUrl);
        String params = redirectUrl.substring(redirectUrl.indexOf("?") + 1, redirectUrl.length());
        LogUti.e("info", "params:" + params);
        try {
            map.put("nasparm", URLEncoder.encode(params, "UTF-8"));
            map.put("func", "app");
            map.put("appname", "allwifi");
            map.put("useraccount", DateUtils.getCurrentTime() + RandomString(19));//stringRequestPostForPortal
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

//        final String url = "http://121.42.59.159:8090/aaa/appUser/doAutoLogin";
        final String url = "http://112.5.16.67:8080/aaa/appUser/doAutoLogin";

        StringRequest gotoPortal;
        gotoPortal = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                try {
                    LogUti.e("info", "s:");
                    JSONObject jsonObject = new JSONObject(s);
                    String result = jsonObject.getString("result");
                    if (result.equals("00")) {
                        Toast.makeText(MainActivity.this, "认证成功", Toast.LENGTH_LONG).show();
                        tv_result.setText("认证结果： 认证成功，您已通过all-WiFi-Free接入互联网");
                        btn_login.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "认证失败" + volleyError.toString(), Toast.LENGTH_LONG).show();
                tv_result.setText("认证结果： 认证失败：" + volleyError.toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                if (map != null) {
                    map.put("accept", "application/json");
                    map.put("content-type", "application/json;charset=utf-8");
                    return map;
                } else
                    return null;
            }
        };

        requestQueue.add(gotoPortal);

    }


    /**
     * 产生一个随机的字符串
     */
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }


    /**
     * 微信登录相关
     *
     * @param
     */
    private void initLoginByWechat() {

        // get token
        if (!AppContext.getInstance().api.isWXAppInstalled()) {
            Toast.makeText(MainActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog = LoadingDialogUtil.createLoadingDialog(MainActivity.this, "微信登录授权中...");
        dialog.show();
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//获取用户个人信息
        AppConfig.WX_STATE_RANDOM = MD5Utils.ecode(Math.random() * 1000000 + "");
        req.state = AppConfig.WX_STATE_RANDOM;//用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        AppContext.getInstance().api.sendReq(req);
        Log.e("info", "发起微信登录请求");


    }

    private StringRequest wxOpidGet;

    private void getOpenId(String code) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        /**
         * 从微信服务器获取openId ,AccessToken
         * http请求方式: GET
         * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
         */
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppContext.APP_ID + "&secret=" + AppContext.SECRET + "&code=" + code + "&grant_type=authorization_code";
        wxOpidGet = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Log.e("info", "wxOpidGet onResponse： " + s.toString());
                /**
                 * {
                 "access_token":"ACCESS_TOKEN",
                 "expires_in":7200,
                 "refresh_token":"REFRESH_TOKEN",
                 "openid":"OPENID",
                 "scope":"SCOPE",
                 "unionid":"o6_bmasdasdsad6_2sgVt7hMZOPfL"
                 }
                 */
                try {
                    String unionid = new JSONObject(s).getString("unionid");
                    if (!unionid.equals("")) {
                        startActivity(new Intent(MainActivity.this, PayActivity.class).putExtra("unionid", unionid));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        requestQueue.add(wxOpidGet);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadingDialogUtil.dissMissDialog(dialog);
        if (!AppConfig.WX_CODE.equals("")) {
            if (AppConfig.WX_CODE.equals("error")) {
                AppConfig.WX_CODE = "";
            } else {
                getOpenId(AppConfig.WX_CODE);
                AppConfig.WX_CODE = "";
            }

        }
    }
}
