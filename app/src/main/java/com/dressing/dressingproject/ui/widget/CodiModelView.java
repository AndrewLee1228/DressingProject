package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiModel;

/**
 * Created by lee on 15. 11. 9.
 */
public class CodiModelView extends BaseModelFrameLayout {

    private final Context mContext;
    public RectangleImageView codiView;
    private TextView recomendScoreText;
    private ImageView favoriteImageView;
    public FrameLayout frameLayout;

    private CodiModel mItem;
    private RelativeLayout mRecommendFrameLayout;
    private TextView mRecommendViewText;

    public CodiModelView(Context context) {
        super(context);
        mContext = context;
        init();
    }


    private void init() {
        inflate(mContext, R.layout.item_detail_product_view, this);
        codiView = (RectangleImageView)findViewById(R.id.item_detail_product_view_img);
        codiView.setOnClickListener(this);

        mRecommendFrameLayout = (RelativeLayout)findViewById(R.id.item_recommend_view_root_layout);

        mRecommendViewText = (TextView)findViewById(R.id.item_recommend_view_text);
        mRecommendViewText.setTextSize(10);
        recomendScoreText = (TextView)findViewById(R.id.item_recommend_view_score_text);
        recomendScoreText.setTextSize(24);

        frameLayout = (FrameLayout)findViewById(R.id.item_recommend_view_frame_layout);
        frameLayout.setOnClickListener(this);

        favoriteImageView = (ImageView)findViewById(R.id.item_detail_product_view_image_favorite);
        favoriteImageView.setOnClickListener(this);

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
        Glide.with(mContext)
                .load(Integer.parseInt(item.getImageURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .into(codiView);
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
            mRecommendViewText.setText(R.string.myscore);
        }
        else
        {
            recomendScoreText.setText(String.format("%.1f", floastRating));
            mRecommendFrameLayout.setSelected(false);
            mRecommendViewText.setText(R.string.estimationScore);
        }
    }
}