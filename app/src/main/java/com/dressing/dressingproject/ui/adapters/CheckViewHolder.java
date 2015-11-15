package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.widget.CheckItemView;

/**
 * Created by lee on 15. 11. 14.
 */
public class CheckViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;
    private final ImageView mImageView;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    OnItemClickListener mCheckListener;
    public void setOnItemCheckListener(OnItemClickListener listener) {
        mCheckListener = listener;
    }

    CheckItemView itemView;

    public CheckViewHolder(CheckItemView itemView) {

        super(itemView);
        this.itemView = (CheckItemView)itemView;


        mTextView = (TextView)itemView.findViewById(R.id.item_check_view_text_name);

        mImageView = (ImageView)itemView.findViewById(R.id.item_check_view_image_icon);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckListener != null) {
                    mCheckListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
    }


    public void setImageId(int id)
    {
        mImageView.setImageResource(id);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setChecked(boolean checked) {
        if (itemView instanceof Checkable) {
            ((Checkable)itemView).setChecked(checked);
        }
    }
}
