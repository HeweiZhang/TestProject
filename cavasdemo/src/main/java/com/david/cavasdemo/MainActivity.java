package com.david.cavasdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.david.cavasdemo.custom.PieView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PieView pieView;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieView = (PieView)findViewById(R.id.pieView);
        ArrayList<PieData> datas = new ArrayList<>();
        datas.add(new PieData(mColors[0],20,100,20));
        datas.add(new PieData(mColors[1],20,100,20));
        datas.add(new PieData(mColors[2],20,100,20));
        datas.add(new PieData(mColors[3],20,90,20));
        pieView.setData(datas);

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CavasTranslate.class));
            }
        });
    }
}
