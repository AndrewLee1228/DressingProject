package com.dressing.dressingproject.manager;

import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.VersionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 3.
 */
public class NetworkManager {

    private static List<CodiModel> items = new ArrayList<>();
    private static List<String> list = new ArrayList<String>();

    static {
        for (int i = 1; i <= 10; i++) {
            items.add(new CodiModel("Item " + i, "http://lorempixel.com/500/500/animals/" + i));
        }


        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }
    }

    public NetworkManager() {

    }

    public static List<CodiModel> getRecommendList() {
        return items;
    }

    public static List<String> getList() {
        return list;
    }
}
