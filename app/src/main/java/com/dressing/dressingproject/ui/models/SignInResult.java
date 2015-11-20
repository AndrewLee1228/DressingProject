package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 18.
 */
public class SignInResult {
    public int code;
    public String msg;
    @SerializedName("memberInfo")
    public ArrayList<MemberInfo> info;
}
