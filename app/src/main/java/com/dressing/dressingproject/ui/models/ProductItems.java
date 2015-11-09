package com.dressing.dressingproject.ui.models;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 9.
 */
public class ProductItems {
    String title;
    String link;
    int total;
    int start;
    int display;
    //    @SerializedName("item")
    public ArrayList<CodiModel> items = new ArrayList<CodiModel>();
}
