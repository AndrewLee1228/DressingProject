package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.ProductSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 15.
 */
public class ProductSearchAllRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > implements ProductSearchView.OnItemClickListener
{

    public List<ProductModel> items = new ArrayList<ProductModel>();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public interface OnAdapterItemListener {
        public void onAdapterItemClick(ProductSearchAllRecyclerAdapter adapter, View view, ProductModel productModel,int position);
    }

    OnAdapterItemListener mListener;
    public void setOnAdapterItemListener(OnAdapterItemListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ProductSearchView productSearchView = new ProductSearchView(parent.getContext());
        return new ProductSearchViewHolder(productSearchView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        if(holder instanceof ProductSearchViewHolder)
        {
            ProductModel item = items.get(position);
            ((ProductSearchView)holder.itemView).setProductItem(item);
            ((ProductSearchView)holder.itemView).setPosition(position);
            ((ProductSearchView)holder.itemView).setOnItemClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(ProductModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<ProductModel> itemlist) {
        items.addAll(itemlist);
        notifyDataSetChanged();
    }
    @Override
    public void onItemClick(View view, ProductModel productModel, int position) {
        if (mListener != null) {
            mListener.onAdapterItemClick(this, view, productModel,position);
        }
    }

    class ProductSearchViewHolder extends RecyclerView.ViewHolder
    {

        public ProductSearchViewHolder(ProductSearchView itemView) {
            super(itemView);
        }

    }

}
