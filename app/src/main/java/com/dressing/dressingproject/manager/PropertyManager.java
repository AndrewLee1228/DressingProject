package com.dressing.dressingproject.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lee on 15. 11. 19.
 */
public class PropertyManager {

    public static final String LOGIN_TYPE_NORMAL = "login_type_normal";
    public static final String LOGIN_TYPE_FACEBOOK = "login_type_facebook";
    public static final String LOGIN_TYPE_GOOGLE = "login_type_google";
    public static final String USER_IMG = "user_img";
    public static final String USER_NICKNAME = "user_nickname";

    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ApplicationLoader.getContext());
        mEditor = mPrefs.edit();
    }

    private static final String REG_ID = "regToken";

    public void setRegistrationToken(String regId) {
        mEditor.putString(REG_ID, regId);
        mEditor.commit();
    }

    public String getRegistrationToken() {
        return mPrefs.getString(REG_ID, "");
    }

    /**
     * 시
     */
    private static final String FIELD_CITY_LOCATION = "city_location";
    public void setCityLocation(String location) {
        mEditor.putString(FIELD_CITY_LOCATION, location);
        mEditor.commit();
    }
    public String getCityLocation() {
        return mPrefs.getString(FIELD_CITY_LOCATION, "");
    }


    /**
     * 구
     */
    private static final String FIELD_GU_LOCATION = "gu_location";

    public void setGuLocation(String location) {
        mEditor.putString(FIELD_GU_LOCATION, location);
        mEditor.commit();
    }

    public String getGuLocation() {
        return mPrefs.getString(FIELD_GU_LOCATION, "");
    }

    /**
     * 로그인타입
     */

    public static final String LOGIN_TYPE = "login_type";

    public void setLoginType(String loginType){
        mEditor.putString(LOGIN_TYPE, loginType);
        mEditor.commit();
    }

    public String getLoginType(){
        return mPrefs.getString(LOGIN_TYPE, "");
    }

    /**
     * 아이디
     */
    private static final String USER_ID = "userId";

    public void setUserId(String id) {
        mEditor.putString(USER_ID, id);
        mEditor.commit();
    }

    public String getUserId() {
        return mPrefs.getString(USER_ID, "");
    }

    /**
     * 패스워드
     */
    private static final String USER_PASSWORD = "userPassword";

    public void setUserPassword(String password) {
        mEditor.putString(USER_PASSWORD, password);
        mEditor.commit();
    }

    public String getUserPassword() {
        return mPrefs.getString(USER_PASSWORD, "");
    }

    public void setUserImgURL(String memberImg) {
        mEditor.putString(USER_IMG, memberImg);
        mEditor.commit();
    }

    public String getUserImgURL() {
        return mPrefs.getString(USER_IMG, "");
    }

    public void setUserNickName(String nickName) {
        mEditor.putString(USER_NICKNAME, nickName);
        mEditor.commit();
    }

    public String getUserNickName() {
        return mPrefs.getString(USER_NICKNAME, "");
    }
}
