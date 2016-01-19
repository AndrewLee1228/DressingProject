package com.dressing.dressingproject.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.dressing.dressingproject.ui.models.LoginInfo;
import com.dressing.dressingproject.util.Constants;

/**
 * Created by lee on 15. 11. 19.
 */
public class PropertyManager {


    private static PropertyManager instance;
    private final Context mContext;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    private PropertyManager() {
        mContext = ApplicationLoader.getContext();
    }

    /**
     * GCM을 위한 RegistrationID 등록
     * @param regId
     */
    public void setRegistrationToken(String regId) {
        SharedPreferences prefs = mContext.getSharedPreferences(ApplicationLoader.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PROPERTY_REG_ID_KEY, regId);
        editor.commit();
    }

    public String getRegistrationToken() {
        SharedPreferences prefs = mContext.getSharedPreferences(ApplicationLoader.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(Constants.PROPERTY_REG_ID_KEY, "");
        return registrationId;
    }

    /**
     * 로그인 정보를 저장한다.
     * @param info
     */
    public void saveLoginInfo(Bundle info) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.LOGININFO_PREF_KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.LOGIN_USER_ID_KEY, info.getString(Constants.LOGIN_USER_ID_KEY));
        editor.putString(Constants.LOGIN_USER_PASSWORD_KEY, info.getString(Constants.LOGIN_USER_PASSWORD_KEY));
        editor.putString(Constants.LOGIN_USER_NICKNAME,info.getString(Constants.LOGIN_USER_NICKNAME));
        editor.putString(Constants.LOGIN_USER_IMG,info.getString(Constants.LOGIN_USER_IMG));
        editor.putString(Constants.LOGIN_TYPE,info.getString(Constants.LOGIN_TYPE));
        editor.commit();
    }


    /**
     *사용자 정보 반환
     * @return LoginInfo(Id및Password)
     */
    public LoginInfo getLoginInfo() {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.LOGININFO_PREF_KEY, Activity.MODE_PRIVATE);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(prefs.getString(Constants.LOGIN_USER_ID_KEY, ""));
        loginInfo.setPassword(prefs.getString(Constants.LOGIN_USER_PASSWORD_KEY, ""));
        loginInfo.setNickName(prefs.getString(Constants.LOGIN_USER_NICKNAME, ""));
        loginInfo.setImg(prefs.getString(Constants.LOGIN_USER_IMG, ""));
        loginInfo.setLoginType(prefs.getString(Constants.LOGIN_TYPE, ""));
        loginInfo.setAccessToken(prefs.getString(Constants.LOGIN_ACCESSTOKEN, ""));
        return loginInfo;
    }

    /**
     * 시
     */
    public void setCityLocation(String location) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.LOCATION_PREF_KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.LOCATION_CITY_KEY, location);
        editor.commit();
    }
    public String getCityLocation() {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.LOGININFO_PREF_KEY, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.LOCATION_CITY_KEY, "");
    }

    /**
     * 구
     */

    public void setGuLocation(String location) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.LOCATION_PREF_KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.LOCATION_GU_KEY, location);
        editor.commit();
    }

    public String getGuLocation() {
        SharedPreferences prefs = mContext.getSharedPreferences(Constants.LOGININFO_PREF_KEY, Activity.MODE_PRIVATE);
        return prefs.getString(Constants.LOCATION_GU_KEY, "");
    }

}
