package com.sikeda.mvpsample.app.image;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sikeda.mvpsample.R;

/**
 * Created by Administrator on 2016/11/15.
 */

public class ImageActivity extends Activity implements ImagesView  {
    private ImageView image;
    private Button btn_invisiable;
    private Button btn_gone;
    private Button btn_visiable;
    private ImagePresenterImpl imagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        image = (ImageView) findViewById(R.id.image);
        btn_invisiable = (Button) findViewById(R.id.btn_invisiable);
        btn_gone = (Button) findViewById(R.id.btn_gone);
        btn_visiable = (Button) findViewById(R.id.btn_visiable);

        imagePresenter = new ImagePresenterImpl(this);
        setOnclick();
    }

    private void setOnclick() {

        btn_invisiable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePresenter.setInVisiable();
            }
        });

        btn_gone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePresenter.setGone();
            }
        });

        btn_visiable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePresenter.setVisiable();
            }
        });
    }


    @Override
    public void setVisiable() {
        image.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGone() {
        image.setVisibility(View.GONE);
    }

    @Override
    public void setInVisiable() {
        image.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imagePresenter.onDestroy();
    }


}
