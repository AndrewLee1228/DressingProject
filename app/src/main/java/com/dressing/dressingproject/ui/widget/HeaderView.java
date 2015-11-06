package com.dressing.dressingproject.ui.widget;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dressing.dressingproject.R;


public class HeaderView extends LinearLayout{


     TextView mTitle;
     TextView mSubTitle;


    @Override
    public void setShowDividers(int showDividers) {
        super.setShowDividers(showDividers);
    }

    public HeaderView(Context context) {
        super(context);

    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTitle = (TextView)findViewById(R.id.header_view_title);
        mSubTitle = (TextView)findViewById(R.id.header_view_sub_title);
    }


    /**
     * 뷰와 그 하위 자식뷰들이 전개된 후 호출됩니다.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitle = (TextView)findViewById(R.id.header_view_title);
        mSubTitle = (TextView)findViewById(R.id.header_view_sub_title);
    }

    public void bindTo(String title) {
        bindTo(title, "");
    }

    public void bindTo(String title, String subTitle) {
        hideOrSetText(mTitle, title);
        hideOrSetText(mSubTitle, subTitle);
    }

    private void hideOrSetText(TextView tv, String text) {
        if (text == null || text.equals(""))
            tv.setVisibility(GONE);
        else
            tv.setText(text);
    }




}
