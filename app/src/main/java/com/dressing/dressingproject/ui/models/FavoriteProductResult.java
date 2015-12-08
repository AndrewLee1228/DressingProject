package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteProductResult {
    public int code;
    public String msg;
    @SerializedName("itemList")
    public ArrayList<ProductModel> items = new ArrayList<ProductModel>();
}
