package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 21.
 */
public class MallModel {
    public int mallNum;
    public String mallName ="";
    public String mallImg ="";
    public boolean mallSelected =false;

    public boolean isSelected() {
        return mallSelected;
    }
    public void setSelected(boolean selected) {
        mallSelected =selected;
    }

}
