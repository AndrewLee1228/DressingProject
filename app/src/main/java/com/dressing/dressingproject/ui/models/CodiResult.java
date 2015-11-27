package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 9.
 */
public class CodiResult {
    public int code;
    public String msg;
    @SerializedName("coordinationList")
    public ArrayList<CodiModel> items = new ArrayList<CodiModel>();
}
