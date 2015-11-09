package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailProductAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.ProductItems;
import com.dressing.dressingproject.ui.widget.HeaderView;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public class DetailProductActivity extends AppCompatActivity implements  DetailProductAdapter.OnItemClickListener,AppBarLayout.OnOffsetChangedListener , RatingBar.OnRatingBarChangeListener{

    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingLayout;
    private Toolbar mToolbar;
    private RatingBar mRatingBar;
    private AlertDialog mAlertDialog;
    private ImageView mProductImageView;
    private HeaderView mToolbarHeader;
    private HeaderView mFloatHeader;
    private RecyclerView mRecyclerView;
    private DetailProductAdapter mDetailProductAdapter;
    private String mTitle;
    private int mTitleColor;
    private boolean mIsHideToolbarView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        InitLayout();

        InitValue(getIntent());
    }

    private void InitLayout() {
        mAppBarLayout = (AppBarLayout)findViewById(R.id.activity_detail_product_app_bar_layout);
        mAppBarLayout.addOnOffsetChangedListener(this);

        mCollapsingLayout = (CollapsingToolbarLayout)findViewById(R.id.activity_detail_product_collapsing_layout);

        //툴바
        mToolbar = (Toolbar) findViewById(R.id.activity_detail_product_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Score Dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(null);
        View customDialogView = inflater.inflate(R.layout.item_score_dialog, null, false);
        mRatingBar = (RatingBar) customDialogView.findViewById(R.id.ratingBar);
        mRatingBar.setOnRatingBarChangeListener(this);
        builder.setView(customDialogView);
        mAlertDialog = builder.create();

        //이미지뷰
        mProductImageView = (ImageView)findViewById(R.id.activity_detail_product_image);

        //헤더뷰
        mToolbarHeader = (HeaderView) findViewById(R.id.activity_detail_product_toolbar_header_view);
        mFloatHeader = (HeaderView) findViewById(R.id.activity_detail_product_float_header_view);

        //리싸이클러
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_detail_product_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mDetailProductAdapter = new DetailProductAdapter();
        mDetailProductAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mDetailProductAdapter);

        //그리드 레이아웃 Span 개수 Controll
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return mDetailProductAdapter.isPositionHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }

        });


    }

    //툴바 색상 세팅
    private void ChangeToolbarColor(int color, float percentage) {
        final int[] toolbarColours = {
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        };

        if(percentage == 0)
        {
            mToolbar.setBackgroundColor(color);
            setSupportActionBar(mToolbar);
        }
        //알파값
        else
        {
            mToolbar.setBackgroundColor(Color.argb((int) (130f * percentage), toolbarColours[0], toolbarColours[1], toolbarColours[2]));
            setSupportActionBar(mToolbar);
        }

    }

    private void InitValue(Intent intent) {

        String productName ="";
        String productMapURL ="";
        String productBrandName ="";
        String productImgURL ="";
        String productPrice ="";
        String productNum ="";
        boolean isFavorite= false;

        String productTitle ="";

        if (intent != null) {
            productTitle = intent.getExtras().getString("ProductTitle");
            productName = intent.getExtras().getString("ProdutcName");
            productMapURL = intent.getExtras().getString("MapURL");
            productBrandName = intent.getExtras().getString("ProductBrandName");
            productImgURL = intent.getExtras().getString("ProductImgURL");
            productPrice = intent.getExtras().getString("ProductPrice");
            productNum = intent.getExtras().getString("ProductNum");
            isFavorite = intent.getExtras().getBoolean("Favorite");
        }

        //앱바 타이틀
        mTitle = productTitle;
        // bindTo(String title, String subTitle)
        mFloatHeader.bindTo(mTitle, "");

        //앱바 이미지
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {

                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();

                                Palette.Swatch backgroundAndContentColors = (darkVibrantSwatch != null)
                                        ? darkVibrantSwatch : darkMutedSwatch;

                                mTitleColor = backgroundAndContentColors.getRgb();
                            }
                        });

                        return bitmap;
                    }
                })
                .considerExifParams(true)
                .build();

        ImageLoader.getInstance().displayImage("drawable://" + productImgURL, mProductImageView, options);

        //header text
        mDetailProductAdapter.setHeader(productName,productPrice,productBrandName,productNum,isFavorite);

        //개별상품로딩
        NetworkManager.getInstance().getNetworkDetailProduct(this, new NetworkManager.OnResultListener<ProductItems>() {

            @Override
            public void onSuccess(ProductItems result) {
                for (CodiModel item : result.items) {
                    mDetailProductAdapter.add(item);
                }
            }

            @Override
            public void onFail(int code) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().cancelAll(this);
    }

    /**
     * AppBarLayout 컨트롤
     * @param appBarLayout
     * @param offset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        Log.i("percentage : ", Float.toString(percentage));

        //AppBarLayout 영역 최소일 경우
        if (percentage == 1f && mIsHideToolbarView) {
            mToolbarHeader.setVisibility(View.GONE);
            mFloatHeader.setVisibility(View.GONE);
            mIsHideToolbarView = !mIsHideToolbarView;
            mCollapsingLayout.setTitle(mTitle);
            ChangeToolbarColor(mTitleColor,percentage);
            //AppBarLayout 영역 최대일 경우
        } else if (percentage < 1f && !mIsHideToolbarView) {
            mToolbarHeader.setVisibility(View.GONE );
            mFloatHeader.setVisibility(View.VISIBLE);
            mIsHideToolbarView = !mIsHideToolbarView;
            mCollapsingLayout.setTitle(" ");
            ChangeToolbarColor(Color.TRANSPARENT,0);
        }
    }


    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }

    @Override
    public void onItemClick(View view, CodiModel item) {
        switch (view.getId())
        {
//            //item codi click
//            case R.id.item_detail_product_view_image:
//                Intent intent = new Intent(this,DetailProductActivity.class);
//                intent.putExtra("ProdutcName",item.getProdutcName());
//                intent.putExtra("MapURL",item.getMapURL());
//                intent.putExtra("ProductBrandName",item.getProductBrandName());
//                intent.putExtra("ProductImgURL",item.getProductImgURL());
//                intent.putExtra("ProductPrice",item.getProductPrice());
//                startActivity(intent);
//                break;
            //item favorite click
            case R.id.item_detail_product_view_image_favorite:
            case R.id.item_detail_product_headerview_favorite:
                if(view.isSelected() == false){
                    view.setSelected(true);
                    AndroidUtilities.MakeFavoriteToast(getApplicationContext());
                }
                else{
                    view.setSelected(false);
                }
                break;

            case R.id.item_recommend_view_frame_layout:
                mAlertDialog.show();
                break;
            case R.id.item_detail_product_view_img:
                Intent intent = new Intent(this,DetailCodiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }
}
