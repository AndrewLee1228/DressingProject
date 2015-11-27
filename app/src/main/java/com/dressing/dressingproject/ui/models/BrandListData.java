package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 25.
 */
public class BrandListData
{
    private int code;

    private String img;

    private String name;

    private int brandNum;

    private boolean sync;

    public BrandListData(int code, String imgURL, String name,int brandNum, boolean sync )
    {
        this.code = code;
        this.img = imgURL;
        this.name = name;
        this.brandNum = brandNum;
        this.sync = sync;
    }

    public int getCode()
    {
        return code;
    }

    public String getImg()
    {
        return img;
    }

    public String getName()
    {
        return name;
    }

    public int getBrandNum() {
        return brandNum;
    }

    public boolean isSync()
    {
        return sync;
    }

}
