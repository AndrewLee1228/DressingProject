package com.dressing.dressingproject.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dressing.dressingproject.R;

/**
 * Created by lee on 15. 11. 8.
 */
public class DetailCodiHeaderView extends FrameLayout{
    private TextView mItemHeaderText;
    private SquareImageView mItemHeaderFavorite;

    public DetailCodiHeaderView(Context context, View.OnClickListener clickListener) {
        super(context);
        init(clickListener);
    }

    private void init(View.OnClickListener clickListener) {
        inflate(getContext(), R.layout.item_detail_codi_headerview, this);
        mItemHeaderText = (TextView)findViewById(R.id.item_detail_codi_headerview_text);
        mItemHeaderFavorite = (SquareImageView)findViewById(R.id.item_detail_codi_headerview_favorite);
        mItemHeaderFavorite.setOnClickListener(clickListener);
    }

    public void setText(String text)
    {
        mItemHeaderText.setText(text);
    }

}
