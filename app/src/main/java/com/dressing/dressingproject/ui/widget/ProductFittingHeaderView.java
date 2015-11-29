package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.util.FontManager;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductFittingHeaderView extends BaseSearchModelFrameLayout {
    private Button mSearchBtn;

    public ProductFittingHeaderView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_product_fitting_headerview, this);
        mSearchBtn = (Button) findViewById(R.id.item_product_fitting_search_btn);
        mSearchBtn.setTypeface(FontManager.getInstance().getTypeface(getContext(), FontManager.NOTO), Typeface.BOLD);
        mSearchBtn.setOnClickListener(this);
    }

    public void SetPrice(int price)
    {
        mSearchBtn.setText(String.format("총 금액은 : %s %,d", Currency.getInstance(Locale.KOREA).getSymbol(),price));
    }

    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }
}
