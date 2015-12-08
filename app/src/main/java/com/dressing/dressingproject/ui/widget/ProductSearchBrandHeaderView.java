package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.MainActivity;
import com.dressing.dressingproject.ui.ProductBrandFragment;
import com.dressing.dressingproject.ui.adapters.MainChipViewAdapter;
import com.dressing.dressingproject.ui.adapters.OnChipClickListener;
import com.dressing.dressingproject.ui.models.BrandListData;
import com.dressing.dressingproject.ui.models.Chip;
import com.dressing.dressingproject.ui.models.Tag;
import com.dressing.dressingproject.util.FontManager;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 9.
 */
public class ProductSearchBrandHeaderView extends BaseSearchModelFrameLayout implements OnChipClickListener {


    private Context mContext;
    private ArrayList<Chip> mTagList;

    private ChipView mTextChipLayout;
    private MainChipViewAdapter mAdapterLayout;

    public ProductSearchBrandHeaderView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    //ChipView 추가
    public void addTag(BrandListData item)
    {
        mAdapterLayout.add(new Tag(item.getName(),item.getCode(),item.getImg(),item.getBrandNum(),item.isSync()));
    }

    //어뎁터 초기화
    public void adapterClear()
    {
        mAdapterLayout.Clear();
    }


    private void init() {
        inflate(getContext(), R.layout.item_search_product_brand_headerview, this);
        Button searchBtn = (Button) findViewById(R.id.item_search_product_brand_search_btn);
        searchBtn.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        searchBtn.setOnClickListener(this);

        mTagList = new ArrayList<>();

        // Adapter
        mAdapterLayout = new MainChipViewAdapter(getContext());

        mTextChipLayout = (ChipView) findViewById(R.id.text_chip_layout);
        mTextChipLayout.setAdapter(mAdapterLayout);
        mTextChipLayout.setChipLayoutRes(R.layout.chip_close);

        mTextChipLayout.setChipBackgroundColor(getResources().getColor(R.color.main_blue));
        mTextChipLayout.setChipBackgroundColorSelected(getResources().getColor(R.color.main_blue));
//        mTextChipLayout.setChipBackgroundColor(getResources().getColor(R.color.light_green));
//        mTextChipLayout.setChipBackgroundColorSelected(getResources().getColor(R.color.green));
        mTextChipLayout.setChipList(mTagList);
        mTextChipLayout.setOnChipClickListener(this);


    }

    @Override
    public void onChipClick(Chip chip) {
        mTextChipLayout.remove(chip);
        ProductBrandFragment fragment = (ProductBrandFragment) ((MainActivity) mContext).GetCurrentPageFragment();
        fragment.updateFilter(mAdapterLayout.getChipList());
    }

    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }
}
