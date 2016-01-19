package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 16. 1. 14.
 */
public class LoginInfo {
    private String password;
    private String userId;
    private String nickName;
    private String img;
    private String loginType;
    private String accessToken;


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
