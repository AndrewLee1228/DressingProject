package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by lee on 15. 11. 9.
 */
public class DetailProductView extends FrameLayout {

    public RectangleImageView codiView;

    public DetailProductView(Context context,OnClickListener clickListener) {
        super(context);
        init(clickListener);
    }

    DisplayImageOptions options;

    private void init(OnClickListener clickListener) {
        inflate(getContext(), R.layout.item_detail_product_view, this);
        codiView = (RectangleImageView)findViewById(R.id.item_detail_product_view_img);
        codiView.setOnClickListener(clickListener);

        TextView recommendViewText = (TextView)findViewById(R.id.item_recommend_view_text);
        recommendViewText.setTextSize(10);
        TextView recomendScoreText = (TextView)findViewById(R.id.item_recommend_view_score_text);
        recomendScoreText.setTextSize(24);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.item_recommend_view_frame_layout);
        frameLayout.setOnClickListener(clickListener);

        ImageView favoriteImageView = (ImageView)findViewById(R.id.item_detail_product_view_image_favorite);
        favoriteImageView.setOnClickListener(clickListener);

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

    public void setCodiItem(CodiModel item) {


        ImageLoader.getInstance().displayImage("drawable://"+Integer.parseInt(item.getImageURL()), codiView, options);
    }
}
