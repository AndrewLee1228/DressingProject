package com.dressing.dressingproject.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
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
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailProductAdapter;
import com.dressing.dressingproject.ui.models.CodiFavoriteResult;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.ProductFavoriteResult;
import com.dressing.dressingproject.ui.models.ProductItems;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.HeaderView;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

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
    private int mThumbnailTop;
    private int mThumbnailLeft;
    private int mThumbnailWidth;
    private int mThumbnailHeight;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;
    private static final int ANIM_DURATION = 600;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if (intent != null) {
            mProductModel = (ProductModel) intent.getExtras().get("ProductModel");
            mThumbnailTop = bundle.getInt("top");
            mThumbnailLeft = bundle.getInt("left");
            mThumbnailWidth = bundle.getInt("width");
            mThumbnailHeight = bundle.getInt("height");
        }

        InitLayout(savedInstanceState);

        InitValue();


    }

    private void InitLayout(Bundle savedInstanceState) {

        mAppBarLayout = (AppBarLayout)findViewById(R.id.activity_detail_product_app_bar_layout);
        mAppBarLayout.addOnOffsetChangedListener(this);

        mCollapsingLayout = (CollapsingToolbarLayout)findViewById(R.id.activity_detail_product_collapsing_layout);

        //툴바
        mToolbar = (Toolbar) findViewById(R.id.activity_detail_product_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //이미지뷰
        mProductImageView = (ImageView)findViewById(R.id.activity_detail_product_image);

        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = mProductImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mProductImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    mProductImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = mThumbnailLeft - screenLocation[0];
                    mTopDelta = mThumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) mThumbnailWidth / mProductImageView.getWidth();
                    mHeightScale = (float) mThumbnailHeight / mProductImageView.getHeight();

                    enterAnimation();

                    return true;
                }
            });
        }

        //헤더뷰
        mToolbarHeader = (HeaderView) findViewById(R.id.activity_detail_product_toolbar_header_view);
        mFloatHeader = (HeaderView) findViewById(R.id.activity_detail_product_float_header_view);

        //리싸이클러
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_detail_product_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.GONE);
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
                        //뷰의 현재 셀렉트 상태를 확인하여 아이템 찜 세팅
                        if (view.isSelected() == false) {
                            codiModel.setIsFavorite(true);
                        } else {
                            codiModel.setIsFavorite(false);
                        }

                        NetworkManager.getInstance().requestUpdateCodiFavorite(getApplicationContext(), codiModel, new NetworkManager.OnResultListener<CodiFavoriteResult>() {

                            @Override
                            public void onSuccess(CodiFavoriteResult codiFavoriteResult) {
                                //찜하기 요청이 정삭적으로 처리 되었으므로
                                //뷰의 셀렉트 상태를 변경한다.
                                if (codiFavoriteResult.getSelectedState()) {
                                    view.setSelected(true);
                                    AndroidUtilities.MakeFavoriteToast(getApplicationContext());
                                } else
                                    view.setSelected(false);
                            }

                            @Override
                            public void onFail(int code) {
                                //찜하기 요청 실패
                            }

                        });
                        break;
                    //상품
                    case R.id.item_detail_product_headerview_favorite:
                        if (view.isSelected() == false) {
                            mProductModel.setIsFavorite(true);
                        } else {
                            mProductModel.setIsFavorite(false);
                        }

                        NetworkManager.getInstance().requestUpdateProductFavorite(getApplicationContext(), mProductModel, new NetworkManager.OnResultListener<ProductFavoriteResult>() {

                            @Override
                            public void onSuccess(ProductFavoriteResult productFavoriteResult)
                            {
                                if (productFavoriteResult.getSelectedState())
                                {
                                    view.setSelected(true);
                                    AndroidUtilities.MakeFavoriteToast(getApplicationContext());
                                }
                                else
                                    view.setSelected(false);
                            }

                            @Override
                            public void onFail(int code) {
                                //찜하기 실패
                            }

                        });
                        break;

                    case R.id.item_recommend_view_frame_layout:
                        ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.getInstance(Float.parseFloat(codiModel.getUserScore()));
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

    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location.
     */
    public void enterAnimation() {

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        mProductImageView.setPivotX(0);
        mProductImageView.setPivotY(0);
        mProductImageView.setScaleX(mWidthScale);
        mProductImageView.setScaleY(mHeightScale);
        mProductImageView.setTranslationX(mLeftDelta);
        mProductImageView.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        mProductImageView.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(Color.WHITE, "alpha", 0, 255);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        bgAnim.start();

    }

    /**
     * The exit animation is basically a reverse of the enter animation.
     * This Animate image back to thumbnail size/location as relieved from bundle.
     *
     * @param endAction This action gets run after the animation completes (this is
     *                  when we actually switch activities)
     */
    public void exitAnimation(final Runnable endAction) {

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        mProductImageView.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(Color.WHITE, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
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

    private void InitValue() {

        //앱바 타이틀
        mTitle = mProductModel.getProductTitle();
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
                .postProcessor(new BitmapProcessor() {
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

        ImageLoader.getInstance().displayImage("drawable://" + mProductModel.getProductImgURL(), mProductImageView, options);

        //header text
        mDetailProductAdapter.setHeader(mProductModel);

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
