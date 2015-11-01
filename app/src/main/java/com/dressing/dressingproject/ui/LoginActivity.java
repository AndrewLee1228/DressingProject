package com.dressing.dressingproject.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dressing.dressingproject.R;

/**
 * 로그인과 회원가입을 담당한다.
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

    public void pushSingInFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new SignInFragment()).addToBackStack(null).commit();
    }

    public void pushSingUpFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new SignUpFragment()).addToBackStack(null).commit();
    }
}
