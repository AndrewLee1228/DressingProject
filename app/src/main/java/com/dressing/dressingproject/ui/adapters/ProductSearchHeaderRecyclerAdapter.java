package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.DetailProductHeaderView;
import com.dressing.dressingproject.ui.widget.ProductSearchView;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductSearchHeaderRecyclerAdapter extends ProductSearchAllRecyclerAdapter
{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == TYPE_HEADER) {
            DetailProductHeaderView view = new DetailProductHeaderView(parent.getContext());
//            view.setInfo(mHeaderNameText, mHeaderPriceText, mHeaderBrandText, mHeaderProductNumText, mIsFavorite);
            return new ProductSearchHeaderViewHolder(view);
        }
        else if (viewType == TYPE_ITEM) {
            ProductSearchView productSearchView = new ProductSearchView(parent.getContext());
            return new ProductSearchViewHolder(productSearchView);
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

        }
    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }


    protected static class ProductSearchHeaderViewHolder extends RecyclerView.ViewHolder {
        public ProductSearchHeaderViewHolder(DetailProductHeaderView detailProductHeaderView) {
            super(detailProductHeaderView);
        }
    }

}
