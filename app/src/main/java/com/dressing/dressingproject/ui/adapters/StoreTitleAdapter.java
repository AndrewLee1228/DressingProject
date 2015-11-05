package com.dressing.dressingproject.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dressing.dressingproject.ui.StoreTitleFragment;


public class StoreTitleAdapter extends FragmentStatePagerAdapter {
    public StoreTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return StoreTitleFragment.newInstance("title : " + position);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
