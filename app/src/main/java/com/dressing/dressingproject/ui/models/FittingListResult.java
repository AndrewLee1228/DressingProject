package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 30.
 */
public class FittingListResult {
    public int code;
    public String msg;
    public int totalPrice;
    @SerializedName("itemList")
    public ArrayList<FitModel> list = new ArrayList<FitModel>();
}
