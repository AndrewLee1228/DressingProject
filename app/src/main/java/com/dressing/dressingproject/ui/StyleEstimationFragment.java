package com.dressing.dressingproject.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 29.
 */
public class StyleEstimationFragment extends Fragment{
    public StyleEstimationFragment() {

    }

    public static StyleEstimationFragment newInstance() {

        Bundle args = new Bundle();

        StyleEstimationFragment fragment = new StyleEstimationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_style_estimation,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
