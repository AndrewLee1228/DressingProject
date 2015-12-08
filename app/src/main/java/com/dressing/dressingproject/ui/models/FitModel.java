package com.dressing.dressingproject.ui.models;

import java.io.Serializable;

/**
 * Created by lee on 15. 11. 30.
 */
public class FitModel implements Serializable
{
    public static final int FIT_CODI = 0;
    public static final int FIT_PRODUCT = 1;
    public static final int FIT_FITTING = 2
            ;
    public int fittingNum;
    public int itemNum;
    public String itemName;
    public int itemPrice;
    public String brandName;
    public String productName;
    public String mallName;
    public String shopName;
    public String itemImg;
    public String brandImg;
    public String shoppositionImg;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag)
    {

        this.flag = flag;

    }

}




