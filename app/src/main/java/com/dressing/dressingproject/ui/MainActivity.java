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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.ViewPagerAdapter;

/**
 * 메인화면
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int FRAGMENT_RECOMMEND = 0;    //코디추천 프래그먼트
    private static final int FRAGMENT_PRODUCT = 1;      //상품 프래그먼트
    private static final int FRAGMENT_FAVORITE = 2;     //찜하기 프래그먼트
    private static final int FRAGMENT_FITTING = 3;      //피팅하기 프래그먼트
    private static final int FRAGMENT_LOCATION = 4;     //위치설정 프래그먼트

    private TabLayout mTabLayout;
    private ViewPager mViewPager;


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
                        Intent intent = new Intent(getBaseContext(),StyleActivity.class);
                        startActivity(intent);
                    }
                });

                ImageView settingBtn = (ImageView) findViewById(R.id.nav_content_setting_btn);
                settingBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(getBaseContext(),SettingActivity.class);
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
        setupViewPager(mViewPager, FRAGMENT_PRODUCT);//추천코디 첫번째 화면에 세팅!

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

        ViewPagerAdapter adapter;
        //같으면 현재 프래그먼트 이므로 리턴한다.
        if((viewPager.getAdapter() != null) &&
                ((ViewPagerAdapter)viewPager.getAdapter()).getFragmentFlag() == FRAGMENT_FLAG)
        {
            return ;
        }
        //같지 않다면 초기화
        else
        {
            viewPager.setAdapter(null);
            adapter = new ViewPagerAdapter(getSupportFragmentManager());

        }

        switch (FRAGMENT_FLAG)
        {
            case FRAGMENT_RECOMMEND:
            default:
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.accent_material_light)), getString(R.string.fragment_recommend_text),FRAGMENT_FLAG);
                TabLayoutVisible(false);
                //액션바 타이틀 변경
                setActionBarTitle(getString(R.string.app_name));
                break;

            case FRAGMENT_PRODUCT:
                TabLayoutVisible(true);
                //액션바 타이틀 변경
                setActionBarTitle(getString(R.string.activity_main_bottom_item_search_product_text));
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_product_total_text),FRAGMENT_FLAG);
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_product_brand_text),FRAGMENT_FLAG);
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_product_color_text),FRAGMENT_FLAG);
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_product_price_text),FRAGMENT_FLAG);
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
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_fitting_text),FRAGMENT_FLAG);
                break;

            case FRAGMENT_LOCATION:
                TabLayoutVisible(false);
                setActionBarTitle(getString(R.string.activity_main_bottom_item_location_text));
                adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), getString(R.string.fragment_location_text),FRAGMENT_FLAG);
                break;

        }
        viewPager.setAdapter(adapter);
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
                setupViewPager(mViewPager,FRAGMENT_PRODUCT);
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

        }
    }
}
