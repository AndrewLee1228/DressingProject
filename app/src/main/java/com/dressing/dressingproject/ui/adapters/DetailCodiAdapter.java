package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.DetailCodiHeaderView;
import com.dressing.dressingproject.ui.widget.DetailCodiView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 5.
 */
public class DetailCodiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ProductModel> items = new ArrayList<ProductModel>();
    private OnItemClickListener onItemClickListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;;
    private String mHeaderText = "";
    private boolean mIsFavorite;

    public DetailCodiAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            DetailCodiHeaderView view = new DetailCodiHeaderView(parent.getContext(),this);
            view.setText(mHeaderText);
            view.setFavorite(mIsFavorite);
            return new ViewHolderHeader(view);
        }
        else if (viewType == TYPE_ITEM) {
            DetailCodiView view = new DetailCodiView(parent.getContext(),this);
            return new ViewHolderItem(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType);

    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderItem) {
            ProductModel item = items.get(position-1);
            ((DetailCodiView)holder.itemView).setProductItem(item);
            ((DetailCodiView)holder.itemView).productView.setTag(item);
        }
        else if(holder instanceof ViewHolderHeader)
        {
            //헤더뷰 데이터셋
//            ViewHolderHeader viewHolderHeader = (ViewHolderHeader)holder;

        }

    }

    @Override
    public int getItemViewType(int position) {

        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override public int getItemCount() {
        return items.size()+1;
    }


    @Override public void onClick(final View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (ProductModel) v.getTag());
        }
    }

    public void add(ProductModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setHeader(String text,boolean isFavorite) {
        mHeaderText = text;
        mIsFavorite = isFavorite;
    }

    protected static class ViewHolderItem extends RecyclerView.ViewHolder {

        public ViewHolderItem(DetailCodiView itemView) {
            super(itemView);
        }
    }

    protected static class ViewHolderHeader extends RecyclerView.ViewHolder {
        DetailCodiHeaderView mDetailCodiHeaderView;
        public ViewHolderHeader(DetailCodiHeaderView detailCodiHeaderView) {
            super(detailCodiHeaderView);
            mDetailCodiHeaderView = detailCodiHeaderView;
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ProductModel item);

    }
}
