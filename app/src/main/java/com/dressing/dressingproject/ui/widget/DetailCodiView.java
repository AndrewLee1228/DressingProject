package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by dongja94 on 2015-10-19.
 */
public class DetailCodiView extends FrameLayout {
    public ImageView productView;
    private TextView productName;
    private TextView productPrice;
    public ImageView productFavoriteView;

    public DetailCodiView(Context context,OnClickListener clickListener) {
        super(context);
        init(clickListener);
    }


    DisplayImageOptions options;

    private void init(OnClickListener clickListener) {
        inflate(getContext(), R.layout.item_detail_codi_view, this);
        productView = (ImageView) findViewById(R.id.item_detail_codi_view_image);
        productView.setOnClickListener(clickListener);
        productName = (TextView) findViewById(R.id.item_detail_codi_view_image_text);
        productPrice = (TextView) findViewById(R.id.item_detail_codi_view_image_price_text);
        productFavoriteView = (ImageView) findViewById(R.id.item_detail_codi_view_image_favorite);
        productFavoriteView.setOnClickListener(clickListener);

        BitmapFactory.Options resizeOptions = new BitmapFactory.Options();


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .build();

    }

    public void setProductItem(ProductModel item) {
        productName.setText(item.getProdutcName());
        productPrice.setText(item.getProductPrice());
        productFavoriteView.setSelected(item.isFavorite());

        ImageLoader.getInstance().displayImage("drawable://"+Integer.parseInt(item.getProductImgURL()), productView, options);
    }
}
