package com.dressing.dressingproject.ui.models;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 6.
 */
public class CodiProducts {
    String title;
    String link;
    int total;
    int start;
    int display;
    //    @SerializedName("item")
    public ArrayList<ProductModel> items = new ArrayList<ProductModel>();
}
