package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by dongja94 on 2015-10-19.
 */
public class DetailCodiView extends FrameLayout {
    private final Context mContext;
    public ImageView productView;
    private TextView productName;
    private TextView productPrice;
    public ImageView productFavoriteView;
    private ProgressWheel progressWheel;

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
        progressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);


    }

    public void setProductItem(ProductModel item) {
        productName.setText(item.getProductName());
        productPrice.setText(String.format("%s %s", Currency.getInstance(Locale.KOREA).getSymbol(),item.getProductPrice()));
        productFavoriteView.setSelected(item.isFavorite());
        Glide.with(mContext)
                .load(item.getProductImgURL())
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
//                .thumbnail(0.1f)
                .crossFade()
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        progressWheel.setVisibility(GONE);
                        return false;
                    }
                })
                .into(productView);

    }
}
