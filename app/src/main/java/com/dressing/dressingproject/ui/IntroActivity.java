package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dressing.dressingproject.R;
import com.github.paolorotolo.appintro.AppIntro;


public class IntroActivity extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(IntroSlide.newInstance(R.drawable.intro_1));
        addSlide(IntroSlide.newInstance(R.drawable.intro_2));
        addSlide(IntroSlide.newInstance(R.drawable.intro_3));
        addSlide(IntroSlide.newInstance(R.drawable.intro_4));
        addSlide(IntroSlide.newInstance(R.drawable.intro_5));
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("fromIntro", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        //Toast.makeText(getApplicationContext(),getString(R.string.skip),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
