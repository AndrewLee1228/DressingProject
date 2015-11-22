package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductFIttingView extends BaseFittingModelFrameLayout{
    private final Context mContext;
    private ProductModel mItem;
    private ImageView mProductImg;
    private TextView mNameText;
    private TextView mPriceText;
    private TextView mNumText;
    private TextView mLocationText;
    private ImageView mMapImg;
    private ImageView mLogoImg;
    public SwipeLayout mSwipeLayout;
    public TextView mTvDelete;

    public ProductFIttingView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.item_fitting_product, this);

         mProductImg =(ImageView) findViewById(R.id.item_fitting_product_img);

        mNameText =(TextView) findViewById(R.id.item_fitting_product_name_text);
        mPriceText =(TextView)findViewById(R.id.item_fitting_product_price_text);
        mNumText =(TextView)findViewById(R.id.item_fitting_product_num_text);
        mLocationText=(TextView)findViewById(R.id.item_fitting_product_location_text);

        mMapImg =(ImageView)findViewById(R.id.item_fitting_product_map);
        mMapImg.setOnClickListener(this);
        mLogoImg =(ImageView)findViewById(R.id.item_fitting_product_logo);


        mSwipeLayout = (SwipeLayout)findViewById(R.id.item_fitting_product_swipe);
        mTvDelete = (TextView) findViewById(R.id.item_fitting_product_tvDelete);


    }


    public void setProductItem(ProductModel item) {
        mItem = item;
        mNameText.setText(item.getProductName());
        /**
         * java에서 원화 표시하기
         * Currency.getInstance(Locale.KOREA).getSymbol()
         * 여기서 Locale 설정을 바꾸면 해당 나라의 통화 심볼을 얻을 수 있습니다.
         */
        mPriceText.setText(String.format("가격 : %s %s", Currency.getInstance(Locale.KOREA).getSymbol(), item.getProductPrice()));
        mNumText.setText(String.format("제품번호 : %s",item.getProductNum()));
        mLocationText.setText(String.format("위치 : %s",item.getMallName()));

        //상품이미지 로드
        Glide.with(mContext)
                .load(Integer.parseInt(item.getProductImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .into(mProductImg);

        //상품로고 이미지 로드
        Glide.with(mContext)
                .load(R.mipmap.ic_launcher)
//                .load(Integer.parseInt(item.getProductLogoImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .into(mLogoImg);
    }


    @Override
    public void onClick(View view)
    {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(view, mItem, getPosition());
        }
    }

}
