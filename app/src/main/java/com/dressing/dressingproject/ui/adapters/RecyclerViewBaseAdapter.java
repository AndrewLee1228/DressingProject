package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by lee on 15. 11. 12.
 */
public class RecyclerViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void ItemChanged(int position)
    {
        notifyItemChanged(position);
    }
}
