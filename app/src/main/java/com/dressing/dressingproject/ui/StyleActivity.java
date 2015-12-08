package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.ViewPagerAdapter;
import com.dressing.dressingproject.util.FontManager;

public class StyleActivity extends AppCompatActivity {

    private ViewPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public boolean mIsFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        Intent intent = getIntent();
        mIsFirst = intent.getBooleanExtra("isFirst",false);
        IntiLayout();
        if(mIsFirst)
        {
            //홈버튼을 제거한다.
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //탭레이아웃 숨기기
            mTabLayout.setVisibility(View.GONE);
            //프래그먼트 한개만 생성 flag

            //10개 이상 선택시 메인화면 이동버튼 나타나도록 한다.


        }


   }

    private void IntiLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_style_tabanim_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.main_blue));
        setSupportActionBar(toolbar);
        setActionBarTitle(getString(R.string.activity_style_title_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_blue);

        mViewPager = (ViewPager) findViewById(R.id.activity_style_tabanim_viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.activity_style_tabanim_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        //showToast("One");
                        break;
                    case 1:
                        //showToast("Two");
                        break;
                    case 2:
                        //showToast("Three");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public Fragment GetCurrentPageFragment()
    {
        return mAdapter.getItem(mViewPager.getCurrentItem());
    }


    //액션바 타이틀 세팅
    public void setActionBarTitle(String title)
    {
        //액션바에 폰트적용
        SpannableString s = new SpannableString(title);
        s.setSpan(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO), Typeface.BOLD, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
//        if(title.equals(getString(R.string.view_tabbar_bottom_item_codi_text)))
//        {
//            getSupportActionBar().setTitle("");
//            getSupportActionBar().setIcon(R.drawable.ic_title_logo);
//        }
//        else
//            getSupportActionBar().setIcon(null);
    }

    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //처음 로그인 이라면 스타일평가 Fragment만 보여준다.
        if(mIsFirst)
        {
            mAdapter.addFrag(StyleEstimationFragment.newInstance(mIsFirst), getString(R.string.fragment_style_estimation_text),0);
            viewPager.setAdapter(mAdapter);
            return;
        }
        mAdapter.addFrag(StyleEstimationFragment.newInstance(mIsFirst), getString(R.string.fragment_style_estimation_text),0);
        mAdapter.addFrag(StyleModifyFragment.newInstance(), getString(R.string.fragment_style_modify_text),0);
        mAdapter.addFrag(StyletasteFragment.newInstance(), getString(R.string.fragment_style_taste_text),0);
        viewPager.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
