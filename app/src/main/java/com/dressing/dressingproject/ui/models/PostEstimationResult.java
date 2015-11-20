package com.dressing.dressingproject.ui.models;

/**
 * Created by lee on 15. 11. 11.
 */
public class PostEstimationResult {

    public int code;
    public String msg;

    float rating;

    public PostEstimationResult() {
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}
