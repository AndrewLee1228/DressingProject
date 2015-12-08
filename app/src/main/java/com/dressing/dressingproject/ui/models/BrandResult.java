package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 12. 1.
 */
public class BrandResult
{
    public int code;
    public String msg;
    @SerializedName("mallList")
    public ArrayList<BrandModel> list = new ArrayList<BrandModel>();
}
