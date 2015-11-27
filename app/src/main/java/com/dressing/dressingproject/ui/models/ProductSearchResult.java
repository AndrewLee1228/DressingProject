package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 24.
 */
public class ProductSearchResult
{

    public int code;
    public String msg;
    @SerializedName("itemData")
    public ArrayList<ProductModel> list = new ArrayList<ProductModel>();
}
