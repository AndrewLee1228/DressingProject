package com.dressing.dressingproject.ui.models;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteProductResult {
    String title;
    String link;
    int total;
    int start;
    int display;
    //    @SerializedName("item")
    public ArrayList<ProductModel> items = new ArrayList<ProductModel>();
}
