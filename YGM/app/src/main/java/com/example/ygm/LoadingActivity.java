package com.example.ygm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

public class LoadingActivity extends AppCompatActivity {

    ImageView ivGIF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Activity activity = this;

        ivGIF = findViewById(R.id.imageView_gif);
        Glide.with(this).load(R.drawable.loading_infinity)
                .fitCenter()
                .override(Target.SIZE_ORIGINAL)
                .into(ivGIF);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(activity, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}