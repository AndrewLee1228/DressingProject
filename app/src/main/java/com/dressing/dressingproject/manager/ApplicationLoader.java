package com.dressing.dressingproject.manager;

import android.app.Application;
import android.content.Context;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CategoryModel;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 6.
 */
public class ApplicationLoader extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

    //Bottom Sheet 카테고리 리스트
    public static CategoryModel[] getCatagoryModels()
    {

        ArrayList<CategoryModel> subCategoryModels = new ArrayList<CategoryModel>();

        /*-------------catagory1---------------*/
        CategoryModel categoryModel1 = new CategoryModel("test1", R.mipmap.ic_launcher);

        subCategoryModels.add(new CategoryModel("sub test1", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test1", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test1", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test1", R.mipmap.ic_launcher));

        categoryModel1.addSubCategoryList(subCategoryModels);

        /*-------------catagory2---------------*/
        CategoryModel categoryModel2 = new CategoryModel("test2", R.mipmap.ic_launcher);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("sub test2", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test2", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test2", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test2", R.mipmap.ic_launcher));

        categoryModel2.addSubCategoryList(subCategoryModels);

        /*-------------catagory3---------------*/
        CategoryModel categoryModel3 = new CategoryModel("test3", R.mipmap.ic_launcher);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("sub test3", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test3", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test3", R.mipmap.ic_launcher));

        categoryModel3.addSubCategoryList(subCategoryModels);

        /*-------------catagory4---------------*/
        CategoryModel categoryModel4 = new CategoryModel("test4", R.mipmap.ic_launcher);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("sub test4", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test4", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test4", R.mipmap.ic_launcher));

        categoryModel4.addSubCategoryList(subCategoryModels);

        /*-------------catagory5---------------*/
        CategoryModel categoryModel5 = new CategoryModel("test5", R.mipmap.ic_launcher);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("sub test5", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test5", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test5", R.mipmap.ic_launcher));

        categoryModel5.addSubCategoryList(subCategoryModels);

        /*-------------catagory6---------------*/
        CategoryModel categoryModel6 = new CategoryModel("test6", R.mipmap.ic_launcher);

        subCategoryModels.clear();
        subCategoryModels.add(new CategoryModel("sub test6", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test6", R.mipmap.ic_launcher));
        subCategoryModels.add(new CategoryModel("sub test6", R.mipmap.ic_launcher));

        categoryModel6.addSubCategoryList(subCategoryModels);


        //카테고리 리스트 만들기
        CategoryModel[] categoryModels = {  categoryModel1,
                                            categoryModel2,
                                            categoryModel3,
                                            categoryModel4,
                                            categoryModel5,
                                            categoryModel6,
                                            };
        return categoryModels;
    }

}
