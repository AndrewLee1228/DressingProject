package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 9.
 */
public class DetailProductHeaderView extends BaseModelFrameLayout {

    private TextView mName;
    private TextView mPrice;
    private TextView mBrand;
    private TextView mProuctNum;
    private SquareImageView mItemHeaderFavorite;

    public DetailProductHeaderView(Context context) {
        super(context);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.item_detail_product_headerview, this);
        mName = (TextView)findViewById(R.id.item_detail_product_headerview_productname_text);
        mPrice = (TextView)findViewById(R.id.item_detail_product_headerview_productprice_text);
        mBrand = (TextView)findViewById(R.id.item_detail_product_headerview_productbrand_text);
        mProuctNum = (TextView)findViewById(R.id.item_detail_product_headerview_productnum_text);

        mItemHeaderFavorite = (SquareImageView)findViewById(R.id.item_detail_product_headerview_favorite);
        mItemHeaderFavorite.setOnClickListener(this);
    }

    public void setInfo(String name,String price,String brand,String productNum,boolean isFavorite)
    {
        mName.setText(String.format("제품명 : %s",name));
        mPrice.setText(String.format("가격 : \\ %s",price));
        mBrand.setText(String.format("브랜드 : %s",brand));
        mProuctNum.setText(String.format("제품번호 : %s",productNum));

        mItemHeaderFavorite.setSelected(isFavorite);
    }

    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }
}
