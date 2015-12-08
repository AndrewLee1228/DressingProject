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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.StoreTitleAdapter;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductSearchResult;
import com.dressing.dressingproject.ui.models.SearchItem;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.dressing.dressingproject.util.FontManager;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.Currency;
import java.util.Locale;

public class StoreLocationActivity extends AppCompatActivity {

    ViewPager mPager;
    private StoreTitleAdapter mStoreTitleAdapter;
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
        Boolean isFit = (Boolean) intent.getExtras().get("isFit");
        ProductModel productModel = (ProductModel) intent.getSerializableExtra("productModel");


        //네트워크 데이터요청

            SearchItem searchItem = new SearchItem();
            searchItem.brandNum = "";
            searchItem.start = 0;
            searchItem.display = mPosition +3;
            NetworkManager.getInstance().requestGetSearchProduct(this, searchItem ,new NetworkManager.OnResultListener<ProductSearchResult>() {

                @Override
                public void onSuccess(ProductSearchResult result)
                {

                    if(result.code == 200 && result.msg.equals("Success"))
                    {
                        mStoreTitleAdapter.addList(result.list);
                        mPager.setCurrentItem(mPosition);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "상품 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFail(int code) {

                }

            });



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
        mStoreTitleAdapter = new StoreTitleAdapter(this);
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
     * @param productModel
     */
    public void setInfoItem(ProductModel productModel) {



        mProductImg.setImageBitmap(null);
        mLogoImg.setImageBitmap(null);

        mNameText.setText(productModel.getProductName());
        mNameText.setTextColor(getResources().getColor(R.color.black_gray));
        mPriceText.setText(String.format("가격 : %s %,d", Currency.getInstance(Locale.KOREA).getSymbol(), Integer.parseInt(productModel.getProductPrice())));
        mPriceText.setTextColor(getResources().getColor(R.color.black_gray));
        mNumText.setText(String.format("제품번호 : %s",productModel.getProductNum()));
        mNumText.setTextColor(getResources().getColor(R.color.black_gray));
        mLocationText.setText(String.format("위치 : %s",productModel.getMallName()));
        mLocationText.setTextColor(getResources().getColor(R.color.black_gray));

        //상품이미지 로드
        Glide.with(this)
        .load(productModel.getProductImgURL())
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
        .load(productModel.getProductLogoImgURL())
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
