package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.FitModel;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductFitView extends BaseSearchModelFrameLayout{
    private final Context mContext;
    private FitModel mItem;
    private ImageView mProductImg;
    private TextView mNameText;
    private TextView mPriceText;
    private TextView mNumText;
    private TextView mLocationText;
    private ImageView mFavoriteImg;
    private ImageView mMapImg;
    private ImageView mLogoImg;
    private ProgressWheel mProgressWheel;

    public ProductFitView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.item_search_product, this);

        mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);

         mProductImg =(ImageView) findViewById(R.id.item_search_product_img);

        mNameText =(TextView) findViewById(R.id.item_search_product_name_text);
//        mNameText.setSelected(true); //글자흐름 마퀴를 적용하기 위해
        mPriceText =(TextView)findViewById(R.id.item_search_product_price_text);
        mNumText =(TextView)findViewById(R.id.item_search_product_num_text);
        mLocationText=(TextView)findViewById(R.id.item_search_product_location_text);

        mMapImg =(ImageView)findViewById(R.id.item_search_product_map);
        mMapImg.setOnClickListener(this);
        mLogoImg =(ImageView)findViewById(R.id.item_search_product_logo);
    }

    public void setFavoriteVisible(int visible)
    {
        mFavoriteImg.setVisibility(VISIBLE);
    }

    //상품아이템 세팅
    public void setProductItem(FitModel item) {
        mItem = item;
        mNameText.setText(item.itemName);
        /**
         * java에서 원화 표시하기
         * Currency.getInstance(Locale.KOREA).getSymbol()
         * 여기서 Locale 설정을 바꾸면 해당 나라의 통화 심볼을 얻을 수 있습니다.
         */
        mPriceText.setText(String.format("가격 : %s %,d", Currency.getInstance(Locale.KOREA).getSymbol(), item.itemPrice));
        mNumText.setText(String.format("제품번호 : %s",item.productName));
        mLocationText.setText(String.format("위치 : %s",item.mallName));

        //상품이미지 로드
        Glide.with(mContext)
                .load(item.itemImg)
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .override(400, 400)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
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
                .into(mProductImg);

        //상품로고 이미지 로드
        Glide.with(mContext)
                .load(item.brandImg)
//                .load(Integer.parseInt(item.getProductLogoImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .override(400, 400)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mLogoImg);
    }


    @Override
    public void onClick(View view)
    {
        if (this.onItemClickListener != null) {
//            this.onItemClickListener.onItemClick(view, mItem, getPosition());
        }
    }

}
