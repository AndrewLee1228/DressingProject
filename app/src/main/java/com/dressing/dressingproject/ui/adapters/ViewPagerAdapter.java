package com.dressing.dressingproject.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 2.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter
{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final FragmentManager mManager;
    private int mFragmentFlag;
    private int mPosition;

    public ViewPagerAdapter(android.support.v4.app.FragmentManager manager)
    {
        super(manager);
        mManager = manager;
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = mFragmentList.get(position);
        return fragment;


    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
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

    public void ClearFragment()
    {
        mFragmentList.clear();
        mFragmentTitleList.clear();
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
