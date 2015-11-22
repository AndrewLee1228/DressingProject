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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailProductAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiResult;
import com.dressing.dressingproject.ui.models.FavoriteResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.ui.widget.HeaderView;
import com.dressing.dressingproject.util.AndroidUtilities;

public class DetailProductActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

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
    private ProductModel mProductModel;


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
        mDetailProductAdapter.setOnAdapterItemListener(new DetailProductAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(DetailProductAdapter adapter,final View view, CodiModel codiModel,int position) {
                switch (view.getId())
                {

                    //item favorite click
                    //코디
                    case R.id.item_detail_product_view_image_favorite:
                        //찜하기 해제
                        if(codiModel.isFavorite())
                        {

                            NetworkManager.getInstance().requestDeleteFavorite(getApplicationContext(), null, codiModel, new NetworkManager.OnResultListener<SucessResult>() {

                                @Override
                                public void onSuccess(SucessResult sucessResult) {
                                    //찜하기 해제 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(false);
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        //찜하기
                        else
                        {
                            NetworkManager.getInstance().requestPostCodiFavorite(getApplicationContext(), codiModel, new NetworkManager.OnResultListener<FavoriteResult>() {

                                @Override
                                public void onSuccess(FavoriteResult favoriteResult) {
                                    //찜하기 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(true);
                                    AndroidUtilities.MakeFavoriteToast(getApplicationContext());
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        break;
                    //상품
                    case R.id.item_detail_product_headerview_favorite:
                        //찜하기 해제
                        if(mProductModel.isFavorite())
                        {

                            NetworkManager.getInstance().requestDeleteFavorite(getApplicationContext(), mProductModel,null, new NetworkManager.OnResultListener<SucessResult>() {

                                @Override
                                public void onSuccess(SucessResult sucessResult) {
                                    //찜하기 해제 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(false);
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        //찜하기
                        else
                        {
                            NetworkManager.getInstance().requestPostProductFavorite(getApplicationContext(), mProductModel, new NetworkManager.OnResultListener<FavoriteResult>() {

                                @Override
                                public void onSuccess(FavoriteResult favoriteResult) {
                                    //찜하기 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(true);
                                    AndroidUtilities.MakeFavoriteToast(getApplicationContext());
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        break;

                    case R.id.item_recommend_view_frame_layout:
                        ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.newInstance(Float.parseFloat(codiModel.getUserScore()));
                        scoreDialogFragment.setData(codiModel,adapter,position);
                        scoreDialogFragment.show(getSupportFragmentManager(), "dialog");
                        break;
                    case R.id.item_detail_product_view_img:
                        Intent intent = new Intent(getBaseContext(),DetailCodiActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                }
            }
        });
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

        if (intent != null) {
            mProductModel = (ProductModel) intent.getExtras().get("ProductModel");
        }

        //앱바 타이틀
        mTitle = mProductModel.getProductName();
        // bindTo(String title, String subTitle)
        mFloatHeader.bindTo(mTitle, "");

        //앱바 이미지
        Animation anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Glide.with(this)
                .load(Integer.parseInt(mProductModel.getProductImgURL()))
                .asBitmap()
//                .centerCrop()
                .animate(anim)
//                .placeholder(android.R.drawable.progress_horizontal)
                .thumbnail(0.1f) //이미지 크기중 10%만 먼저 가져와 보여줍니다.
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        mProductImageView.setImageBitmap(bitmap);
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
                    }
                });

        //header text
        mDetailProductAdapter.setHeader(mProductModel);

        //개별상품로딩
        NetworkManager.getInstance().getNetworkDetailProduct(this, new NetworkManager.OnResultListener<CodiResult>() {

            @Override
            public void onSuccess(CodiResult result) {
//                for (CodiModel item : result.items) {
//                    mDetailProductAdapter.add(item);
//                }
                mDetailProductAdapter.addList(result.items);
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

}
