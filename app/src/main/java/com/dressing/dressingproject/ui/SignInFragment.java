package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.manager.PropertyManager;
import com.dressing.dressingproject.ui.models.LoginInfo;
import com.dressing.dressingproject.ui.models.SignInResult;
import com.dressing.dressingproject.util.Constants;
import com.dressing.dressingproject.util.FontManager;
import com.dressing.dressingproject.util.Validate;

/**
 * Created by lee on 15. 10. 30.
 * 로그인,비밀번호찾기
 */
public class SignInFragment extends Fragment
{
    private AutoCompleteTextView mEmail;
    private AutoCompleteTextView mPassword;

    public SignInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signin,container,false);

        TextView logoDescript = (TextView)view.findViewById(R.id.fragment_signin_logo_descript_text);
        logoDescript.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);

        mEmail = (AutoCompleteTextView) view.findViewById(R.id.fragment_signin_email);
        mEmail.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        mPassword = (AutoCompleteTextView) view.findViewById(R.id.fragment_signin_password);
        mPassword.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        mPassword.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    switch (actionId)

                    {

                        case EditorInfo.IME_ACTION_DONE:

                            //수행동작 입력
                            login();
                            break;

                        case EditorInfo.IME_ACTION_NEXT:

                            //수행동작 입력

                            break;

                    }

                    return false;

                }

            });


        TextView findPasswordBtn = (TextView) view.findViewById(R.id.fragment_signin_findpassword_text);
        findPasswordBtn.setTypeface(FontManager.getInstance().

        getTypeface(getContext(), FontManager

                .NOTO),Typeface.BOLD);
        findPasswordBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
            Toast.makeText(getActivity(), "다이얼로그", Toast.LENGTH_SHORT).show();
        }
        }

        );

        //회원가입화면으로 이동!
//        Button signUpBtn = (Button) view.findViewById(R.id.fragment_signin_signup_btn);
//        signUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((LoginActivity) getActivity()).pushSingUpFragment();
//            }
//        });

        Button signInBtn = (Button) view.findViewById(R.id.fragment_signin_signin_btn);
        signInBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                login();
            }
        } );


        return view;
    }

    private void login() {
        Validate validate = Validate.getInstance();

        if (!validate.validEmail(mEmail.getText().toString())) {
            Toast.makeText(getContext(), "이메일 주소가 유효하지 않습니다!", Toast.LENGTH_SHORT).show();
            return;
        }

        //주소가 입력되었는지 확인
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(mEmail.getText().toString());
        loginInfo.setPassword(mPassword.getText().toString());
        NetworkManager.getInstance().requestPostSignin(getContext(), loginInfo, new NetworkManager.OnResultListener<SignInResult>() {
            @Override
            public void onSuccess(SignInResult result) {
                int code = result.code;
                String msg = result.msg;

                PropertyManager propertyManager = PropertyManager.getInstance();
                //!TextUtils.isEmpty(result._id)
                if (msg.equals("Success")) {
                    String userId = mEmail.getText().toString();
                    String password = mPassword.getText().toString();
                    String nickName = result.memberInfo.nickName;
                    String userImg = result.memberInfo.memberImg;
                    String loginType = Constants.LOGIN_TYPE_NORMAL;

                    Bundle extras = new Bundle();
                    extras.putString(Constants.LOGIN_USER_ID_KEY, userId);
                    extras.putString(Constants.LOGIN_USER_PASSWORD_KEY, password);
                    extras.putString(Constants.LOGIN_USER_NICKNAME, nickName);
                    extras.putString(Constants.LOGIN_USER_IMG, userImg);
                    extras.putString(Constants.LOGIN_TYPE, loginType);

                    propertyManager.saveLoginInfo(extras); //로그인 정보 저장

                    StartMainActivity();
                }
                else if(code == 400)
                {
                    Toast.makeText(getActivity(), "네트워크 연결에 실패하였습니다!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "아이디 또는 패스워드를 확인해 주세요!", Toast.LENGTH_SHORT).show();
                }

//                ArrayList<MemberInfo> memberInfos = result.info;
//                String email = MemberInfo.emali;
//                String nickName = MemberInfo.nickName;
//                String memberImg = MemberInfo.memberImg;
                //여기서 아이디 패스워드 저장하고 넘어가기
                //updateFilter.makeText(getContext(), "아이디/패스워드를 확인해 주세요!", updateFilter.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(int code) {
                Toast.makeText(getContext(), "아이디/패스워드를 확인해 주세요!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void StartMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }


}
