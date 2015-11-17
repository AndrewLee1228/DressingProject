package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 17.
 */
public class FitResult
{
    private boolean selectedState;

    public boolean isFit() {
        return selectedState;
    }

    public void setFit(boolean selectedState) {
        this.selectedState = selectedState;
    }
}
