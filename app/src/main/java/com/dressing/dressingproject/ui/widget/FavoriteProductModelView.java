package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteProductModelView extends FrameLayout implements Checkable, View.OnClickListener {

    public OnItemClickListener onItemClickListener;
    private int mPosition;

    private final Context mContext;
    private ProductModel mItem;
    private ImageView mCodiView;
    private ImageView checkView;

    public FavoriteProductModelView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public interface OnItemClickListener{
        public void onItemClick(View view,ProductModel codiModel,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view)
    {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(view, mItem, getPosition());
        }
    }


    public void setPosition(int position)
    {
        mPosition = position;
    }

    public int getPosition()
    {
        return mPosition;
    }

    public void setProductItem(ProductModel item) {

        mItem = item;
//        favoriteImageView.setSelected(item.isFavorite());
        Glide.with(mContext)
                .load(Integer.parseInt(item.getProductImgURL()))
                .fitCenter()
//                .placeholder(android.R.drawable.progress_horizontal)
                .into(mCodiView);
    }


    private void init() {
        inflate(mContext, R.layout.item_favorite_product_view, this);
        mCodiView = (ImageView)findViewById(R.id.item_favorite_product_view_img);
        checkView = (ImageView)findViewById(R.id.item_favorite_product_check_img);
    }

    private void drawCheck() {
        if (isChecked) {
            checkView.setVisibility(VISIBLE);
        } else {
            checkView.setVisibility(INVISIBLE);
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
