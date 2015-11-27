package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 21.
 */
public class MallModel {
    public int brandNum;
    public String brandName ="";
    public String brandImg ="";
    public boolean mallSelected =false;

    public boolean isSelected() {
        return mallSelected;
    }
    public void setSelected(boolean selected) {
        mallSelected =selected;
    }

}
