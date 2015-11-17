package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.SelectedTabType;

/**
 * Created by lee on 15. 11. 16.
 */
public class TabBar extends LinearLayout implements View.OnClickListener {

    private View mCodiTab;
    private ImageView mCodiImg;
    private TextView mCodiText;
    private View mSearchProductTab;
    private ImageView mSearchProductImg;
    private TextView mSearchProductText;
    private View mFavoriteTab;
    private ImageView mFavoriteImg;
    private TextView mFavoriteText;
    private View mFittingTab;
    private ImageView mFittingImg;
    private TextView mFittingText;
    private View mLocationTab;
    private ImageView mLocationImg;
    private TextView mLocationText;

    public TabBar(Context context) {
        super(context);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private TabSelectedListener tabSelectedListener;

    public interface TabSelectedListener {
        public void onTabSelected(SelectedTabType type);
    }

    public TabSelectedListener getTabSelectedListener() {
        return this.tabSelectedListener;
    }

    public void setTabSelectedListener(TabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_tabbar, this);

        mCodiTab = (View)findViewById(R.id.view_tabbar_bottom_item_codi_layout);
        mCodiImg = (ImageView)findViewById(R.id.view_tabbar_bottom_item_codi_img);
        mCodiText = (TextView)findViewById(R.id.view_tabbar_bottom_item_codi_text);

        mSearchProductTab = (View)findViewById(R.id.view_tabbar_bottom_item_search_product_layout);
        mSearchProductImg = (ImageView)findViewById(R.id.view_tabbar_bottom_item_search_product_img);
        mSearchProductText = (TextView)findViewById(R.id.view_tabbar_bottom_item_search_product_text);

        mFavoriteTab = (View)findViewById(R.id.view_tabbar_bottom_item_favorite_layout);
        mFavoriteImg = (ImageView)findViewById(R.id.view_tabbar_bottom_item_favorite_img);
        mFavoriteText = (TextView)findViewById(R.id.view_tabbar_bottom_item_favorite_text);

        mFittingTab = (View)findViewById(R.id.view_tabbar_bottom_item_fitting_layout);
        mFittingImg = (ImageView)findViewById(R.id.view_tabbar_bottom_item_fitting_img);
        mFittingText = (TextView)findViewById(R.id.view_tabbar_bottom_item_fitting_text);

        mLocationTab = (View)findViewById(R.id.view_tabbar_bottom_item_location_layout);
        mLocationImg = (ImageView)findViewById(R.id.view_tabbar_bottom_item_location_img);
        mLocationText = (TextView)findViewById(R.id.view_tabbar_bottom_item_location_text);

        mCodiTab.setOnClickListener(this);
        mFavoriteTab.setOnClickListener(this);
        mFittingTab.setOnClickListener(this);
        mLocationTab.setOnClickListener(this);
        mSearchProductTab.setOnClickListener(this);

        clearSelected();
    }

    public void clearSelected() {
        selectedCodiTab(false);
        selectedSearchProductTab(false);
        selectedFavoriteTab(false);
        selectedFittingTab(false);
        selectedLocationTab(false);
    }

    public void selectedLocationTab(boolean isSelected) {
        if (isSelected)
        {
            mLocationImg.setImageResource(R.drawable.buttom_tapbar_location);
            mLocationText.setTextColor(Color.BLUE);
        } else
        {
            mLocationImg.setImageResource(R.drawable.buttom_tapbar_location_unselect);
            mLocationText.setTextColor(Color.BLACK);
        }
    }

    public void selectedFittingTab(boolean isSelected) {
        if (isSelected)
        {
            mFittingImg.setImageResource(R.drawable.buttom_tapbar_fitting);
            mFittingText.setTextColor(Color.BLUE);
        } else
        {
            mFittingImg.setImageResource(R.drawable.buttom_tapbar_fitting_unselect);
            mFittingText.setTextColor(Color.BLACK);
        }
    }

    public void selectedFavoriteTab(boolean isSelected) {
        if (isSelected)
        {
            mFavoriteImg.setImageResource(R.drawable.buttom_tapbar_favorite);
            mFavoriteText.setTextColor(Color.BLUE);
        } else
        {
            mFavoriteImg.setImageResource(R.drawable.buttom_tapbar_favorite_unselect);
            mFavoriteText.setTextColor(Color.BLACK);
        }
    }

    public void selectedSearchProductTab(boolean isSelected) {
        if (isSelected)
        {
            mSearchProductImg.setImageResource(R.drawable.buttom_tapbar_product);
            mSearchProductText.setTextColor(Color.BLUE);
        } else
        {
            mSearchProductImg.setImageResource(R.drawable.buttom_tapbar_product_unselect);
            mSearchProductText.setTextColor(Color.BLACK);
        }
    }

    public void selectedCodiTab(boolean isSelected) {
        if (isSelected)
        {
            mCodiImg.setImageResource(R.drawable.buttom_tapbar_recommend);
            mCodiText.setTextColor(Color.BLUE);
        } else
        {
            mCodiImg.setImageResource(R.drawable.buttom_tapbar_recommend_unselect);
            mCodiText.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onClick(View v) {
        if (this.tabSelectedListener != null) {
            SelectedTabType type = null;
            switch (v.getId()) {
                case R.id.view_tabbar_bottom_item_codi_layout:
                    type = SelectedTabType.Codi;
                    break;
                case R.id.view_tabbar_bottom_item_search_product_layout:
                    type = SelectedTabType.SearchProduct;
                    break;
                case R.id.view_tabbar_bottom_item_favorite_layout:
                    type = SelectedTabType.Favorite;
                    break;
                case R.id.view_tabbar_bottom_item_fitting_layout:
                    type = SelectedTabType.Fitting;
                    break;
                case R.id.view_tabbar_bottom_item_location_layout:
                    type = SelectedTabType.Location;
                    break;
            }
            this.tabSelectedListener.onTabSelected(type);
        }
    }


}
