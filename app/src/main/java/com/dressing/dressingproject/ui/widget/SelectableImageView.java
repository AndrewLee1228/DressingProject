package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lee on 15. 11. 8.
 */
public class SelectableImageView extends ImageView {

    private static final int[] STATE_SELECTED = { android.R.attr.state_selected };

    private boolean mIsSelected = false;

    public SelectableImageView(Context context) {
        super(context);
    }

    public SelectableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (mIsSelected)
            mergeDrawableStates(drawableState, STATE_SELECTED);

        return drawableState;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (mIsSelected != selected) {
            mIsSelected = selected;
            invalidate();
            refreshDrawableState();
        }
    }

    public boolean getStateSelected() {
        return mIsSelected;
    }
}
