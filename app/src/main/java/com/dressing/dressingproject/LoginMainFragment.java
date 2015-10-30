package com.dressing.dressingproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lee on 15. 10. 29.
 */
public class LoginMainFragment extends Fragment
{
    public LoginMainFragment() {
        //생성자
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_login,container,false);

        return view;
    }
}
