package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductSearchPriceHeaderView extends BaseSearchModelFrameLayout {
    public ProductSearchPriceHeaderView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_search_product_price_headerview, this);
        Button searchBtn = (Button) findViewById(R.id.item_search_product_price_search_btn);
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
