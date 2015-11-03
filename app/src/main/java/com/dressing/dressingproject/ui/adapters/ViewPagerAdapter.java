package com.dressing.dressingproject.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 2.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private int mFragmentFlag;

    public ViewPagerAdapter(android.support.v4.app.FragmentManager manager)
    {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title,int FRAGMENT_FLAG) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentFlag = FRAGMENT_FLAG;
    }

    public int getFragmentFlag()
    {
        return mFragmentFlag;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
