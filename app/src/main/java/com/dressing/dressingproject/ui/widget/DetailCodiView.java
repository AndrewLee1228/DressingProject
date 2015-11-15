package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;

/**
 * Created by dongja94 on 2015-10-19.
 */
public class DetailCodiView extends FrameLayout {
    private final Context mContext;
    public ImageView productView;
    private TextView productName;
    private TextView productPrice;
    public ImageView productFavoriteView;

    public DetailCodiView(Context context,OnClickListener clickListener) {
        super(context);
        mContext = context;
        init(clickListener);
    }


    private void init(OnClickListener clickListener) {
        inflate(getContext(), R.layout.item_detail_codi_view, this);
        productView = (ImageView) findViewById(R.id.item_detail_codi_view_image);
        productView.setOnClickListener(clickListener);
        productName = (TextView) findViewById(R.id.item_detail_codi_view_image_text);
        productPrice = (TextView) findViewById(R.id.item_detail_codi_view_image_price_text);
        productFavoriteView = (ImageView) findViewById(R.id.item_detail_codi_view_image_favorite);
        productFavoriteView.setOnClickListener(clickListener);


    }

    public void setProductItem(ProductModel item) {
        productName.setText(item.getProdutcName());
        productPrice.setText(item.getProductPrice());
        productFavoriteView.setSelected(item.isFavorite());

        Glide.with(mContext)
                .load(Integer.parseInt(item.getProductImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .thumbnail(0.1f)
                .crossFade()
                .into(productView);

    }
}
