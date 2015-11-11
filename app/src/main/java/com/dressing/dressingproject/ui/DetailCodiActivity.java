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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailCodiAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.HeaderView;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public class DetailCodiActivity extends AppCompatActivity implements DetailCodiAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener{

    private CollapsingToolbarLayout mCollapsingLayout;
    private HeaderView mToolbarHeader;
    private HeaderView mFloatHeader;
    private RecyclerView mRecyclerView;
    private AppBarLayout mAppBarLayout;

    private boolean mIsHideToolbarView = false;
    private Toolbar mToolbar;
    private ImageView mCodiImageView;
    private int mTitleColor;
    private TextView mRecommendViewTextView;
    private AlertDialog mAlertDialog;
    private DetailCodiAdapter mDetailCodiAdapter;
    private CodiModel mCodiModel;
    private FrameLayout mRecommendFrameLayout;
    private RelativeLayout mRecommendRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_codi);


        InitLayout();

        InitValue(getIntent());

    }

    private void InitLayout() {

        mAppBarLayout = (AppBarLayout)findViewById(R.id.activity_detail_codi_app_bar_layout);
        mAppBarLayout.addOnOffsetChangedListener(this);

        mCollapsingLayout = (CollapsingToolbarLayout)findViewById(R.id.activity_detail_codi_collapsing_layout);

        //툴바
        mToolbar = (Toolbar) findViewById(R.id.activity_detail_codi_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //이미지뷰
        mCodiImageView = (ImageView)findViewById(R.id.activity_detail_codi_image);

        mRecommendRootLayout = (RelativeLayout)findViewById(R.id.item_recommend_view_root_layout);

        mRecommendViewTextView = (TextView)findViewById(R.id.item_recommend_view_score_text);
        mRecommendFrameLayout = (FrameLayout)findViewById(R.id.item_recommend_view_frame_layout);
        mRecommendFrameLayout.bringToFront();
        mRecommendFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.getInstance(Float.parseFloat(mCodiModel.getEstimationScore()));
                scoreDialogFragment.setData(mCodiModel);
                scoreDialogFragment.show(getSupportFragmentManager(),"");
            }
        });

        //헤더뷰
        mToolbarHeader = (HeaderView) findViewById(R.id.activity_detail_codi_toolbar_header_view);
        mFloatHeader = (HeaderView) findViewById(R.id.activity_detail_codi_float_header_view);

        //리싸이클러
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_detail_codi_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mDetailCodiAdapter = new DetailCodiAdapter();
        mDetailCodiAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mDetailCodiAdapter);

        //그리드 레이아웃 Span 개수 Controll
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return mDetailCodiAdapter.isPositionHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }

        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
            mCollapsingLayout.setTitle(mCodiModel.getTitle());
            ChangeToolbarColor(mTitleColor, percentage);
        //AppBarLayout 영역 최대일 경우
        } else if (percentage < 1f && !mIsHideToolbarView) {
            mToolbarHeader.setVisibility(View.GONE );
            mFloatHeader.setVisibility(View.VISIBLE);
            mIsHideToolbarView = !mIsHideToolbarView;
            mCollapsingLayout.setTitle(" ");
            ChangeToolbarColor(Color.TRANSPARENT,0);
        }
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
//        Intent i = this.getIntent();
//        String movieId = i.getStringExtra(MovieDetailsFragment.KEY_MOVIE_ID);
//        String movieTitle = i.getStringExtra(MovieDetailsFragment.KEY_MOVIE_TITLE);

        //코디추천 점수
        mCodiModel = new CodiModel("남자들아, 겨울이 오면\n이렇게 입어주자!","코디 설명 블라블라~",
                                             Integer.toString(R.drawable.test_codi),
                                            "3.0",
                                            "0",
                                            true);
        /**코디 스코어 세팅
         * param score
         *
         * 스코어를 세팅하기전 예상score와 유저Score를 구분하여 score value와 View 의 상태를 변경한다.
         */
        if (mCodiModel.isRated() == true) {
            changeScore(mCodiModel.getUserScore(),true);
            mRecommendFrameLayout.setSelected(true);
        }
        else {
            changeScore(mCodiModel.getEstimationScore(),false);
            mRecommendRootLayout.setSelected(false);
        }


        /**플로팅 툴바텍스트 세팅
         * param 큰제목
         * param 소제목
         */
        mFloatHeader.bindTo(mCodiModel.getTitle(), "");


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

        ImageLoader.getInstance().displayImage("drawable://" + Integer.parseInt(mCodiModel.getImageURL()), mCodiImageView, options);


        /** 어뎁터의 헤더뷰 데이터 세팅
         * param 코디설명
         * param 코디 찜여부
         */
        mDetailCodiAdapter.setHeader(mCodiModel.getDescription(), mCodiModel.isFavorite());

        //개별상품로딩
        NetworkManager.getInstance().getNetworkDetailCodi(this, new NetworkManager.OnResultListener<CodiResult>() {

            @Override
            public void onSuccess(CodiResult result) {
                for (ProductModel item : result.items) {
                    mDetailCodiAdapter.add(item);
                }
            }

            @Override
            public void onFail(int code) {

            }
        });
    }

    @Override
    public void onItemClick(View view, ProductModel item) {
        switch (view.getId())
        {
            //item product click
            case R.id.item_detail_codi_view_image:
                Intent intent = new Intent(this,DetailProductActivity.class);

                intent.putExtra("ProductTitle",item.getProductTitle());
                intent.putExtra("ProdutcName",item.getProdutcName());
                intent.putExtra("MapURL",item.getMapURL());
                intent.putExtra("ProductBrandName",item.getProductBrandName());
                intent.putExtra("ProductImgURL",item.getProductImgURL());
                intent.putExtra("ProductPrice",item.getProductPrice());
                intent.putExtra("ProductNum",item.getProductNum());
                intent.putExtra("Favorite",item.isFavorite());

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            //item favorite click
            case R.id.item_detail_codi_view_image_favorite:
            case R.id.item_detail_codi_headerview_favorite:
                if(view.isSelected() == false){
                    view.setSelected(true);
                    AndroidUtilities.MakeFavoriteToast(getApplicationContext());
                }
                else{
                    view.setSelected(false);
                }
                break;
        }
    }


    public void changeScore(String rating,boolean isUserScore)
    {
        float floastRating = Float.parseFloat(rating);
        if (isUserScore == true) {
            mRecommendViewTextView.setText(String.format("%.1f", floastRating));
            mRecommendRootLayout.setSelected(true);
        }
        else
        {
            mRecommendViewTextView.setText(String.format("%.1f",floastRating));
            mRecommendRootLayout.setSelected(false);
        }

    }


}
