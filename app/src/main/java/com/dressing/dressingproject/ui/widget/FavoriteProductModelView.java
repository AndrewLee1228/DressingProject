package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.FavoriteProductAdapter;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Currency;
import java.util.Locale;

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
    private ProgressWheel mProgressWheel;
    private TextView mProductName;
    private TextView mProductPrice;
    private ImageView mCheckCircle;

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

    public void setProductItem(FavoriteProductAdapter favoriteProductAdapter, SparseBooleanArray checkedItems, int position, ProductModel item) {

        mItem = item;
        setChecked(item.isFit());
        checkedItems.put(position,item.isFit());
        favoriteProductAdapter.checkItems();
        mProductName.setText(item.getProductName());
        mProductPrice.setText(String.format("%s %,d", Currency.getInstance(Locale.KOREA).getSymbol(),Integer.parseInt(item.getProductPrice())));
        Glide.with(mContext)
                .load(item.getProductImgURL())
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
                .fitCenter()
//                .placeholder(android.R.drawable.progress_horizontal)
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .into(mCodiView);
    }


    private void init() {
        inflate(mContext, R.layout.item_favorite_product_view, this);
        mProgressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);
        mCodiView = (ImageView)findViewById(R.id.item_favorite_product_view_img);
        checkView = (ImageView)findViewById(R.id.item_favorite_product_check_img);
        mProductName = (TextView) findViewById(R.id.item_favorite_product_image_text);
        mProductPrice = (TextView) findViewById(R.id.item_favorite_product_price_text);
        mCheckCircle = (ImageView)findViewById(R.id.item_favorite_product_check_arrow_img);
    }

    private void drawCheck() {
        if (isChecked) {
            checkView.setVisibility(VISIBLE);
            mCheckCircle.setVisibility(VISIBLE);
        } else {
            checkView.setVisibility(INVISIBLE);
            mCheckCircle.setVisibility(INVISIBLE);
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
