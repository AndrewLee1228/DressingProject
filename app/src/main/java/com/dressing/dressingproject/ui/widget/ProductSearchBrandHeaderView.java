package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 9.
 */
public class ProductSearchBrandHeaderView extends BaseSearchModelFrameLayout{


    public ProductSearchBrandHeaderView(Context context) {
        super(context);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.item_search_product_brand_headerview, this);
        Button searchBtn = (Button) findViewById(R.id.item_search_product_brand_search_btn);
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }
}
