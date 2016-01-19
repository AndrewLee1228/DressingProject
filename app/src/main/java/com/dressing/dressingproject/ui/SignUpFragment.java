package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.manager.PropertyManager;
import com.dressing.dressingproject.ui.models.LoginInfo;
import com.dressing.dressingproject.ui.models.SignUpResult;
import com.dressing.dressingproject.util.Constants;
import com.dressing.dressingproject.util.FontManager;
import com.dressing.dressingproject.util.Validate;

/**
 * Created by lee on 15. 10. 30.
 * 회원가입
 */
public class SignUpFragment extends Fragment {

    private AutoCompleteTextView mEmailAcText;
    private AutoCompleteTextView mNickAcText;
    private AutoCompleteTextView mPasswordAcText;
    private AutoCompleteTextView mPasswordCheckAcText;
    //private AutoCompleteTextView mAreaAcText;

    public SignUpFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup,container,false);

        mEmailAcText = (AutoCompleteTextView)view.findViewById(R.id.fragment_signup_email_edittext);
        mNickAcText = (AutoCompleteTextView)view.findViewById(R.id.fragment_signup_nick_edittext);
        mPasswordAcText = (AutoCompleteTextView)view.findViewById(R.id.fragment_signup_password_edittext);
        mPasswordCheckAcText = (AutoCompleteTextView)view.findViewById(R.id.fragment_signup_password_checked_edittext);
        //mAreaAcText = (AutoCompleteTextView)view.findViewById(R.id.fragment_signup_area_edittext);

        Button signUpBtn = (Button)view.findViewById(R.id.fragment_signup_signupbtn);
        signUpBtn.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = GetTextFromView(mEmailAcText);
                String password = GetTextFromView(mPasswordAcText);
                String passwordCheck = GetTextFromView(mPasswordCheckAcText);
                String nick = GetTextFromView(mNickAcText);
                //String area = GetTextFromView(mEmailAcText);

                Validate validate = Validate.getInstance();

                if (!validate.validEmail(email)) {
                    Toast.makeText(getContext(), "이메일 주소가 유효하지 않습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!validate.validPsaaword(password) ||
                        !validate.validPsaaword(passwordCheck)) {
                    Toast.makeText(getContext(), "패스워드 길이가 맞지 않습니다 \n 4글자 이상 10글자 이하!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(passwordCheck))
                {
                    Toast.makeText(getContext(), "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUserId(email);
                loginInfo.setPassword(nick);
                loginInfo.setNickName(password);

                //주소가 입력되었는지 확인

                NetworkManager.getInstance().requestPostSignUp(getContext(),loginInfo,new NetworkManager.OnResultListener<SignUpResult>(){
                        @Override
                        public void onSuccess(SignUpResult result) {
                            int code = result.code;
                            String msg = result.msg;
                            Log.d("Network : ",Integer.toString(code));
                            PropertyManager propertyManager = PropertyManager.getInstance();

                            Bundle extras = new Bundle();
                            extras.putString(Constants.LOGIN_USER_ID_KEY, loginInfo.getUserId());
                            extras.putString(Constants.LOGIN_USER_PASSWORD_KEY, loginInfo.getPassword());
                            extras.putString(Constants.LOGIN_USER_NICKNAME, loginInfo.getNickName());
                            //extras.putString(Constants.LOGIN_USER_IMG, "");
                            extras.putString(Constants.LOGIN_TYPE, Constants.LOGIN_TYPE_NORMAL);

                            propertyManager.saveLoginInfo(extras); //로그인 정보 저장

                            StartMainActivity();
                        }

                        @Override
                        public void onFail(int code) {

                        }
                });

            }
        });

        return view;
    }

    private void StartMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    private String GetTextFromView(AutoCompleteTextView view)
    {
        return view.getText().toString();
    }
}
