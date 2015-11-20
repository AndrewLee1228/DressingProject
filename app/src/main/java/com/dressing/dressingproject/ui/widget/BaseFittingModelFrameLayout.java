package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;

import com.dressing.dressingproject.ui.models.ProductModel;

/**
 * Created by lee on 15. 11. 15.
 */
public class BaseFittingModelFrameLayout extends com.daimajia.swipe.SwipeLayout implements View.OnClickListener{

public OnItemClickListener onItemClickListener;
private int mPosition;

public BaseFittingModelFrameLayout(Context context) {
        super(context);
        }

public interface OnItemClickListener{
    public void onItemClick(View view, ProductModel productModel, int position);
}

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {

    }

    public void setPosition(int position)
    {
        mPosition = position;
    }

    public int getPosition()
    {
        return mPosition;
    }
}
