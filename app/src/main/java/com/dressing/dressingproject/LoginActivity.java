package com.dressing.dressingproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 로그인 페이지
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //LoginMainFragment 바인딩
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginMainFragment()).commit();
        }
    }
}
