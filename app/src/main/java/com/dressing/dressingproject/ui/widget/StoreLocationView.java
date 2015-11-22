package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.MallModel;

/**
 * Created by lee on 15. 11. 20.
 */
public class StoreLocationView extends FrameLayout implements View.OnClickListener,Checkable{
    private final Context mContext;
    private MallModel mItem;
    private ImageView mLocationImg;
    private TextView mLocationText;
    private ImageView mCheckView;

    public OnItemClickListener onItemClickListener;
    private int mPosition;

    public interface OnItemClickListener{
        public void onItemClick(View view,MallModel mallModel,int position);
    }

    public StoreLocationView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.item_store_location, this);

        mLocationImg =(ImageView) findViewById(R.id.item_store_location_bg_img);
        mLocationText =(TextView) findViewById(R.id.item_store_location_text);
        mCheckView = (ImageView)findViewById(R.id.item_store_location_check_img);
        this.setOnClickListener(this);
    }

    public void setItem(MallModel item) {
        mItem = item;
        mLocationText.setText(item.mallName);
//        mNameText.setText(item.getProductName());
//        /**
//         * java에서 원화 표시하기
//         * Currency.getInstance(Locale.KOREA).getSymbol()
//         * 여기서 Locale 설정을 바꾸면 해당 나라의 통화 심볼을 얻을 수 있습니다.
//         */
//        mPriceText.setText(String.format("가격 : %s %,d", Currency.getInstance(Locale.KOREA).getSymbol(), Integer.parseInt(item.getProductPrice())));
//        mNumText.setText(String.format("제품번호 : %s",item.getProductNum()));
//        mLocationText.setText(String.format("위치 : %s",item.getProductLocation()));
//        mFavoriteImg.setSelected(item.isFavorite());
//
//        //상품이미지 로드
//        Glide.with(mContext)
//                .load(Integer.parseInt(item.getProductImgURL()))
////                .centerCrop()
////                .placeholder(android.R.drawable.progress_horizontal)
//                .crossFade()
//                .thumbnail(0.1f)
//                .into(mProductImg);
//
//        //상품로고 이미지 로드
//        Glide.with(mContext)
//                .load(R.mipmap.ic_launcher)
////                .load(Integer.parseInt(item.getProductLogoImgURL()))
////                .centerCrop()
////                .placeholder(android.R.drawable.progress_horizontal)
//                .crossFade()
//                .thumbnail(0.1f)
//                .into(mLogoImg);
    }


    @Override
    public void onClick(View view)
    {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClick(view, mItem, getPosition());
        }
    }

    boolean isChecked = false;

    private void drawCheck() {
        if (isChecked) {
            mCheckView.setVisibility(View.VISIBLE);
        } else {
            mCheckView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            drawCheck();
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public void setPosition(int position)
    {
        mPosition = position;
    }

    public int getPosition()
    {
        return mPosition;
    }
}