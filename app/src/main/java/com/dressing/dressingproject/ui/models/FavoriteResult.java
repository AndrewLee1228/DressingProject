package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 12.
 */
public class FavoriteResult
{
    private boolean selectedState;
    public int code;
    public String msg;

    public boolean getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(boolean selectedState) {
        this.selectedState = selectedState;
    }
}
