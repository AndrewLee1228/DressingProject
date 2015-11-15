package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.BaseModelFrameLayout;
import com.dressing.dressingproject.ui.widget.DetailProductHeaderView;
import com.dressing.dressingproject.ui.widget.CodiModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 9.
 */
public class DetailProductAdapter extends RecyclerViewBaseAdapter implements BaseModelFrameLayout.OnItemClickListener{

    private List<CodiModel> items = new ArrayList<CodiModel>();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String mHeaderNameText = "";
    private boolean mIsFavorite = false;
    private String mHeaderPriceText;
    private String mHeaderBrandText;
    private String mHeaderProductNumText;

    public DetailProductAdapter() {
    }

    public interface OnAdapterItemListener {
        public void onAdapterItemClick(DetailProductAdapter adapter, View view, CodiModel codiModel,int position);
    }

    OnAdapterItemListener mListener;
    public void setOnAdapterItemListener(OnAdapterItemListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            DetailProductHeaderView view = new DetailProductHeaderView(parent.getContext());
            view.setInfo(mHeaderNameText, mHeaderPriceText, mHeaderBrandText, mHeaderProductNumText, mIsFavorite);
            return new ViewHolderHeader(view);
        }
        else if (viewType == TYPE_ITEM) {
            CodiModelView view = new CodiModelView(parent.getContext());
            return new ViewHolderItem(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof ViewHolderItem) {
            CodiModel item = items.get(position-1);
            ((CodiModelView)holder.itemView).setCodiItem(item);
            ((CodiModelView)holder.itemView).setPosition(position);
            ((CodiModelView)holder.itemView).setOnItemClickListener(this);
        }
        else if(holder instanceof ViewHolderHeader)
        {
            ((DetailProductHeaderView)holder.itemView).setOnItemClickListener(this);
            ((DetailProductHeaderView)holder.itemView).setPosition(position);
            //헤더뷰 데이터셋
//            ViewHolderHeader viewHolderHeader = (ViewHolderHeader)holder;
        }

    }

    @Override public int getItemCount() {
        return items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void add(CodiModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<CodiModel> itemlist) {
        items.addAll(itemlist);
        notifyDataSetChanged();
    }

    public void setHeader(ProductModel productModel) {
        mHeaderNameText = productModel.getProdutcName();
        mHeaderPriceText = productModel.getProductPrice();
        mHeaderBrandText = productModel.getProductBrandName();
        mHeaderProductNumText = productModel.getProductNum();
        mIsFavorite = productModel.isFavorite();
    }

    @Override
    public void onItemClick(View view, CodiModel codiModel,int position) {
        if (mListener != null) {
            mListener.onAdapterItemClick(this, view, codiModel,position);
        }
    }

    protected static class ViewHolderItem extends RecyclerView.ViewHolder {

        public ViewHolderItem(CodiModelView itemView) {
            super(itemView);
        }
    }

    protected static class ViewHolderHeader extends RecyclerView.ViewHolder {
        DetailProductHeaderView mDetailProductHeaderView;
        public ViewHolderHeader(DetailProductHeaderView detailProductHeaderView) {
            super(detailProductHeaderView);
            mDetailProductHeaderView = detailProductHeaderView;
        }
    }

}
