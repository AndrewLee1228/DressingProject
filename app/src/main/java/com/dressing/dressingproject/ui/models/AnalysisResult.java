package com.dressing.dressingproject.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 19.
 */
public class AnalysisResult
{
    public int code;
    public String msg;
    @SerializedName("analysisData")
    public ArrayList<AnalysisData> list;
}
