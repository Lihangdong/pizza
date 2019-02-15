package com.huashe.pizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.huashe.pizz.VMBanner.Banner;
import java.util.Arrays;
import java.util.List;


public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        final Banner banner = findViewById(R.id.banner);
        Intent intent = getIntent();
        List<String> path = Arrays.asList(intent.getExtras().get("path").toString().split(","));
        System.out.println("path   "+path);
        banner.setDataList(path);
        int pos = Integer.parseInt(intent.getExtras().get("pos").toString())+1;
        System.out.println("pos  "+pos);
        banner.setAutoCurrIndex(pos);
//        banner.setImgDelyed(6000);
        banner.startBanner();
        ImageButton ibBack=findViewById(R.id.ib_play_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayActivity.this.finish();
            }
        });

    }


}
