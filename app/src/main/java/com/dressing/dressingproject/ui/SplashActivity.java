package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.dressing.dressingproject.R;

public class SplashActivity extends AppCompatActivity
{

    private final int SPLASH_DISPLAY_LENGHT = 1000; //Splash 시간

    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goLogin();
            }
        }, SPLASH_DISPLAY_LENGHT);


//        findViewById(R.id.splash).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
//            }
//        }, 1000);
    }

    private void goMain() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    private void goLogin() {

        startActivity(new Intent(this, LoginActivity.class));
        SplashActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}
