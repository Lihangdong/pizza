package com.huashe.pizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ortiz.touchview.TouchImageView;

public class PlayPicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_pic);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        TouchImageView touchImageView=findViewById(R.id.touchimageview);
        Glide.with(this).load(path).into(touchImageView);
        touchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayPicActivity.this.finish();
            }
        });
    }
}
