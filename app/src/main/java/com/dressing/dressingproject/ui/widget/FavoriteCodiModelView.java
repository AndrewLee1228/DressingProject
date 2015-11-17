package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiModel;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteCodiModelView extends BaseModelFrameLayout implements Checkable {

    private final Context mContext;
    private CodiModel mItem;
    private RectangleImageView mCodiView;
    private ImageView checkView;

    public FavoriteCodiModelView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    @Override
    public void onClick(View view)
    {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(view, mItem, getPosition());
        }
    }

    public void setCodiItem(CodiModel item) {

        mItem = item;
        setChecked(item.isFit());
//        favoriteImageView.setSelected(item.isFavorite());
        Glide.with(mContext)
                .load(Integer.parseInt(item.getImageURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .into(mCodiView);
    }


    private void init() {
        inflate(mContext, R.layout.item_favorite_codi_view, this);
        mCodiView = (RectangleImageView)findViewById(R.id.item_favorite_codi_view_img);
        checkView = (ImageView)findViewById(R.id.item_favorite_codi_check_img);
    }

    private void drawCheck() {
        if (isChecked) {
            checkView.setImageResource(R.drawable.ic_favorite);
        } else {
            checkView.setImageBitmap(null);
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
