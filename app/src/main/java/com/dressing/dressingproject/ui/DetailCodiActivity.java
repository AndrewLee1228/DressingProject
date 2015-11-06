package com.dressing.dressingproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailCodiAdapter;
import com.dressing.dressingproject.ui.models.CodiProducts;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.HeaderView;

public class DetailCodiActivity extends AppCompatActivity implements DetailCodiAdapter.OnItemClickListener, AppBarLayout.OnOffsetChangedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_codi);




        InitLayout();

//        Intent i = this.getIntent();
//        String movieId = i.getStringExtra(MovieDetailsFragment.KEY_MOVIE_ID);
//        String movieTitle = i.getStringExtra(MovieDetailsFragment.KEY_MOVIE_TITLE);
        mTitle = "남자들아, 겨울이 오면\n이렇게 입어주자!";
        // bindTo(String title, String subTitle)
        mFloatHeader.bindTo(mTitle, "");

        Bitmap codiBmp = BitmapFactory.decodeResource(getResources(),R.drawable.test_codi);
        Palette.from(codiBmp).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch darkVibrantSwatch    = palette.getDarkVibrantSwatch();
                Palette.Swatch darkMutedSwatch      = palette.getDarkMutedSwatch();

                Palette.Swatch backgroundAndContentColors = (darkVibrantSwatch != null)
                        ? darkVibrantSwatch : darkMutedSwatch;

                mTitleColor = backgroundAndContentColors.getRgb();
            }
        });
        mCodiImageView.setImageBitmap(codiBmp);
//



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

        //헤더뷰
        mToolbarHeader = (HeaderView) findViewById(R.id.activity_detail_codi_toolbar_header_view);
        mFloatHeader = (HeaderView) findViewById(R.id.activity_detail_codi_float_header_view);

        //리싸이클러
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_detail_codi_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        final DetailCodiAdapter detailCodiAdapter = new DetailCodiAdapter();
        detailCodiAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(detailCodiAdapter);

        NetworkManager.getInstance().getNetworkDetailCodi(this, new NetworkManager.OnResultListener<CodiProducts>() {

            @Override
            public void onSuccess(CodiProducts result) {
                for (ProductModel item : result.items) {
                    detailCodiAdapter.add(item);
                }
            }

            @Override
            public void onFail(int code) {

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
     * 헤더뷰 컨트롤
     * @param appBarLayout
     * @param offset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        Log.i("percentage : ", Float.toString(percentage));

        //헤더뷰 영역 최소일 경우
        if (percentage == 1f && isHideToolbarView) {
            mToolbarHeader.setVisibility(View.GONE);
            mFloatHeader.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
            mCollapsingLayout.setTitle(mTitle);
            ChangeToolbarColor(mTitleColor,percentage);
        //헤더뷰 영역 최대일 경우
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

    @Override
    public void onItemClick(View view, ProductModel item) {
        switch (view.getId())
        {
            case R.id.item_detail_codi_view_image:
                Toast.makeText(this, "상품이 눌렸습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_detail_codi_view_image_favorite:
                Toast.makeText(DetailCodiActivity.this, "찜하기가 눌렸습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}