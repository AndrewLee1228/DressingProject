package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 6.
 */
public class ProductResult {
    @SerializedName("itemList")
    public ArrayList<ProductModel> list;
    public int code;
    public String msg;
}
