package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.ApplicationLoader;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.manager.PropertyManager;
import com.dressing.dressingproject.ui.adapters.CategoryAdapter;
import com.dressing.dressingproject.ui.adapters.DividerItemDecoration;
import com.dressing.dressingproject.ui.adapters.FavoriteCodiAdapter;
import com.dressing.dressingproject.ui.adapters.FavoriteProductAdapter;
import com.dressing.dressingproject.ui.adapters.MyLinearLayoutManager;
import com.dressing.dressingproject.ui.adapters.ViewPagerAdapter;
import com.dressing.dressingproject.ui.models.AnalysisResult;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.SelectedTabType;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.ui.widget.TabBar;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.dressing.dressingproject.util.FontManager;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

/**
 * 메인화면
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,TabBar.TabSelectedListener,TabLayout.OnTabSelectedListener{

    private static final int FRAGMENT_RECOMMEND = 0;    //코디추천 프래그먼트
    private static final int FRAGMENT_PRODUCT = 1;      //상품 프래그먼트
    private static final int FRAGMENT_FAVORITE = 2;     //찜하기 프래그먼트
    private static final int FRAGMENT_FITTING = 3;      //피팅하기 프래그먼트
    private static final int FRAGMENT_LOCATION = 4;     //위치설정 프래그먼트

    CategoryModel[] mCategoryModels = ApplicationLoader.getCatagoryModels();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BottomSheetLayout mBottomSheetLayout;
    private RecyclerView mCategoryRecyclerView;
    private CategoryAdapter mCategoryAdapter;
    private LinearLayout mRootLayout;
    private RecyclerView mSubCategoryRecyclerView;
    private CategoryAdapter mSubCategoryAdapter;
    private TextView mSubCategoryTextView;
    private Button mSearchBtn;
    private Button mFittingBtn;
    private Button mFavoriteDeleteBtn;
    private TabBar mTabbar;
    private ViewPagerAdapter mAdapter;
    private ImageView mArrowLeft;
    private ImageView mArrowRight;
    private Animation mAnimBlink;
    private RadarChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitButton();//버튼 초기화
        Init(); //레이아웃 초기화

        //네트워크 요청
        NetworkManager.getInstance().requestAnalysis(this, new NetworkManager.OnResultListener<AnalysisResult>() {

            @Override
            public void onSuccess(AnalysisResult analysisResult) {
                if (analysisResult.code == 200) {
                    LeftDrawLayoutInit(analysisResult);
                }
            }

            @Override
            public void onFail(int code) {
                //찜하기 요청 실패
            }

        });

    }

    /**
     * 왼쪽 드로어 레이아웃 초기화!
     * @param analysisResult
     */
    private void LeftDrawLayoutInit(AnalysisResult analysisResult) {

        ArrayList<String> sortedKeyword= analysisResult.sortedKeyword;
        TextView keyword1 = (TextView) findViewById(R.id.keyword1);
        keyword1.setText(sortedKeyword.get(0));
        TextView keyword2 = (TextView) findViewById(R.id.keyword2);
        keyword2.setText(sortedKeyword.get(1));
        TextView keyword3 = (TextView) findViewById(R.id.keyword3);
        keyword3.setText(sortedKeyword.get(2));
        TextView keyword4 = (TextView) findViewById(R.id.keyword4);
        keyword4.setText(sortedKeyword.get(3));
        TextView keyword5 = (TextView) findViewById(R.id.keyword5);
        keyword5.setText(sortedKeyword.get(4));
        TextView keyword6 = (TextView) findViewById(R.id.keyword6);
        keyword6.setText(sortedKeyword.get(5));

        PropertyManager propertyManager = PropertyManager.getInstance();

        //사용자 닉네임
        String nick = String.format("%s님 취향 분석",propertyManager.getUserNickName());
        TextView userNick = (TextView) findViewById(R.id.nav_user_nick);
        userNick.setText(nick);

        //사용자 이미지
        String userImgURL = propertyManager.getUserImgURL();
        final ImageView userImg = (ImageView) findViewById(R.id.chart_user_img);
        Glide.with(this).load(userImgURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(userImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userImg.setImageDrawable(circularBitmapDrawable);
            }
        });

        mChart = (RadarChart) findViewById(R.id.chart);

        mChart.setDescription("");
        mChart.setRotationAngle(60);

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        setData(analysisResult);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO));
        xAxis.setTextSize(9f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setTypeface(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO));
        yAxis.setStartAtZero(true);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTypeface(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO));
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

//    private String[] mParties = new String[] {
//            "빈티지", "캐쥬얼", "남성적", "모던", "세련", "댄디"
//    };


    public void setData(AnalysisResult analysisResult) {
        String[] parties = new String[6];
        for (int i = 0; i < 6; i++) {
            parties[i] = analysisResult.sortedLiking.get(i);
        }
        float mult = 150;
        int cnt = 6;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < cnt; i++) {
            //mult 해당 값

            switch (i)
            {
                case 0:
                    yVals1.add(new Entry((float) 130 , i));
                    break;
                case 1:
                    yVals1.add(new Entry((float) 150 , i));
                    break;
                case 2:
                    yVals1.add(new Entry((float) 120 , i));
                    break;
                case 4:
                    yVals1.add(new Entry((float) 180 , i));
                    break;
                default:
                    yVals1.add(new Entry((float) 90 , i));
                    break;
            }
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(parties[i % parties.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "선호취향");
        set1.setColor(Color.parseColor("#fbc02d"));
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(xVals, sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);

        mChart.setData(data);

        mChart.invalidate();
    }

    /**
     * 하단 Navi 버튼 초기화
     */
    private void InitButton() {

        mFittingBtn = (Button)findViewById(R.id.fitting_btn);
        mFittingBtn.setTypeface(FontManager.getInstance().getTypeface(this, FontManager.NOTO), Typeface.BOLD);
        mFittingBtn.setOnClickListener(this);

        mFavoriteDeleteBtn = (Button)findViewById(R.id.favorite_delete_btn);
        mFavoriteDeleteBtn.setTypeface(FontManager.getInstance().getTypeface(this, FontManager.NOTO), Typeface.BOLD);
        mFavoriteDeleteBtn.setOnClickListener(this);

        mTabbar = (TabBar) this.findViewById(R.id.activity_main_content_main_tabbar);
        mTabbar.setTabSelectedListener(this);
//        //시작은 코디탭을 세팅한다.
//        mTabbar.selectedCodiTab(true);
        //시작은 로케이션 세팅한다.
        mTabbar.selectedLocationTab(true);
//        setupViewPager(mViewPager, FRAGMENT_LOCATION);

    }

    /**
     * 레이아웃 초기화
     */
    public void Init()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_app_bar_main_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.main_blue));
        setSupportActionBar(toolbar);


        //네비게이션 Control
        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Button btn = (Button) findViewById(R.id.nav_content_style_analysis_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getBaseContext(), StyleActivity.class);
                        startActivity(intent);
                    }
                });

                ImageView settingBtn = (ImageView) findViewById(R.id.nav_content_setting_btn);
                settingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
//                        drawer.closeDrawer(GravityCompat.START);
//                        Intent intent = new Intent(getBaseContext(), SettingActivity.class);
//                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        /*
         *하단 Navi Button의 Height를 가져옴.
         *CoordinatorLayout에 하단 네비버튼 높이 만큼 마진을 설정한다.
         */

        CoordinatorLayout coorLayout = (CoordinatorLayout) findViewById(R.id.tabanim_maincontent);

        /*변경하고 싶은 레이아웃의 파라미터 값을 가져 옴*/
        final FrameLayout.LayoutParams coorLayoutParams = (FrameLayout.LayoutParams) coorLayout.getLayoutParams();

        ViewTreeObserver vto = mTabbar.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // 이곳에서 View의 정보를 가져 올 수 있다.
                //int w = bottomNaviLayout.getWidth();
                int h = mTabbar.getHeight();
                //높이 세팅
                coorLayoutParams.bottomMargin = h;

                // onGlobalLayout이 계속 호출될 필요가 없는 경우 설정된 리스너를 제거
                ViewTreeObserver obs = mTabbar.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }

        });

        //레이아웃 파람 세팅
        coorLayout.setLayoutParams(coorLayoutParams);

        //ActionBarDrawerToggle 세팅
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //홈버튼 변경
        //토글 다음에 다음로직을 처리해야 함.
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_homeasup_profile);



        //탭 세팅
        mTabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);

        //ViewPager 세팅
        mViewPager = (ViewPager) findViewById(R.id.activity_main_tabanim_viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        /**
                         * 찜하기 프래그먼트의 경우에는 탭이 이동될 때
                         * 프래그먼트의 아이템 선택여부에 따라 피팅하기 버튼 Visible ,GONE 모드를 선택해야 한다.
                         */
                        if (mAdapter.getFragmentFlag() == FRAGMENT_FAVORITE) {
                            setVisibleFittingBtn(View.GONE);
                            FavoriteCodiAdapter favoriteCodiAdapter = ((FavoriteCodiFragment) mAdapter.getItem(0)).getAdapter();
                            if (favoriteCodiAdapter != null && favoriteCodiAdapter.getCheckedItems().size() > 0) {
                                setVisibleFittingBtn(View.VISIBLE);
                            }
                        }
                        break;
                    case 1:
                        if (mAdapter.getFragmentFlag() == FRAGMENT_FAVORITE) {
                            setVisibleFittingBtn(View.GONE);
                            FavoriteProductAdapter favoriteProductAdapter = ((FavoriteProductFragment) mAdapter.getItem(1)).getAdapter();
                            if (favoriteProductAdapter != null && favoriteProductAdapter.getCheckedItems().size() > 0) {
                                setVisibleFittingBtn(View.VISIBLE);
                            }
                        }
                        break;
                    case 2:
                        //showToast("Three");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        setupViewPager(mViewPager, FRAGMENT_RECOMMEND);//추천코디 첫번째 화면에 세팅!
        setupViewPager(mViewPager, FRAGMENT_LOCATION);//위치설정 첫번째 화면에 세팅!


        //탭을 선택 했을 때 호출됨.
        mTabLayout.setOnTabSelectedListener(this);
    }

    public Fragment GetCurrentPageFragment()
    {
        return mAdapter.getItem(mViewPager.getCurrentItem());
    }

    /**
     * BottomSheet
     * Category와 SubCategory의 어뎁터와 RecyclerView 초기화
     */
    private void initBottomSheet()
    {


        mBottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        mBottomSheetLayout.showWithSheetView(getLayoutInflater().inflate(R.layout.bottomsheet_view, mBottomSheetLayout, false));
        mSubCategoryTextView =(TextView)findViewById(R.id.bottomsheet_view_sub_category_textview);
        mSearchBtn = (Button)findViewById(R.id.bottomsheet_view_search_btn);
        mSearchBtn.setTypeface(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO), Typeface.BOLD);
        mSearchBtn.setOnClickListener(this);

        //레이아웃 높이 계산하여 Bottom Sheet의 높이를 세팅한다.
        mRootLayout = (LinearLayout) findViewById(R.id.bottomsheet_view_layout);
        mRootLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRootLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = mRootLayout.getHeight() + AndroidUtilities.dp(10);
                mBottomSheetLayout.setPeekSheetTranslation(height);
                return false;
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);

        mCategoryRecyclerView = (RecyclerView)findViewById(R.id.bottomsheet_view_category_recycler);
        mCategoryRecyclerView.addItemDecoration(dividerItemDecoration);
        mSubCategoryRecyclerView = (RecyclerView)findViewById(R.id.bottomsheet_view_sub_category_recycler);
        mSubCategoryRecyclerView.addItemDecoration(dividerItemDecoration);

        //화살표 애니메이션
        mArrowLeft = (ImageView)findViewById(R.id.bottomsheet_view_category_arrow_left);
        mArrowRight = (ImageView)findViewById(R.id.bottomsheet_view_category_arrow_right);

        mAnimBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                                                    R.anim.blink);
        // set animation listener
        mAnimBlink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // check for blink animation
                if (animation == mAnimBlink) {
                    mArrowLeft.setVisibility(View.GONE);
                    mArrowRight.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //애니메이션 실행
        mArrowLeft.startAnimation(mAnimBlink);
        mArrowRight.startAnimation(mAnimBlink);



        //레이아웃 방향은 가로
        /**
         * 레이아웃 방향은 가로
         * 자식뷰의 사이즈를 제대로 인식하지 못하는 문제가 있어서 이를 계산하는 로직 추가됨
         * @param context
         * @param HORIZONTAL //방향
         * @param boolean //아이템순서반전여부
         */
        MyLinearLayoutManager categoryManager = new MyLinearLayoutManager(
                                            this,
                                            LinearLayoutManager.HORIZONTAL,
                                            false
                                            );

        //CategoryAdapter

        mCategoryAdapter = new CategoryAdapter(this);
        mCategoryRecyclerView.setLayoutManager(categoryManager);
        mCategoryAdapter.setIsSingle(true); //어뎁터 싱글모드
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);

        //카테고리 아이템 세팅
        for(CategoryModel categoryModel :mCategoryModels)
        {
            mCategoryAdapter.add(categoryModel);
        }

        //SubCategoryAdapter

        MyLinearLayoutManager subCategoryManager = new MyLinearLayoutManager(
                                                            this,
                                                            LinearLayoutManager.HORIZONTAL,
                                                            false
                                                    );

        mSubCategoryAdapter = new CategoryAdapter(this);
        mSubCategoryRecyclerView.setLayoutManager(subCategoryManager);
        mSubCategoryAdapter.setIsSingle(false); //어뎁터 멀티모드
        mSubCategoryRecyclerView.setAdapter(mSubCategoryAdapter);

    }


    //Bottom Sheet 검색버튼 활성/비활성
    public void setEnableSearchBtn(boolean searchEnable) {
        mSearchBtn.setEnabled(searchEnable);
    }

    //서브카테고리 아이템리스트 세팅
    public void setSubCategory(ArrayList<CategoryModel> subCategoryList)
    {
        mSubCategoryRecyclerView.setVisibility(View.VISIBLE);
        mSubCategoryTextView.setVisibility(View.GONE);

        //클리어
        mSubCategoryAdapter.Clear();

        //카테고리 아이템 세팅
        for(CategoryModel categoryModel :subCategoryList)
        {
            mSubCategoryAdapter.add(categoryModel);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //바텀시트가 보이는지 확인
        else if (mBottomSheetLayout != null &&mBottomSheetLayout.isSheetShowing())
        {
            mBottomSheetLayout.dismissSheet();
        }
        else
        {
            super.onBackPressed();
        }
    }

    //액션바 타이틀 세팅
    public void setActionBarTitle(String title)
    {
        //액션바에 폰트적용
        SpannableString s = new SpannableString(title);
        s.setSpan(FontManager.getInstance().getTypeface(getApplicationContext(), FontManager.NOTO), Typeface.BOLD, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        //코디 추천일 경우만 아이콘세팅
        if(title.equals(getString(R.string.view_tabbar_bottom_item_codi_text)))
        {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setIcon(R.drawable.ic_title_logo);
        }
        else
            getSupportActionBar().setIcon(null);
    }

    //토스트 메시지 생성
    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void TabLayoutVisible(boolean isVisible)
    {
        if(isVisible){
            mTabLayout.setVisibility(View.VISIBLE);
        }
        else mTabLayout.setVisibility(View.GONE);
    }


    //Change ViewPager & Fragment Binding.
    private void setupViewPager(ViewPager viewPager,int FRAGMENT_FLAG) {

          //피팅버튼 숨김
          setVisibleFittingBtn(View.GONE);

//        ViewPagerAdapter mAdapter = (ViewPagerAdapter) viewPager.getAdapter();
//        if(mAdapter == null)
//        {
           mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//           mAdapter.ClearFragment();

//            viewPager.setAdapter(mAdapter);
//        }
//        else
//        {
//            mAdapter = (ViewPagerAdapter) viewPager.getAdapter();
//            mAdapter.ClearFragment();
//            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            viewPager.setAdapter(mAdapter);
//        }
//
//
//
        //같으면 현재 프래그먼트 이므로 리턴한다.
        //FRAGMENT_PRODUCT 는 예외 매번 갱신해야 하므로!
        if(FRAGMENT_FLAG != FRAGMENT_PRODUCT &&(viewPager.getAdapter() != null) &&
                ((ViewPagerAdapter)viewPager.getAdapter()).getFragmentFlag() == FRAGMENT_FLAG)
        {
            return ;
        }
        //같지 않다면 초기화
        else
        {
            viewPager.setAdapter(null);
        }

        switch (FRAGMENT_FLAG)
        {
            case FRAGMENT_RECOMMEND:
                mAdapter.addFrag(new RecommendCodiFragment(), getString(R.string.fragment_recommend_text), FRAGMENT_FLAG);
                TabLayoutVisible(false);
                //액션바 타이틀 변경
                setActionBarTitle(getString(R.string.view_tabbar_bottom_item_codi_text));
                break;

            case FRAGMENT_PRODUCT:
                TabLayoutVisible(true);
                //액션바 타이틀 변경
                setActionBarTitle(getString(R.string.view_tabbar_bottom_item_search_product_text));

                //파람전달
                ArrayList<CategoryModel> categoryModels =mCategoryAdapter.getCheckedItems();
                ArrayList<CategoryModel> subCategoryModels =mSubCategoryAdapter.getCheckedItems();

                mAdapter.addFrag(ProductAllFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_total_text),FRAGMENT_FLAG);
                mAdapter.addFrag(ProductBrandFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_brand_text),FRAGMENT_FLAG);
                mAdapter.addFrag(ProductColorFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_color_text),FRAGMENT_FLAG);
                mAdapter.addFrag(ProductPriceFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_price_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_FAVORITE:
                TabLayoutVisible(true);
                setActionBarTitle(getString(R.string.view_tabbar_bottom_item_favorite_text));
                mAdapter.addFrag(new FavoriteCodiFragment(), getString(R.string.fragment_favorite_codi_text),FRAGMENT_FLAG);
                mAdapter.addFrag(new FavoriteProductFragment(), getString(R.string.fragment_favorite_product_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_FITTING:
                TabLayoutVisible(false);
                setActionBarTitle(getString(R.string.view_tabbar_bottom_item_fitting_text));
                mAdapter.addFrag(new FittingFragment(), getString(R.string.fragment_fitting_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_LOCATION:
                TabLayoutVisible(false);
                setActionBarTitle(getString(R.string.view_tabbar_bottom_item_location_text));
                mAdapter.addFrag(LocationFragment.newInstance(), getString(R.string.fragment_location_text),FRAGMENT_FLAG);
                break;

        }

            mViewPager.setAdapter(mAdapter);
            mViewPager.setOffscreenPageLimit(2);
            mTabLayout.setupWithViewPager(mViewPager);

    }

    public ViewPagerAdapter getViewPagerAdapter()
    {
        return mAdapter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.activity_main_bottom_item_codi_layout:
//                setupViewPager(mViewPager,FRAGMENT_RECOMMEND);
//                break;
//            case R.id.activity_main_bottom_item_search_product_layout:
//                initBottomSheet();
//                break;
//            case R.id.activity_main_bottom_item_favorite_layout:
//                setupViewPager(mViewPager,FRAGMENT_FAVORITE);
//                break;
//            case R.id.activity_main_bottom_item_fitting_layout:
            case R.id.fitting_btn:
                //피팅아이콘 상태 변경
                mTabbar.clearSelected();
                mTabbar.selectedFittingTab(true);
                setupViewPager(mViewPager,FRAGMENT_FITTING);
                break;
            case R.id.favorite_delete_btn:
                //찜 여러개 동시에 해제
                //해제된 아이템 어뎁터에서 삭제
                Fragment fragment = GetCurrentPageFragment();
                if (fragment instanceof FavoriteCodiFragment) {
                    ArrayList<CodiModel> checkedItems = ((FavoriteCodiFragment)fragment).getAdapter().getCheckedItems();
                    for(CodiModel item: checkedItems)
                    {
                        CodiFavoriteDelete(item,fragment);
                    }
                }
                else if(fragment instanceof FavoriteProductFragment)
                {
                    ArrayList<ProductModel> checkedItems = ((FavoriteProductFragment)fragment).getAdapter().getCheckedItems();
                    for(ProductModel item : checkedItems)
                    {
                        ProductFavoriteDelete(item,fragment);
                    }
                }
                break;
//            case R.id.activity_main_bottom_item_location_layout:
//                setupViewPager(mViewPager,FRAGMENT_LOCATION);
//                break;
            case R.id.bottomsheet_view_search_btn:

                //상품검색아이콘 상태 변경
                mTabbar.selectedSearchProductTab(true);
                //BottomSheet dismiss()
                mBottomSheetLayout.dismissSheet();
                setupViewPager(mViewPager, FRAGMENT_PRODUCT);

                break;
//
        }
    }

    /**
     * 상품찜 리스트 찜해제
     * @param productModel
     * @param fragment
     */
    private void ProductFavoriteDelete(final ProductModel productModel, final Fragment fragment) {
        NetworkManager.getInstance().requestDeleteFavorite(this, true,productModel, null, new NetworkManager.OnResultListener<SucessResult>() {

            @Override
            public void onSuccess(SucessResult sucessResult) {
                //찜하기 해제 요청이 정삭적으로 처리 되었으므로
                //뷰의 셀렉트 상태를 변경한다.
                if(sucessResult.code ==200)
                {
                    //네트워크 재요청
                    ((FavoriteProductFragment)fragment).request(true);
                }
            }

            @Override
            public void onFail(int code) {
                //찜하기 요청 실패
            }

        });
    }

    /**
     * 코디찜리스트 찜해제
     * @param codiModel
     * @param fragment
     */
    private void CodiFavoriteDelete(final CodiModel codiModel,final Fragment fragment) {
        NetworkManager.getInstance().requestDeleteFavorite(this,true, null, codiModel, new NetworkManager.OnResultListener<SucessResult>() {

            @Override
            public void onSuccess(SucessResult sucessResult) {
                //찜하기 해제 요청이 정삭적으로 처리 되었으므로
                //뷰의 셀렉트 상태를 변경한다.
                if(sucessResult.code ==200)
                {
                    //어뎁터에서 해당 아이템 제거
                    ((FavoriteCodiFragment)fragment).request(true);
                }
            }

            @Override
            public void onFail(int code) {
                //찜하기 요청 실패
            }

        });
    }

    public void setVisibleFittingBtn(int visible)
    {
        if(mFittingBtn != null){
            mFittingBtn.setVisibility(visible);
        }

        if(mFavoriteDeleteBtn != null)
        {
            mFavoriteDeleteBtn.setVisibility(visible);
        }
    }

    @Override
    public void onTabSelected(SelectedTabType type)
    {
        mTabbar.clearSelected();
        switch (type) {
            case Codi:
                setupViewPager(mViewPager, FRAGMENT_RECOMMEND);
                mTabbar.selectedCodiTab(true);
                break;
            case SearchProduct:
                initBottomSheet();
                break;
            case Favorite:
                setupViewPager(mViewPager,FRAGMENT_FAVORITE);
                mTabbar.selectedFavoriteTab(true);
                break;
            case Fitting:
                setupViewPager(mViewPager,FRAGMENT_FITTING);
                mTabbar.selectedFittingTab(true);
                break;
            case Location:
                setupViewPager(mViewPager, FRAGMENT_LOCATION);
                mTabbar.selectedLocationTab(true);
                break;
//            default:
//                if (MainApplication.isLogin()) {
//                    navigateTo(CircleFragment.class);
//                    this.tabbar.selectedCircleTab(true);
//                } else {
//                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1001);
//                }
//                break;
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
