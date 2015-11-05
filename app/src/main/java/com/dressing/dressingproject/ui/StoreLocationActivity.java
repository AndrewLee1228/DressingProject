package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.StoreTitleAdapter;

public class StoreLocationActivity extends AppCompatActivity {

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_location);

        InitLayout();
        InitPager();
    }

    private void InitLayout() {
        getSupportActionBar().setTitle("코디 상세보기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void InitPager() {
        mPager = (ViewPager)findViewById(R.id.activity_store_location_pager);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(0);
        mPager.setAdapter(new StoreTitleAdapter(getSupportFragmentManager()));
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(MainActivity.this, "selected : " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
