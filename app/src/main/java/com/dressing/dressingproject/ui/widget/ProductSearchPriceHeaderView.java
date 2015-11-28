package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.dressing.dressingproject.R;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductSearchPriceHeaderView extends BaseSearchModelFrameLayout {
    private RangeBar mRangebar;
    private TextView mPriceText;

    public ProductSearchPriceHeaderView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_search_product_price_headerview, this);

        mPriceText = (TextView) findViewById(R.id.price_headerview_text);

        // Gets the index value TextViews
        mRangebar = (RangeBar) findViewById(R.id.price_headerview_rangebar);
        //초기값 세팅!
        mRangebar.setRangePinsByValue(0,0);

        // Sets the display values of the indices
        mRangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar,
                                              int leftPinIndex,int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                //가격 값을 뷰에 세팅!
                Log.d("test", "onRangeChangeListener: ");
                String price = String.format("설정금액 : %s ~ %s",leftPinValue,rightPinValue);
                mPriceText.setText(price);
            }

        });

        mRangebar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    if (onItemClickListener != null) {
                        view.setTag(mRangebar);
                        onItemClickListener.onItemClick(view, null,0);
                    }

                }
                return false;
            }
        });


        mRangebar.setPinTextFormatter(new RangeBar.PinTextFormatter() {
            @Override
            public String getText(String value) {
                if (value.equalsIgnoreCase("0")) {
                    return value;
                }
                else if(value.equalsIgnoreCase("16"))
                {
                    return "제한없음";
                }
                else
                {
                    value = String.format("%s%s", Currency.getInstance(Locale.KOREA).getSymbol(),value+"0,000");
                    return value;
                }
            }
        });
    }



    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }
}
