package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.models.MemberInfo;
import com.dressing.dressingproject.ui.models.SignInResult;
import com.dressing.dressingproject.ui.models.UserItem;
import com.dressing.dressingproject.util.Validate;

import java.util.ArrayList;

/**
 * Created by lee on 15. 10. 30.
 * 로그인,비밀번호찾기
 */
public class SignInFragment extends Fragment
{
    private AutoCompleteTextView mEmail;
    private EditText mPassword;

    public SignInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signin,container,false);

        mEmail = (AutoCompleteTextView) view.findViewById(R.id.fragment_signin_email);
        mPassword = (EditText) view.findViewById(R.id.fragment_signin_password);


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

                Validate validate = Validate.getInstance();

                if (!validate.validEmail(mEmail.getText().toString())) {
                    Toast.makeText(getContext(), "이메일 주소가 유효하지 않습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //주소가 입력되었는지 확인
//                ((LoginActivity) getActivity()).startActivity(intent);


                UserItem item = new UserItem();
                item.setEmail(mEmail.getText().toString());
                item.setPassword(mPassword.getText().toString());
                NetworkManager.getInstance().requestPostSignin(getContext(), item, new NetworkManager.OnResultListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult result) {
                        int code = result.code;
                        String msg = result.msg;
                        ArrayList<MemberInfo> memberInfos = result.info;
                        String email = MemberInfo.emali;
                        String nickName = MemberInfo.nickName;
                        String memberImg =MemberInfo.memberImg;

                    }

                    @Override
                    public void onFail(int code) {

                    }
                });

            }
        });
        return view;
    }

    /**
     * 안드로이드 로그인 참고하여 나머지 부분 구현하기!
     *
     */


}
