package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductSearchColorHeaderView extends BaseSearchModelFrameLayout {
    private CheckBox[] mChkArray;

    public ProductSearchColorHeaderView(Context context) {
        super(context);
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.item_search_product_color_headerview, this);
//        Button searchBtn = (Button) findViewById(R.id.item_search_product_color_search_btn);
//        searchBtn.setOnClickListener(this);

        mChkArray = new CheckBox[10];
        mChkArray[0]=(CheckBox) findViewById(R.id.rb_colorbox_red);
        mChkArray[0].setTag("ff0000"); //컬러 헥사코드!
        mChkArray[1]=(CheckBox) findViewById(R.id.rb_colorbox_black);
        mChkArray[1].setTag("000000");
        mChkArray[2]=(CheckBox) findViewById(R.id.rb_colorbox_blue);
        mChkArray[2].setTag("1565c0");
        mChkArray[3]=(CheckBox) findViewById(R.id.rb_colorbox_brown);
        mChkArray[3].setTag("6d4c41");
        mChkArray[4]=(CheckBox) findViewById(R.id.rb_colorbox_gray);
        mChkArray[4].setTag("757575");
        mChkArray[5]=(CheckBox) findViewById(R.id.rb_colorbox_green);
        mChkArray[5].setTag("558b2f");
        mChkArray[6]=(CheckBox) findViewById(R.id.rb_colorbox_navy);
        mChkArray[6].setTag("263238");
        mChkArray[7]=(CheckBox) findViewById(R.id.rb_colorbox_orange);
        mChkArray[7].setTag("ef6c00");
        mChkArray[8]=(CheckBox) findViewById(R.id.rb_colorbox_white);
        mChkArray[8].setTag("ffffff");
        mChkArray[9]=(CheckBox) findViewById(R.id.rb_colorbox_yellow);
        mChkArray[9].setTag("ffca28");

        for (int i = 0; i < mChkArray.length; i++) {
            mChkArray[i].setOnClickListener(this);
        }

//        CheckBox colorRedBox = (CheckBox) findViewById(R.id.rb_colorbox_red);
//        CheckBox colorBlackBox = (CheckBox) findViewById(R.id.rb_colorbox_black);
//        CheckBox colorBlueBox = (CheckBox) findViewById(R.id.rb_colorbox_blue);
//        CheckBox colorBrownBox = (CheckBox) findViewById(R.id.rb_colorbox_brown);
//        CheckBox colorGrayBox = (CheckBox) findViewById(R.id.rb_colorbox_gray);
//        CheckBox colorGreenBox = (CheckBox) findViewById(R.id.rb_colorbox_green);
//        CheckBox colorNavyBox = (CheckBox) findViewById(R.id.rb_colorbox_navy);
//        CheckBox colorOrangekBox = (CheckBox) findViewById(R.id.rb_colorbox_orange);
//        CheckBox colorWhiteBox = (CheckBox) findViewById(R.id.rb_colorbox_white);
//        CheckBox colorYellowBox = (CheckBox) findViewById(R.id.rb_colorbox_yellow);
//        colorRedBox.setOnClickListener(this);
//        colorBlackBox.setOnClickListener(this);
//        colorBlueBox.setOnClickListener(this);
//        colorBrownBox.setOnClickListener(this);
//        colorGrayBox.setOnClickListener(this);
//        colorGreenBox.setOnClickListener(this);
//        colorNavyBox.setOnClickListener(this);
//        colorOrangekBox.setOnClickListener(this);
//        colorWhiteBox.setOnClickListener(this);
//        colorYellowBox.setOnClickListener(this);

    }

    //체크박스 배열을 리턴
    public CheckBox[] getCheckBoxArray()
    {
        return mChkArray;
    }

    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }
}
