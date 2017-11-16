package com.example.wonsi.petdictionary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wonsi.petdictionary.R;


import java.util.logging.LogRecord;

/**
 * Created by wonsi on 2017-11-15.
 */

public class Intro extends AppCompatActivity {
    private Handler handler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(Intro.this, Login.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        handler = new Handler();
        handler.postDelayed(runnable,1000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
