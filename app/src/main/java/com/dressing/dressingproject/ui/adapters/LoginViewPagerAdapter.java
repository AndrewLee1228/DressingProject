package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 1.
 */
public class LoginViewPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private int[] mResIds = {R.drawable.fragment_main_login_bg, R.drawable.fragment_signin_bg,R.drawable.fragment_signin_bg};

    public LoginViewPagerAdapter(Context context) {

        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResIds.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        View itemView =  mLayoutInflater.inflate(R.layout.activity_login_pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        ImageView imageView = new ImageView(mContext);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(layoutParams);
        //imageView.setImageResource(mResIds[position]);
        Glide.with(mContext).load(mResIds[position]).into(imageView);

        container.addView(itemView);

        return itemView;
    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((LinearLayout) object);
    }

    public boolean isViewFromObject(View container, Object object) {
        return container.equals(object);
    }
}
