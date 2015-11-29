package com.dressing.dressingproject.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.ViewPagerAdapter;
import com.dressing.dressingproject.util.FontManager;

public class StyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        IntiLayout();
   }

    private void IntiLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_style_tabanim_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.main_blue));
        setSupportActionBar(toolbar);
        setActionBarTitle(getString(R.string.activity_style_title_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_blue);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_style_tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_style_tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(StyleEstimationFragment.newInstance(), getString(R.string.fragment_style_estimation_text),0);
        adapter.addFrag(StyleModifyFragment.newInstance(), getString(R.string.fragment_style_modify_text),0);
        adapter.addFrag(StyletasteFragment.newInstance(), getString(R.string.fragment_style_taste_text),0);
        viewPager.setAdapter(adapter);
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
