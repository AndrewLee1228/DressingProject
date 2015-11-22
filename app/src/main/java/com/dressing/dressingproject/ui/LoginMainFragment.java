package com.dressing.dressingproject.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.util.FontManager;

/**
 * Created by lee on 15. 10. 29.
 */
public class LoginMainFragment extends Fragment implements View.OnClickListener
{

    public LoginMainFragment()
    {
        //생성자
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_login,container,false);

        //로고 설명 텍스트
        TextView logoDescript = (TextView)view.findViewById(R.id.ic_fragment_main_login_logo_descript_text);
        logoDescript.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);

        TextView loginText = (TextView) view.findViewById(R.id.fragment_main_login_alreadyuser_text);
        loginText.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).pushSingInFragment();
            }
        });

        Button signUpBtn = (Button) view.findViewById(R.id.fragment_main_login_signup_btn);
        signUpBtn.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        signUpBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //회원가입버튼
            case R.id.fragment_main_login_signup_btn:
                ((LoginActivity)getActivity()).pushSingUpFragment();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        ((LoginActivity)getActivity()).setCurrentPage(0);
    }
}
