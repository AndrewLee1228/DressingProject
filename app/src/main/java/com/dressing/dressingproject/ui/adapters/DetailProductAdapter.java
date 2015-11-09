package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.widget.DetailProductHeaderView;
import com.dressing.dressingproject.ui.widget.DetailProductView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 9.
 */
public class DetailProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<CodiModel> items = new ArrayList<CodiModel>();
    private OnItemClickListener onItemClickListener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;;
    private String mHeaderNameText = "";
    private boolean mIsFavorite = false;
    private String mHeaderPriceText;
    private String mHeaderBrandText;
    private String mHeaderProductNumText;

    public DetailProductAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            DetailProductHeaderView view = new DetailProductHeaderView(parent.getContext(),this);
            view.setInfo(mHeaderNameText,mHeaderPriceText,mHeaderBrandText,mHeaderProductNumText,mIsFavorite);
            return new ViewHolderHeader(view);
        }
        else if (viewType == TYPE_ITEM) {
            DetailProductView view = new DetailProductView(parent.getContext(),this);
            return new ViewHolderItem(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof ViewHolderItem) {
            CodiModel item = items.get(position-1);
            ((DetailProductView)holder.itemView).setCodiItem(item);
            ((DetailProductView)holder.itemView).codiView.setTag(item);
        }
        else if(holder instanceof ViewHolderHeader)
        {
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

    @Override public void onClick(final View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(v, (CodiModel) v.getTag());
        }
    }

    public void add(CodiModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void setHeader(String name,String price,String brand,String productNum,boolean isFavorite) {
        mHeaderNameText = name;
        mHeaderPriceText = price;
        mHeaderBrandText = brand;
        mHeaderProductNumText = productNum;
        mIsFavorite = isFavorite;
    }

    protected static class ViewHolderItem extends RecyclerView.ViewHolder {

        public ViewHolderItem(DetailProductView itemView) {
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

    public interface OnItemClickListener {

        void onItemClick(View view, CodiModel item);

    }
}
