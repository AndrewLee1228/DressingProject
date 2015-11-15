package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 14.
 */
public class CheckItemView extends FrameLayout implements Checkable {

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }

    public CheckItemView(Context context) {
        super(context);
        init();
    }

    public CheckItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    ImageView checkView;

    private void init() {
        inflate(getContext(), R.layout.item_check_view, this);
        checkView = (ImageView)findViewById(R.id.item_check_view_image_check);
    }


    private void drawCheck() {
        if (isChecked) {
            checkView.setImageResource(R.drawable.ic_check_circle_black_48dp);
        } else {
            checkView.setImageResource(R.drawable.ic_check_circle_black_outline_48dp);
        }
    }

    boolean isChecked = false;

    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            drawCheck();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}

