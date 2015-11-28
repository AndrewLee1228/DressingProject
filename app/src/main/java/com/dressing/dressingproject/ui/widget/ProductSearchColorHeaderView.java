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
        init(context);
    }
    private void init(Context context) {
        inflate(getContext(), R.layout.item_search_product_color_headerview, this);
//        Button searchBtn = (Button) findViewById(R.id.item_search_product_color_search_btn);
//        searchBtn.setOnClickListener(this);

        //color array
        String[] color_array = context.getResources().getStringArray(R.array.default_color_choice_values);

        mChkArray = new CheckBox[10];
        mChkArray[0]=(CheckBox) findViewById(R.id.rb_colorbox_red);
        mChkArray[1]=(CheckBox) findViewById(R.id.rb_colorbox_black);
        mChkArray[2]=(CheckBox) findViewById(R.id.rb_colorbox_blue);
        mChkArray[3]=(CheckBox) findViewById(R.id.rb_colorbox_brown);
        mChkArray[4]=(CheckBox) findViewById(R.id.rb_colorbox_gray);
        mChkArray[5]=(CheckBox) findViewById(R.id.rb_colorbox_green);
        mChkArray[6]=(CheckBox) findViewById(R.id.rb_colorbox_navy);
        mChkArray[7]=(CheckBox) findViewById(R.id.rb_colorbox_orange);
        mChkArray[8]=(CheckBox) findViewById(R.id.rb_colorbox_white);
        mChkArray[9]=(CheckBox) findViewById(R.id.rb_colorbox_yellow);

        for (int i = 0; i < mChkArray.length; i++) {
            mChkArray[i].setOnClickListener(this);  //리스너 세팅
            /**
             * 컬러 헥사코드! s.replace("#", "") 특정 문자열 지우기
             */
            mChkArray[i].setTag(color_array[i].replace("#", ""));//color 값 tag 세팅
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
