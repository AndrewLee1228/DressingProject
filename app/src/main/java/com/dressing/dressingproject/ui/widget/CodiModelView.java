package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.pnikosis.materialishprogress.ProgressWheel;

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
    private ProgressWheel mProgressWheel;

    public CodiModelView(Context context) {
        super(context);
        mContext = context;
        init();
    }


    private void init() {
        inflate(mContext, R.layout.item_detail_product_view, this);
        mProgressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);
        codiView = (RectangleImageView)findViewById(R.id.item_detail_product_view_img);
        codiView.setOnClickListener(this);

        mRecommendFrameLayout = (RelativeLayout)findViewById(R.id.item_recommend_view_root_layout);

        mRecommendViewText = (TextView)findViewById(R.id.item_recommend_view_text);
        mRecommendViewText.setTextSize(8);
        recomendScoreText = (TextView)findViewById(R.id.item_recommend_view_score_text);
        recomendScoreText.setTextSize(20);

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
                .load(item.getImageURL())
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .crossFade()
                .thumbnail(0.1f)
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
