package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.BaseSearchModelFrameLayout;
import com.dressing.dressingproject.ui.widget.ProductSearchBrandHeaderView;
import com.dressing.dressingproject.ui.widget.ProductSearchColorHeaderView;
import com.dressing.dressingproject.ui.widget.ProductSearchPriceHeaderView;
import com.dressing.dressingproject.ui.widget.ProductSearchView;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductBasicHeaderRecyclerAdapter extends ProductBasicAllRecyclerAdapter
{
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_HEADER_BRAND = 1;
    public static final int TYPE_HEADER_COLOR = 2;
    public static final int TYPE_HEADER_PRICE = 3;
    private int mHeaderFlag;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseSearchModelFrameLayout view;
        switch (viewType)
        {
            case TYPE_ITEM:
                ProductSearchView productSearchView = new ProductSearchView(parent.getContext());
                return new ProductSearchViewHolder(productSearchView);
            case TYPE_HEADER_BRAND:
                view = new ProductSearchBrandHeaderView(parent.getContext());
                return new ProductSearchHeaderViewHolder(view);
            case TYPE_HEADER_COLOR:
                view = new ProductSearchColorHeaderView(parent.getContext());
                return new ProductSearchHeaderViewHolder(view);
            case TYPE_HEADER_PRICE:
                view = new ProductSearchPriceHeaderView(parent.getContext());
                return new ProductSearchHeaderViewHolder(view);

        }

        throw new RuntimeException("there is no type that matches the type " + viewType);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        if(holder instanceof ProductSearchViewHolder)
        {
            ProductModel item = items.get(position-1);
            ((ProductSearchView)holder.itemView).setProductItem(item);
            ((ProductSearchView)holder.itemView).setPosition(position);
            ((ProductSearchView)holder.itemView).setOnItemClickListener(this);

        }
        else if(holder instanceof ProductSearchHeaderViewHolder)
        {
            ((BaseSearchModelFrameLayout)holder.itemView).setOnItemClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if(isPositionHeader(position) ==true)
        {
            return mHeaderFlag;
        }
        else return TYPE_ITEM;

    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setHeaderFlag(int headerFlag) {
            mHeaderFlag = headerFlag;
    }


    protected static class ProductSearchHeaderViewHolder extends RecyclerView.ViewHolder {
        public ProductSearchHeaderViewHolder(BaseSearchModelFrameLayout productSearchHeaderView) {
            super(productSearchHeaderView);
        }
    }

}
