package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 10. 30.
 * 로그인,비밀번호찾기
 */
public class SignInFragment extends Fragment
{
    public SignInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin,container,false);

        TextView findPasswordBtn = (TextView) view.findViewById(R.id.fragment_signin_findpassword_text);
        findPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "다이얼로그", Toast.LENGTH_SHORT).show();
            }
        });

        Button signUpBtn = (Button) view.findViewById(R.id.fragment_signin_signup_btn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).pushSingUpFragment();
            }
        });

        Button signInBtn = (Button)view.findViewById(R.id.fragment_signin_signin_btn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((LoginActivity) getActivity()).startActivity(intent);
            }
        });
        return view;
    }

    /**
     * 안드로이드 로그인 참고하여 나머지 부분 구현하기!
     *
     */


}
