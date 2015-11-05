package com.dressing.dressingproject.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dressing.dressingproject.R;


public class StoreTitleFragment extends Fragment {

    public static final String ARGS_TITLE = "title";

    public static StoreTitleFragment newInstance(String title) {
        StoreTitleFragment f = new StoreTitleFragment();
        Bundle b = new Bundle();
        b.putString(ARGS_TITLE, title);
        f.setArguments(b);
        return f;
    }

    public StoreTitleFragment() {
        // Required empty public constructor
    }


    String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            mTitle = arg.getString(ARGS_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_title, container, false);
        TextView tv = (TextView)view.findViewById(R.id.fragment_store_title_text);
        tv.setText(mTitle);
        return view;
    }

}
