package com.dressing.dressingproject.manager;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.facebook.FacebookSdk;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 6.
 */
public class ApplicationLoader extends MultiDexApplication {
    private static Context mContext;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //페이스북 초기화
        FacebookSdk.sdkInitialize(getApplicationContext());

    }

    public static Context getContext() {
        return mContext;
    }

    //Bottom Sheet 카테고리 리스트
    public static CategoryModel[] getCatagoryModels()
    {

        ArrayList<CategoryModel> subCategoryModels = new ArrayList<CategoryModel>();

        /*-------------catagory1---------------*/
        CategoryModel categoryModel1 = new CategoryModel("shirt", R.drawable.shirt);

        subCategoryModels.add(new CategoryModel("dressshirt", R.drawable.shirt_dressshirt));
        subCategoryModels.add(new CategoryModel("knit", R.drawable.shirt_knit));
        subCategoryModels.add(new CategoryModel("tshirt", R.drawable.shirt_tshirt));

        categoryModel1.addSubCategoryList(subCategoryModels);

        /*-------------catagory2---------------*/
        CategoryModel categoryModel2 = new CategoryModel("outer", R.drawable.outer);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("coat", R.drawable.outer_coat));
        subCategoryModels.add(new CategoryModel("jacket", R.drawable.outer_jacket));
        subCategoryModels.add(new CategoryModel("suit", R.drawable.outer_suit));

        categoryModel2.addSubCategoryList(subCategoryModels);

        /*-------------catagory3---------------*/
        CategoryModel categoryModel3 = new CategoryModel("bottom", R.drawable.bottom);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("cottonpants", R.drawable.bottom_cottonpants));
        subCategoryModels.add(new CategoryModel("jeans", R.drawable.bottom_jeans));
        subCategoryModels.add(new CategoryModel("slacks", R.drawable.bottom_slacks));

        categoryModel3.addSubCategoryList(subCategoryModels);

        /*-------------catagory4---------------*/
        CategoryModel categoryModel4 = new CategoryModel("accessory", R.drawable.accessory);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("bag", R.drawable.accessory_bag));
        subCategoryModels.add(new CategoryModel("belt", R.drawable.accessory_belt));
        subCategoryModels.add(new CategoryModel("gloves", R.drawable.accessory_gloves));
        subCategoryModels.add(new CategoryModel("tie", R.drawable.accessory_tie));

        categoryModel4.addSubCategoryList(subCategoryModels);

        /*-------------catagory5---------------*/
        CategoryModel categoryModel5 = new CategoryModel("shoes", R.drawable.shoes);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("boots", R.drawable.shoes_boots));
        subCategoryModels.add(new CategoryModel("shoes", R.drawable.shoes_shoes));
        subCategoryModels.add(new CategoryModel("sneakers", R.drawable.shoes_sneakers));

        categoryModel5.addSubCategoryList(subCategoryModels);


        //카테고리 리스트 만들기
        CategoryModel[] categoryModels = {  categoryModel1,
                                            categoryModel2,
                                            categoryModel3,
                                            categoryModel4,
                                            categoryModel5,
                                            };
        return categoryModels;
    }

}
