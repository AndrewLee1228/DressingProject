package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.FavoriteCodiAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteCodiModelView extends BaseModelFrameLayout implements Checkable {

    private final Context mContext;
    private CodiModel mItem;
    private RectangleImageView mCodiView;
    private ImageView checkView;
    private ProgressWheel mProgressWheel;
    private ImageView checkArrow;

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

    public void setCodiItem(FavoriteCodiAdapter favoriteCodiAdapter, SparseBooleanArray checkedItems, int position, CodiModel item) {

        mItem = item;
        setChecked(item.isFit());
        checkedItems.put(position,item.isFit());
        favoriteCodiAdapter.checkItems();
        Glide.with(mContext)
                .load(item.getImageURL())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        mProgressWheel.setVisibility(GONE);
                        return false;
                    }
                })
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .into(mCodiView);
    }


    private void init() {
        inflate(mContext, R.layout.item_favorite_codi_view, this);
        mProgressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);
        mCodiView = (RectangleImageView)findViewById(R.id.item_favorite_codi_view_img);
        checkView = (ImageView)findViewById(R.id.item_store_location_check_img);
        checkArrow = (ImageView) findViewById(R.id.item_store_location_check_arrow_img);
    }

    private void drawCheck() {
        if (isChecked) {
            checkView.setImageResource(R.drawable.ic_codi_favorite_selected);
            checkArrow.setVisibility(VISIBLE);
        } else {
            checkView.setImageBitmap(null);
            checkArrow.setVisibility(GONE);
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
