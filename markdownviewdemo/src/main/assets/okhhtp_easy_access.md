###### 最近身边越来越多的小伙伴提到okHttp，于是我也打算趁着新项目开始前着手学习一下okhttp，并且希望能在新项目中用起来。Google了一些材料，照着文档写了简单的demo，并且做了最简单的封装。每个项目中的网络请求接口一般都不会太少，所以不封装是不靠谱的，然而okHttp确实封装起来比较麻烦一些。
  OkHttp官网地址： [http://square.github.io/okhttp/](http://square.github.io/okhttp/)

  OkHttp GitHub地址：[https://github.com/square/okhttp](https://github.com/square/okhttp)

* 配置方法 导入jar包
```
  compile 'com.squareup.okhttp3:okhttp:3.3.1'
```
* 开启网络权限不解释。
----
*  ## 基础用法介绍，GET，POST请求，同步，异步
#### 同步Http GET请求
```java
OkHttpClient client = new OkHttpClient();
private void httpGet(String url) throws IOException {
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();//Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应
    final Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
        return response.body().string();
    } else {
        throw new IOException("Unexpected code " + response);
    }
}
    /**
      * onResponse回调的参数是response，一般情况下，比如
      * 我们希望获得返回的字符串，可以通过response.body().string()获取；
      * 如果希望获得返回的二进制字节数组，则调用response.body().bytes()；
      * 如果你想拿到返回的inputStream，则调用response.body().byteStream()
      */
```
---
#### 同步Http POST请求
```java
//POST提交键值对
OkHttpClient client = new OkHttpClient();
private void httpPost(String url) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("u_Phone", "10086")//传入参数
                .add("code", "111111")//传入参数
                .add("encodestr", MD5Utils.ecodeTwice("10086" + "111111" +
                      "action_login"))//传入参数
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
       final Response response = client.newCall(request).execute();
         if (response.isSuccessful()) {
        return response.body().string();
    } else {
        throw new IOException("Unexpected code " + response);
    }
}

//POST提交Json数据
public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
OkHttpClient client = new OkHttpClient();
String post(String url, String json) throws IOException {
    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
    Response response = client.newCall(request).execute();
    f (response.isSuccessful()) {
        return response.body().string();
    } else {
        throw new IOException("Unexpected code " + response);
    }
}
```
---
#### 异步 Http Get
```java
private void asyGet(String url) throws IOException {
       final Request request = new Request.Builder()
               .get()
               .tag(this)
               .url(url)
               .build();//Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应
       final Response response = client.newCall(request).execute();
       Call call = client.newCall(request);
       /**
        * 我们希望以异步的方式去执行请求，所以我们调用的是call.enqueue，将call加入调度队列，然后等待任务执行完成，我们在Callback中即可得到结果。
        */
       call.enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               try {
                   throw new IOException("UnException code" + response);
               } catch (IOException e1) {
                   e1.printStackTrace();
               }
           }
           @Override
           public void onResponse(Call call, Response response) throws IOException {
               Log.i("info", "打印GET响应的数据：:" + response.body().string());
           }
       });
   }
```
---
#### 异步HTTP Post
```java
private void asyPost(String url) throws IOException {
    RequestBody formBody = new FormBody.Builder()
            .add("u_Phone", "10086")
            .add("code", "111111")
            .add("encodestr", MD5Utils.ecodeTwice("10086" + "111111" + "action_login"))
            .build();
    final Request request = new Request.Builder()
            .url(url)
            .post(formBody)
            .build();
    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("info", String.valueOf(e));
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i("info", "打印POST响应的数据：" + response.body().string());
        }
    });
}
```
---
看了基本用法后是不是感觉入门也是十分简单，跟volley十分相似，但是有一点却是十分懵逼的- -。因为volley的每一个请求都需要加入到请求队列中，并且可以使用tag来取消当前网络请求,如下：
```java
requestQueue.add(req);
```
于是用惯了volley的我就不能忍了，于是乎查资料，发现在okhttp的用法跟volley不一样，所以这个一时我就找不到这个tag的作用，一脸懵逼状- -。只允许设置tag，却没法用tag给取消这个请求，kiding me？ 想想这么流行的框架不可能会留下这种低级错误啊，于是乎查看源码（似懂非懂–），Google，终于找到对应的解决方式：
```java
public void cancelCallsWithTag(Object tag){

    if (tag == null || mOkHttpClient == null) {
        return;
    }

    synchronized (mOkHttpClient.dispatcher().getClass()) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) call.cancel();
        }
    }
}
```
最基本的用法了解了，是时候该着手进行简单的封装
这里主要用到 OkHttpClientManager类，与回调接收接口ResultCallback
偷懒一下 ，直接贴源码了，相应解释直接在代码中注释：
```java
package com.okhttpstudy.okhttp;

/**
 * Created by hwei on 2016/7/8.
 */
public interface ResultCallback {

    public abstract void onError(String msg);

    public abstract void onResponse(String result);
}
```
```java
package com.okhttpstudy.okhttp;

import android.app.Activity;

import com.okhttpstudy.utils.LogUti;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hwei on 2016/7/8.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager instance;
    private OkHttpClient mOkHttpClient;
    private static final String TAG = "OkHttpClientManager";

    public OkHttpClientManager() {
        //mOkHttpClient = new OkHttpClient();
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000l, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //.cache(cache)
                .build();

        //cookie enabled
        //cookie相关的东西还有很多需要研究，以后慢慢补充
//        mOkHttpClient.cookieJar(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
    }

    public static OkHttpClientManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }


    public void cancelCallsWithTag(Object tag) {
        if (tag == null || mOkHttpClient == null) {
            return;
        }
        synchronized (mOkHttpClient.dispatcher().getClass()) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }
        }
    }
    public void getAsyn(final Activity activity, String url, Map<String, String> requestmap, final                  ResultCallback callback) {

        final Request request = new Request.Builder()
                .get()
                .tag(activity.getClass().getSimpleName())
                .url(onBuildGetParams(url, requestmap))//get请求，参数拼接
                .build();//Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(String.valueOf(e));
            }

            /**
             *
             * @param call
             * @param response
             * @throws IOException
             * onResponse回调的参数是response，一般情况下，比如
             * 我们希望获得返回的字符串，可以通过response.body().string()获取；
             * 如果希望获得返回的二进制字节数组，则调用response.body().bytes()；
             * 如果你想拿到返回的inputStream，则调用response.body().byteStream()
             */
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    activity.runOnUiThread(new Runnable() {//用惯了volley的人初次搞这个可能都会习惯直接调用ui线程去更新ui，然后okhttp在这一点上一点却让人“不太省心”，我们必须自己进行线程切换
                        @Override
                        public void run() {
                            try {
                                callback.onResponse(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });
    }

    public void postAsyn(final Activity activity, String url, Map<String, String> requestmap, final         ResultCallback callback) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject(requestmap);
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        LogUti.i("info", "requestmapJson" + String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(url)
                .tag(activity.getClass().getSimpleName())
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(String.valueOf(e));
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callback.onResponse(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

/**
* get请求参数的封装，构造成与POST同样的调用方式
*/
    private String onBuildGetParams(String url, Map<String, String> map) {
        StringBuilder sb = new StringBuilder(url);
        boolean isFirst = true;
        if (map == null) {//没有参数时 直接返回url
            return sb.toString();
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isFirst) {
                isFirst = false;
                sb.append("?" + entry.getKey() + "=" + entry.getValue());
            } else {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            }
        }
        LogUti.i("info", "url" + sb.toString());
        return sb.toString();
    }
}

```
#####  如何使用：

```java
//调用post请求
Map<String,String> map = new HashMap<>();
map.put("u_Phone","10086");
map.put("url","http://simple.cn/Service.php?a=checkMobile");
map.put("code","111111");
map.put("encodestr",MD5Utils.ecodeTwice("10086" + "111111" + "action_login"));
OkHttpClientManager.getInstance().postAsyn(this,"http://simple.cn/Service.php?a=checkMobile", map, new ResultCallback() {
    @Override
    public void onError(String msg) {
        LogUti.i("info","post onError" + msg);
    }

    @Override
    public void onResponse(final String result) {
        Toast.makeText(MainActivity.this,"result"+result ,Toast.LENGTH_LONG).show();
        LogUti.i("info","post onResponse result:" + result.toString());
    }
});


//调用get请求
Map<String,String> map = new HashMap<>();
       map.put("u_Phone","10086");
       map.put("url","http://simple.cn/Service.php?a=checkMobile");
       map.put("code","111111");
       map.put("encodestr",MD5Utils.ecodeTwice("simple" + "111111" + "action_login"));
       OkHttpClientManager.getInstance().postAsyn(this,"http://simple.cn/Service.php?a=checkMobile", map, new ResultCallback() {
           @Override
           public void onError(String msg) {
               LogUti.i("info","post onError" + msg);

           }

           @Override
           public void onResponse(final String result) {
               Toast.makeText(MainActivity.this,"result"+result ,Toast.LENGTH_LONG).show();
               LogUti.i("info","post onResponse result:" + result.toString());
           }
       });


//我们可以在每个页面销毁前取消对应tag的网络请求
@Override
  protected void onDestroy() {
      super.onDestroy();
      OkHttpClientManager.getInstance().cancelCallsWithTag(tag);
  }
```
只是简单的进行了封装，直接将网络响应结果回调给每个Activity，而没有进行统一的解析，这里有待改进。。。
