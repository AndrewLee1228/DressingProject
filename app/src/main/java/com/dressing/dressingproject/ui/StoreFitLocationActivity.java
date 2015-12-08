package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.StoreTitleFitAdapter;
import com.dressing.dressingproject.ui.models.FitModel;
import com.dressing.dressingproject.ui.models.FittingListResult;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.dressing.dressingproject.util.FontManager;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by lee on 15. 12. 3.
 */
public class StoreFitLocationActivity extends AppCompatActivity {

    ViewPager mPager;
    private StoreTitleFitAdapter mStoreTitleAdapter;
    private Integer mPosition;
    private ProgressWheel mProgressWheel;
    private ImageView mProductImg;
    private TextView mPriceText;
    private TextView mNameText;
    private TextView mNumText;
    private TextView mLocationText;
    private ImageView mLogoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_location);


        InitLayout();
        InitPager();

        Intent intent = getIntent();

        mPosition = (Integer) intent.getExtras().get("position");
        FitModel fitModel = (FitModel) intent.getSerializableExtra("fitModel");


        NetworkManager.getInstance().requestGetFitting(this,0,mPosition+3 ,new NetworkManager.OnResultListener<FittingListResult>() {
            @Override
            public void onSuccess(FittingListResult result) {
                if (result.code == 200) {
                    int size = result.list.size();
                    if(size >0 )
                    {
                        mStoreTitleAdapter.addList(result.list);
                        mPager.setCurrentItem(mPosition);
                    }
                    mProgressWheel.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFail(int code) {

            }
        });

//        ArrayList<ProductModel> ls = new ArrayList<ProductModel>();
//        for(FitModel fitModel : list)
//        {
//            ProductModel productModel = new ProductModel();
//            productModel.setMallName(fitModel.mallName);
//            productModel.setMapURL(fitModel.shoppositionImg);
//            productModel.setProdutcTitleName(fitModel.itemName);
//            productModel.setProductPrice(String.valueOf(fitModel.itemPrice));
//            productModel.setProductNum(String.valueOf(fitModel.itemNum));
//            productModel.setProductLogoImgURL(fitModel.brandImg);
//            ls.add(productModel);
//        }
//
//        lists.addAll(ls);




    }

    private void InitLayout() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_location_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.main_blue));
        setSupportActionBar(toolbar);
        setActionBarTitle(getString(R.string.activity_location_title_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_blue);

        mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);

        mProductImg =(ImageView) findViewById(R.id.item_store_map_img);

        mNameText =(TextView) findViewById(R.id.item_store_map_name_text);
//        mNameText.setSelected(true); //글자흐름 마퀴를 적용하기 위해
        mPriceText =(TextView)findViewById(R.id.item_store_map_price_text);
        mNumText =(TextView)findViewById(R.id.item_store_map_num_text);
        mLocationText=(TextView)findViewById(R.id.item_store_map_location_text);

        mLogoImg =(ImageView)findViewById(R.id.item_store_map_logo);

    }

    //액션바 타이틀 세팅
    public void setActionBarTitle(String title)
    {
        //액션바에 폰트적용
        SpannableString s = new SpannableString(title);
        s.setSpan(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO), Typeface.BOLD, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
    }

    private void InitPager() {
        mPager = (ViewPager)findViewById(R.id.activity_location_viewpager);
        mPager.setClipToPadding(false);
        mPager.setPageMargin(AndroidUtilities.dp(10));
        mStoreTitleAdapter = new StoreTitleFitAdapter(this);
        mPager.setAdapter(mStoreTitleAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //updateFilter.makeText(MainActivity.this, "selected : " + position, updateFilter.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * viewpager에서 넘어온 아이템 정보를 뷰에 세팅!
     * @param fitModel
     */
    public void setInfoItem(FitModel fitModel) {



        mProductImg.setImageBitmap(null);
        mLogoImg.setImageBitmap(null);

        mNameText.setText(fitModel.itemName);
        mNameText.setTextColor(getResources().getColor(R.color.black_gray));
        mPriceText.setText(String.format("가격 : %s %,d", Currency.getInstance(Locale.KOREA).getSymbol(), fitModel.itemPrice));
        mPriceText.setTextColor(getResources().getColor(R.color.black_gray));
        mNumText.setText(String.format("제품번호 : %s",fitModel.itemNum));
        mNumText.setTextColor(getResources().getColor(R.color.black_gray));
        mLocationText.setText(String.format("위치 : %s",fitModel.mallName));
        mLocationText.setTextColor(getResources().getColor(R.color.black_gray));

        //상품이미지 로드
        Glide.with(this)
                .load(fitModel.itemImg)
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .override(200, 200)
                .diskCacheStrategy (DiskCacheStrategy.RESULT)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                        mProgressWheel.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mProductImg);

//상품로고 이미지 로드
        Glide.with(this)
                .load(fitModel.brandImg)
//                .load(Integer.parseInt(item.getProductLogoImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                .crossFade()
                .thumbnail(0.1f)
                .override(400, 400)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mLogoImg);

    }
}

