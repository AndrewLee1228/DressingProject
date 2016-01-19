package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;

public class IntroSlide extends Fragment {

    private static final String ARG_IMAGE_RES_ID = "mImageResId";

    public static IntroSlide newInstance(int layoutResId) {
        IntroSlide sampleSlide = new IntroSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int mImageResId;

    public IntroSlide() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_IMAGE_RES_ID))
            mImageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro, container, false);
        Glide.with(this).load(mImageResId).into((ImageView)view.findViewById(R.id.imageview));
        return view;
    }

}