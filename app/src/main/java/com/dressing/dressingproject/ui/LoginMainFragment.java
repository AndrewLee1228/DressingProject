package com.dressing.dressingproject.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.manager.PropertyManager;
import com.dressing.dressingproject.ui.models.LoginInfo;
import com.dressing.dressingproject.ui.models.SignInResult;
import com.dressing.dressingproject.util.Constants;
import com.dressing.dressingproject.util.FacebookUserConstants;
import com.dressing.dressingproject.util.FontManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by lee on 15. 10. 29.
 */
public class LoginMainFragment extends Fragment implements View.OnClickListener
{

    private Bundle mLoginBundle;
    private ProgressDialog mProgressDialog;

    // Facebook
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;

    public LoginMainFragment()
    {
        //생성자
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //테스트 계정 토스트 메시지
        Toast.makeText(getActivity(), "회원가입 기능을 일시적으로 막아두었습니다.\n아래의 test 아이디로 접속해주세요 \n id: test@test.com \n pwd:testtest", Toast.LENGTH_LONG).show();
        
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

        ImageView facebookSignin = (ImageView) view.findViewById(R.id.fragment_main_login_facebook_btn);
        facebookSignin.setOnClickListener(this);

        //소셜로그인 인증 다이얼로그
        //다이얼로그 초기화
        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Authenticating...");

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //소셜로그인 초기화
        InitSocialLogin();

        //로그인에 필요한 정보를 담는 번들 객체를 생성한다.
        setData();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    private void setData() {
        mLoginBundle = new Bundle();
    }

    private void InitSocialLogin() {
        // Facebook Login
        mCallbackManager = CallbackManager.Factory.create();

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        mAccessTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //회원가입버튼
            case R.id.fragment_main_login_signup_btn:
                ((LoginActivity)getActivity()).pushSingUpFragment();
            //페이스북 로그인
            case R.id.fragment_main_login_facebook_btn:
                FacebookLogin();
        }

    }

    private void FacebookLogin() {
        //페이스북 권한 세팅!
        LoginManager.getInstance().logInWithReadPermissions(getActivity(),
                Arrays.asList("public_profile", "user_about_me", "email", "user_friends"));

        //로그인 다이얼로그 보이기
        LoginDialogShowAndDismiss(true);
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResults) {
                        AccessToken accessToken = loginResults.getAccessToken();
                        Profile.fetchProfileForCurrentAccessToken();
                        Profile profile = Profile.getCurrentProfile();

                        /**
                         * 페이스북 sdk에 현재 프로필이 저장되어 있다면 아래 profile은 null 이 아니다.
                         * sdk에 정보가 없을 경우! 새로 요청한다.
                         */
                        if (profile != null) {
                            mLoginBundle.putString("id", profile.getId());
                            mLoginBundle.putString("name", profile.getName());
                            mLoginBundle.putString("picture", String.valueOf(profile.getProfilePictureUri(200,200)));
                            mLoginBundle.putString("accessToken", accessToken.getToken());
                            SigninProcess();
                        }
                        else
                        {
                            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject Userobject, GraphResponse response) {
                                            Log.i("info", Userobject.toString());
                                            //페이스북 로그인시 얻어온 유저의 정보를 확인하는 로직. 필요할 경우만 사용한다.
                                            //로그인에 사용될 번들객체에 페이스북 정보를 담는 역할도 한다.
                                            int state = checkFacebookJsonObject(Userobject);
                                            if(Userobject.has("gender") && Userobject.has("location") && Userobject.has("birth")) {
                                                SigninProcess();
                                            } else {
                                                SigninProcess();
                                            }
                                            LoginDialogShowAndDismiss(false);//로그인 다이얼로그 숨기기
                                        }
                                    });
                            request.executeAsync();
                        }


                    }

                    @Override
                    public void onCancel() {
                        LoginDialogShowAndDismiss(false);//로그인 다이얼로그 숨기기
                    }

                    @Override
                    public void onError(FacebookException e) {
                        LoginDialogShowAndDismiss(false);//로그인 다이얼로그 숨기기
                    }
                });

    }


    /**
     * 페이스북 로그인시 얻어온 유저의 정보를 확인하는 로직.
     * 페이스북 유저가 허락한 권한에 따라 얻어온 정보가 다르므로 그 정보의 구분이 필요할 경우 사용한다.
     * @param obj
     * @return retnvalue (페이스북 유저가 허락한 권한에 정보의 따라 리턴하는 상수값이 다르다.)
     */
    private int checkFacebookJsonObject(JSONObject obj) {
        int retnvalue = FacebookUserConstants.USERINFO_COMPLETE; // 기본 성별없음 설정
        int gender_flag = 0;
        int location_flag = 0;

        try {
            mLoginBundle.putString("id", obj.getString("id"));
            mLoginBundle.putString("name", obj.getString("name"));

            mLoginBundle.putString("picture", "http://graph.facebook.com/"+obj.getString("id")+"/picture?type=large");

            if(obj.has("gender") || obj.has("location") || obj.has("birth")) {
                if(obj.has("gender")) {
                    if(obj.getString("gender").equals("male")) {
                        mLoginBundle.putString("sex", "m");
                    } else if(obj.getString("gender").equals("woman")) {
                        mLoginBundle.putString("sex", "w");
                    }
                    gender_flag = 1;
                    retnvalue = FacebookUserConstants.USERINFO_NOLOCATION;
                }

                if(obj.has("location")) {
                    mLoginBundle.putString("location", new JSONObject(obj.getString("location")).getString("name"));
                    location_flag = 1;

                    if(gender_flag == 1) {
                        retnvalue = FacebookUserConstants.USERINFO_COMPLETE;
                    } else {
                        retnvalue = FacebookUserConstants.USERINFO_NOSEX;
                    }
                }

                if(obj.has("birth")) {
                    mLoginBundle.putString("birth", obj.getString("birth"));
                    retnvalue = 3;
                }
            } else {
                retnvalue = FacebookUserConstants.USERINFO_NOALL;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retnvalue;
    }


    /**
     * 로그인 프로세스 시작
     */
    private void SigninProcess() {


//        //번들객체에 있는 값을 아래와 같이 가져와 사용한다.
//        //mLoginBundle.getString("id"), mLoginBundle.getString("name")
//        String id = mLoginBundle.getString("id");
//        String name = mLoginBundle.getString("name");
//        String picture = mLoginBundle.getString("picture");
//        String accessTokenString = mLoginBundle.getString("accessToken");
//
//        Toast.makeText(getActivity(), "아이디 "+id +"\n " +
//                "이름 "+ name +"\n "+
//                "프로필 이미지 "+ picture+"\n"+
//                "엑세스토큰 "+accessTokenString, Toast.LENGTH_SHORT).show();
//        Log.d("picture", picture);

        /**
         * 페이스북 정보를(엑세스토큰 그리고 기타 정보들....) 서버로 보내고 받은 응답으로
         * 인증절차를 통해 회원가입을 하거나 이미 가입 되어있다면 로그인 시키는 등의 작업을 처리한다.
         */

        /**
         * 서버에서 응답을 받아 로그인 절차가 완료되었다면
         * 자동 로그인을 위하여 SharedPreferences에 사용자 정보를 저장한후
         * 메인화면등의 새로운 액티비티로 이동한다.
         */


        LoginDialogShowAndDismiss(false); //모든 절차가 끝나면 다이얼로그 dismiss한다.

        SaveLoginInfo();    //로그인 정보 저장!

        login(); //앱서버와 통신하여 로그인 진행!
    }

    /**
     * 로그인 정보 저장!
     */
    private void SaveLoginInfo() {

        PropertyManager propertyManager = PropertyManager.getInstance();

        String loginType = Constants.LOGIN_TYPE_FACEBOOK;

        String id = mLoginBundle.getString("id");
        String name = mLoginBundle.getString("name");
        String picture = mLoginBundle.getString("picture");
        String accessTokenString = mLoginBundle.getString("accessToken");

        Bundle extras = new Bundle();
        extras.putString(Constants.LOGIN_USER_ID_KEY, id);
//        extras.putString(Constants.LOGIN_USER_PASSWORD_KEY, password);
        extras.putString(Constants.LOGIN_USER_NICKNAME, name);
        extras.putString(Constants.LOGIN_USER_IMG, picture);
        extras.putString(Constants.LOGIN_ACCESSTOKEN, accessTokenString);
        extras.putString(Constants.LOGIN_TYPE, loginType);

        propertyManager.saveLoginInfo(extras); //로그인 정보 저장

    }

    /**
     * 로그인 다이얼로그 보이기 , 숨기기
     */
    public void LoginDialogShowAndDismiss(boolean isTure)
    {
        if (isTure == true) {
            mProgressDialog.show();
        }
        else mProgressDialog.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("onActivityResult", "requestCode : " + requestCode + "resultCode :" + resultCode + "data" + data);

        //로그인 성공
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode() && resultCode == -1) {
            //facebook signin
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else
        {
            LoginDialogShowAndDismiss(false);
        }

    }

    private void login() {

        //로그인 타입 정보와 유저 ID를 가져옴.
        PropertyManager propertyManager = PropertyManager.getInstance();
        LoginInfo loginInfo = propertyManager.getLoginInfo();

        //임시로 Test 계정으로 로그인 시킴!
        loginInfo.setUserId("test@test.com");
        loginInfo.setPassword("testtest");

        NetworkManager.getInstance().requestPostSignin(getContext(), loginInfo, new NetworkManager.OnResultListener<SignInResult>() {
            @Override
            public void onSuccess(SignInResult result) {
                int code = result.code;
                String msg = result.msg;

                //PropertyManager propertyManager = PropertyManager.getInstance();
                //!TextUtils.isEmpty(result._id)
                if (msg.equals("Success")) {

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

    @Override
    public void onStart() {
        super.onStart();
        ((LoginActivity)getActivity()).setCurrentPage(0);
    }
}
