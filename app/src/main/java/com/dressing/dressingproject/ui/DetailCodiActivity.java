package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailCodiAdapter;
import com.dressing.dressingproject.ui.models.CodiProducts;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.HeaderView;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public class DetailCodiActivity extends AppCompatActivity implements DetailCodiAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener , RatingBar.OnRatingBarChangeListener{

    private CollapsingToolbarLayout mCollapsingLayout;
    private HeaderView mToolbarHeader;
    private HeaderView mFloatHeader;
    private RecyclerView mRecyclerView;
    private AppBarLayout mAppBarLayout;

    private boolean isHideToolbarView = false;
    private String mTitle;
    private Toolbar mToolbar;
    private ImageView mCodiImageView;
    private int mTitleColor;
    private TextView mRecommendViewTextView;
    private AlertDialog mAlertDialog;
    private RatingBar mRatingBar;
    private DetailCodiAdapter mDetailCodiAdapter;

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
        mCodiImageView = (ImageView)findViewById(R.id.activity_detail_codi_image);

        mRecommendViewTextView = (TextView)findViewById(R.id.item_recommend_view_text);
        mRecommendViewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialog.show();
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

        if (id == android.R.id.home) {
            onBackPressed();
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
        if (percentage == 1f && isHideToolbarView) {
            mToolbarHeader.setVisibility(View.GONE);
            mFloatHeader.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
            mCollapsingLayout.setTitle(mTitle);
            ChangeToolbarColor(mTitleColor,percentage);
        //AppBarLayout 영역 최대일 경우
        } else if (percentage < 1f && !isHideToolbarView) {
            mToolbarHeader.setVisibility(View.GONE );
            mFloatHeader.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
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

        //앱바 타이틀
        mTitle = "남자들아, 겨울이 오면\n이렇게 입어주자!";
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

        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.test_codi, mCodiImageView, options);

        //코디추천
        float score = 0;
        mRecommendViewTextView.setText(Float.toString(score));
        mRatingBar.setRating(score);

        //header text
        String text = "제품설명 블라블라~";
        boolean isFavorite = true;
        mDetailCodiAdapter.setHeader(text, isFavorite);

        //개별상품로딩
        NetworkManager.getInstance().getNetworkDetailCodi(this, new NetworkManager.OnResultListener<CodiProducts>() {

            @Override
            public void onSuccess(CodiProducts result) {
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
                Toast.makeText(this, "상품이 눌렸습니다.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if(fromUser==true){
            mAlertDialog.dismiss();
            mRecommendViewTextView.setText(Float.toString(rating));
            Toast.makeText(DetailCodiActivity.this, "평가되었습니다!", Toast.LENGTH_SHORT).show();
        }

    }


}
