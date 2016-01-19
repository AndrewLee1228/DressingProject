package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.LoginViewPagerAdapter;
import com.dressing.dressingproject.ui.widget.ViewPagerCustomDuration;

import java.util.List;

/**
 * 로그인과 회원가입을 담당한다.
 */
public class LoginActivity extends AppCompatActivity
{

    private ViewPagerCustomDuration mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        //LoginMainFragment 바인딩
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginMainFragment()).commit();
        }


        //배경 뷰페이저
        mPager = (ViewPagerCustomDuration) findViewById(R.id.activity_login_viewpager);
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(new LoginViewPagerAdapter(this));
        mPager.setPageMargin(0);
        mPager.setPageMarginDrawable(null);
        mPager.setCurrentItem(0);


        //뷰페이저의 스크롤을 막는다.
        final GestureDetector gd = new GestureDetector(new GestureDetector.SimpleOnGestureListener());
        mPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        gd.onTouchEvent(event);

                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //현재 프래그먼트에 data 전달
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        Fragment lastFragment =  fragments.get(0);
        lastFragment.onActivityResult(requestCode, resultCode, data);

    }

    //로그인
    public void pushSingInFragment() {
        setCurrentPage(1);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.container, new SignInFragment())
                .addToBackStack(null)
                .commit();
    }

    //회원가입
    public void pushSingUpFragment() {
        setCurrentPage(1);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                .replace(R.id.container, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    //뷰페이저의 페이지를 변경한다.
    public void setCurrentPage(int position) {
        mPager.setCurrentItem(position);
    }


}
