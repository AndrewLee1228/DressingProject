package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by lee on 15. 11. 9.
 */
public class DetailProductView extends BaseDetialFrameLayout {

    public RectangleImageView codiView;
    private TextView recomendScoreText;
    private ImageView favoriteImageView;
    public FrameLayout frameLayout;

    private CodiModel mItem;
    private RelativeLayout mRecommendFrameLayout;

    public DetailProductView(Context context) {
        super(context);
        init();
    }

    DisplayImageOptions options;

    private void init() {
        inflate(getContext(), R.layout.item_detail_product_view, this);
        codiView = (RectangleImageView)findViewById(R.id.item_detail_product_view_img);
        codiView.setOnClickListener(this);

        mRecommendFrameLayout = (RelativeLayout)findViewById(R.id.item_recommend_view_root_layout);

        TextView recommendViewText = (TextView)findViewById(R.id.item_recommend_view_text);
        recommendViewText.setTextSize(10);
        recomendScoreText = (TextView)findViewById(R.id.item_recommend_view_score_text);
        recomendScoreText.setTextSize(24);

        frameLayout = (FrameLayout)findViewById(R.id.item_recommend_view_frame_layout);
        frameLayout.setOnClickListener(this);

        favoriteImageView = (ImageView)findViewById(R.id.item_detail_product_view_image_favorite);
        favoriteImageView.setOnClickListener(this);

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

    @Override
    public void onClick(View view)
    {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(view, mItem, getPosition());
        }
    }

    public void setCodiItem(CodiModel item) {

        mItem = item;
        CheckAndSetScore(item);
        favoriteImageView.setSelected(item.isFavorite());
        ImageLoader.getInstance().displayImage("drawable://"+Integer.parseInt(item.getImageURL()), codiView, options);

    }

    /**
     * float 정수로 치환하면서 소숫점 첫째 자리까지 표현함.
     * 유저스코어의 점수가 있다면 root layout을 selected 상태로 변경함.
     * @param codiModel
     */
    private void CheckAndSetScore(CodiModel codiModel) {
        float floastRating = Float.parseFloat(codiModel.getUserScore());

        if (codiModel.isRated() == true) {
            recomendScoreText.setText(String.format("%.1f",floastRating));
            mRecommendFrameLayout.setSelected(true);
        }
        else
        {
            recomendScoreText.setText(String.format("%.1f",floastRating));
            mRecommendFrameLayout.setSelected(false);
        }
    }
}
