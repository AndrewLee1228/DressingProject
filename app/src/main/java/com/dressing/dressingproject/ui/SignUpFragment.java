package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 10. 30.
 * 회원가입
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup,container,false);
        return view;
    }
}
