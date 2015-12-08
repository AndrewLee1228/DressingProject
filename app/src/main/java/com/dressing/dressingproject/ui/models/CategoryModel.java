package com.dressing.dressingproject.ui.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 15.
 */
public class CategoryModel implements Serializable {

    private String categorytext;
    private int resoruceId;
    private ArrayList<CategoryModel> subCategoryList = new ArrayList<CategoryModel>();

    public CategoryModel(String categorytext, int resoruceId) {
        this.categorytext = categorytext;
        this.resoruceId = resoruceId;
    }

    public String getCategoryText() {
        return categorytext;
    }

    public void setCategorytext(String categorytext) {
        this.categorytext = categorytext;
    }

    public int getResoruceId() {
        return resoruceId;
    }

    public void setResoruceId(int resoruceId) {
        this.resoruceId = resoruceId;
    }

    public ArrayList<CategoryModel> getSubCategoryList() {
        return subCategoryList;
    }

    public void addSubCategoryList(ArrayList<CategoryModel> subCategoryList)
    {
        this.subCategoryList.addAll(subCategoryList);
    }
}
