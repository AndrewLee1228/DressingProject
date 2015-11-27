package com.dressing.dressingproject.ui.models;

/**
 * Created by Plumillon Forge on 25/09/15.
 */
public class Tag implements Chip {
    private String mName;
    private int mType = 0;


    private int code;

    private String img;

    private int brandNum;

    private boolean sync;

    public Tag(String mName, int code, String img, int brandNum, boolean sync) {
        this.mName = mName;
        this.code = code;
        this.img = img;
        this.brandNum = brandNum;
        this.sync = sync;
    }

    public Tag(String name, int type) {
        this(name);
        mType = type;

    }

    public Tag(String name) {
        mName = name;
    }

    @Override
    public String getText() {
        return mName;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getImgURL() {
        return img;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getBrandNum() {
        return brandNum;
    }

    @Override
    public boolean getSync() {
        return sync;
    }

    public int getType() {
        return mType;
    }
}
