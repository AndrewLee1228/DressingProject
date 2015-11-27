package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 18.
 */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int FITTING = 0;
    public static final int PRODUCT_SEARCH = 1;
    private Drawable mDivider;

    public SimpleDividerItemDecoration(Context context,int flag) {
        switch(flag)
        {
            case PRODUCT_SEARCH:
                mDivider = context.getResources().getDrawable(R.drawable.line_divider2);
                break;
            case FITTING:
            default:
                mDivider = context.getResources().getDrawable(R.drawable.line_divider);
                break;
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
