package com.dressing.dressingproject.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.StoreLocationActivity;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;


public class StoreTitleAdapter extends PagerAdapter {

    private final Context mContext;
    ArrayList<ProductModel> lists = new ArrayList<ProductModel>();
    private ImageView mImageView;
    private ProgressWheel mProgressWheel;

    public StoreTitleAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_store_tile, container, false);
        mImageView = (ImageView) view.findViewById(R.id.image_display);
        mProgressWheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);

        //아이템 세팅
        ProductModel productModel = lists.get(position);

        //parent 에 정보 세팅!
        ((StoreLocationActivity)mContext).setInfoItem(productModel);

        // 이미지 로딩
        Glide.with(mContext)
                .load(productModel.getMapURL())
                //                .centerCrop()
                //                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        mProgressWheel.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        mProgressWheel.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addList(ArrayList<ProductModel> list) {
        lists.addAll(list);
        notifyDataSetChanged();
    }

}
