package com.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_click)
    void onClick(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://retrofit.devwiki.net/")
                .addConverterFactory(GsonConverterFactory.create())//Must be done
                .build();
        PhoneService service = retrofit.create(PhoneService.class);
        Call<String> repos = service.getResult();
 /*       repos.enqueue(new Callback<PhoneResult>() {
            @Override
            public void onResponse(Call<PhoneResult> call, Response<PhoneResult> response) {
                Log.i("info", "onResponse  " + response.body().getDesc().toString());
//                Log.i("info", "onResponse  " + response.toString());
            }

            @Override
            public void onFailure(Call<PhoneResult> call, Throwable t) {
                Log.i("info", "onFailure  " + t);
            }
        });*/
        repos.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("info", "onResponse  " + response.body().toString());

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("info", "onFailure  " + t);

            }
        });
    }
    public interface PhoneService {
        @GET("simple")
        Call<String> getResult();
//        Call<List<PhoneResult>> getResult(@Header("key") String apiKey, @Query("phone") String phone);

    }

}
