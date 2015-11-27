package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.LocationFragment;
import com.dressing.dressingproject.ui.models.MallModel;
import com.dressing.dressingproject.ui.widget.BaseSearchModelFrameLayout;
import com.dressing.dressingproject.ui.widget.LocationHeaderView;
import com.dressing.dressingproject.ui.widget.StoreLocationView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 20.
 */
public class LocationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StoreLocationView.OnItemClickListener
{

    private final LocationFragment mParentFragment;
    public List<MallModel> items = new ArrayList<MallModel>();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    public boolean mVisiblewHeader;
    public RecyclerView.ViewHolder mHeaderHolder;

    public LocationRecyclerAdapter(LocationFragment locationFragment) {
        mParentFragment = locationFragment;
    }


    public interface OnAdapterItemListener {
        public void onAdapterItemClick(LocationRecyclerAdapter adapter, View view, MallModel mallModel,int position);
    }

    OnAdapterItemListener mListener;
    public void setOnAdapterItemListener(OnAdapterItemListener listener) {
        mListener = listener;
    }

    @Override
    public void onItemClick(View view, MallModel mallModel, int position) {
        if (mListener != null) {
            mListener.onAdapterItemClick(this, view, mallModel,position);
            //헤더뷰에 위치 텍스트 추가

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseSearchModelFrameLayout view;
        switch (viewType)
        {
            case TYPE_ITEM:
                StoreLocationView storeLocationView = new StoreLocationView(parent.getContext());
                return new LocationViewHolder(storeLocationView);
            case TYPE_HEADER:
                view = new LocationHeaderView(mParentFragment);
                return new LocationHeaderViewHolder(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        if(holder instanceof LocationViewHolder)
        {
            MallModel item = items.get(position-1);
            ((StoreLocationView)holder.itemView).setItem(item);
            ((StoreLocationView)holder.itemView).setPosition(position);
            ((StoreLocationView)holder.itemView).setChecked(item.isSelected());
            ((StoreLocationView)holder.itemView).setOnItemClickListener(this);

        }
        else if(holder instanceof LocationHeaderViewHolder)
        {
            ((LocationHeaderView)holder.itemView).addTagText(GetTagText());
        }
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if(holder instanceof LocationHeaderViewHolder)
        {
            mVisiblewHeader = true;
            mHeaderHolder =holder;
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(holder instanceof LocationHeaderViewHolder)
        {
            mVisiblewHeader = false;
        }
    }

    public String GetTagText()
    {
        String test = "";
        for(MallModel item: items)
        {
            if(item.isSelected())
                test += item.brandName +",";
        }
        return test;
    }


    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;

    }

    public void addMallList(ArrayList<MallModel> itemlist) {
        items.addAll(itemlist);
        notifyDataSetChanged();
    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void Clear() {
        items.clear();
        notifyDataSetChanged();
    }


    protected class LocationHeaderViewHolder extends RecyclerView.ViewHolder {
        public LocationHeaderViewHolder(BaseSearchModelFrameLayout productSearchHeaderView) {
            super(productSearchHeaderView);
        }
    }

    protected class LocationViewHolder extends RecyclerView.ViewHolder {
        public LocationViewHolder(StoreLocationView itemview) {
            super(itemview);
        }
    }

}