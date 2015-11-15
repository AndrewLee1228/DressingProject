package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.ApplicationLoader;
import com.dressing.dressingproject.ui.adapters.CategoryAdapter;
import com.dressing.dressingproject.ui.adapters.MyLinearLayoutManager;
import com.dressing.dressingproject.ui.adapters.ViewPagerAdapter;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

/**
 * 메인화면
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init(); //레이아웃 초기화
        InitButton();//버튼 초기화

    }

    /**
     * 하단 Navi 버튼 초기화
     */
    private void InitButton() {
        LinearLayout btnLayout = (LinearLayout) findViewById(R.id.activity_main_bottom_item_codi_layout);
        btnLayout.setOnClickListener(this);
        btnLayout = (LinearLayout) findViewById(R.id.activity_main_bottom_item_search_product_layout);
        btnLayout.setOnClickListener(this);
        btnLayout = (LinearLayout) findViewById(R.id.activity_main_bottom_item_favorite_layout);
        btnLayout.setOnClickListener(this);
        btnLayout = (LinearLayout) findViewById(R.id.activity_main_bottom_item_fitting_layout);
        btnLayout.setOnClickListener(this);
        btnLayout = (LinearLayout) findViewById(R.id.activity_main_bottom_item_location_layout);
        btnLayout.setOnClickListener(this);
    }

    /**
     * 레이아웃 초기화
     */
    public void Init()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_app_bar_main_toolbar);
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
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getBaseContext(), SettingActivity.class);
                        startActivity(intent);
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
        final LinearLayout bottomNaviLayout = (LinearLayout) findViewById(R.id.activity_main_content_main_bottom_navi_layout);
        CoordinatorLayout coorLayout = (CoordinatorLayout) findViewById(R.id.tabanim_maincontent);

        /*변경하고 싶은 레이아웃의 파라미터 값을 가져 옴*/
        final FrameLayout.LayoutParams coorLayoutParams = (FrameLayout.LayoutParams) coorLayout.getLayoutParams();

        ViewTreeObserver vto = bottomNaviLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // 이곳에서 View의 정보를 가져 올 수 있다.
                //int w = bottomNaviLayout.getWidth();
                int h = bottomNaviLayout.getHeight();
                //높이 세팅
                coorLayoutParams.bottomMargin = h;

                // onGlobalLayout이 계속 호출될 필요가 없는 경우 설정된 리스너를 제거
                ViewTreeObserver obs = bottomNaviLayout.getViewTreeObserver();
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

        //탭 세팅
        mTabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);

        //ViewPager 세팅
        mViewPager = (ViewPager) findViewById(R.id.activity_main_tabanim_viewpager);
        setupViewPager(mViewPager, FRAGMENT_RECOMMEND);//추천코디 첫번째 화면에 세팅!



        //탭을 선택 했을 때 호출됨.
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        //showToast("One");
                        break;
                    case 1:
                        //showToast("Two");
                        break;
                    case 2:
                        //showToast("Three");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * BottomSheet
     * Category와 SubCategory의 어뎁터와 RecyclerView 초기화
     */
    private void initBottomSheet() {


        mBottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        mBottomSheetLayout.showWithSheetView(getLayoutInflater().inflate(R.layout.bottomsheet_view, mBottomSheetLayout, false));
        mSubCategoryTextView =(TextView)findViewById(R.id.bottomsheet_view_sub_category_textview);
        mSearchBtn = (Button)findViewById(R.id.bottomsheet_view_search_btn);
        mSearchBtn.setOnClickListener(this);

        //레이아웃 높이 계산하여 Bottom Sheet의 높이를 세팅한다.
        mRootLayout = (LinearLayout) findViewById(R.id.bottomsheet_view_layout);
        mRootLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRootLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = mRootLayout.getHeight();
                mBottomSheetLayout.setPeekSheetTranslation(height);
                return false;
            }
        });

        mCategoryRecyclerView = (RecyclerView)findViewById(R.id.bottomsheet_view_category_recycler);
        mSubCategoryRecyclerView = (RecyclerView)findViewById(R.id.bottomsheet_view_sub_category_recycler);


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
        else if (mBottomSheetLayout.isSheetShowing())
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
        getSupportActionBar().setTitle(title);
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


//        ViewPagerAdapter mAdapter = (ViewPagerAdapter) viewPager.getAdapter();
//        if(mAdapter == null)
//        {
           ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
                adapter.addFrag(new RecommendCodiFragment(), getString(R.string.fragment_recommend_text), FRAGMENT_FLAG);
                TabLayoutVisible(false);
                //액션바 타이틀 변경
                setActionBarTitle(getString(R.string.app_name));
                break;

            case FRAGMENT_PRODUCT:
                TabLayoutVisible(true);
                //액션바 타이틀 변경
                setActionBarTitle(getString(R.string.activity_main_bottom_item_search_product_text));

                //파람전달
                ArrayList<CategoryModel> categoryModels =mCategoryAdapter.getCheckedItems();
                ArrayList<CategoryModel> subCategoryModels =mSubCategoryAdapter.getCheckedItems();

                adapter.addFrag(ProductAllFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_total_text),FRAGMENT_FLAG);
                adapter.addFrag(ProductBrandFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_brand_text),FRAGMENT_FLAG);
                adapter.addFrag(ProductColorFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_color_text),FRAGMENT_FLAG);
                adapter.addFrag(ProductPriceFragment.newInstance(categoryModels,subCategoryModels), getString(R.string.fragment_product_price_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_FAVORITE:
                TabLayoutVisible(true);
                setActionBarTitle(getString(R.string.activity_main_bottom_item_favorite_text));
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_favorite_codi_text),FRAGMENT_FLAG);
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_favorite_product_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_FITTING:
                TabLayoutVisible(false);
                setActionBarTitle(getString(R.string.activity_main_bottom_item_fitting_text));
                adapter.addFrag(new FittingFragment(), getString(R.string.fragment_fitting_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_LOCATION:
                TabLayoutVisible(false);
                setActionBarTitle(getString(R.string.activity_main_bottom_item_location_text));
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_location_text),FRAGMENT_FLAG);
                break;

        }

            mViewPager.setAdapter(adapter);
            mTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_main_bottom_item_codi_layout:
                setupViewPager(mViewPager,FRAGMENT_RECOMMEND);
                break;
            case R.id.activity_main_bottom_item_search_product_layout:
                initBottomSheet();
                break;
            case R.id.activity_main_bottom_item_favorite_layout:
                setupViewPager(mViewPager,FRAGMENT_FAVORITE);
                break;
            case R.id.activity_main_bottom_item_fitting_layout:
                setupViewPager(mViewPager,FRAGMENT_FITTING);
                break;
            case R.id.activity_main_bottom_item_location_layout:
                setupViewPager(mViewPager,FRAGMENT_LOCATION);
                break;
            case R.id.bottomsheet_view_search_btn:

                //상품검색아이콘 상태 변경
                //BottomSheet dismiss()
                mBottomSheetLayout.dismissSheet();
                setupViewPager(mViewPager, FRAGMENT_PRODUCT);

                break;

        }
    }

}
