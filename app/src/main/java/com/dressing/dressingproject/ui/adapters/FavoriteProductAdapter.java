package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.MainActivity;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.CheckItemView;
import com.dressing.dressingproject.ui.widget.FavoriteProductModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteProductAdapter extends RecyclerViewBaseAdapter implements FavoriteProductModelView.OnItemClickListener
{
    private final Context mContext;
    SparseBooleanArray checkedItems = new SparseBooleanArray();
    private List<ProductModel> items = new ArrayList<ProductModel>();

    public interface OnAdapterItemListener {
        public void onAdapterItemClick(FavoriteProductAdapter adapter, View view, ProductModel ProductModel,int position);
    }

    OnAdapterItemListener mListener;
    public void setOnAdapterItemListener(OnAdapterItemListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        FavoriteProductModelView view = new FavoriteProductModelView(parent.getContext());
        return new ViewHolderItem(view);
    }

    public FavoriteProductAdapter(Context context) {
        mContext =context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {


        final ProductModel item = items.get(position);
        ((FavoriteProductModelView)holder.itemView).setProductItem(item);
        ((FavoriteProductModelView)holder.itemView).setPosition(position);
        ((FavoriteProductModelView)holder.itemView).setOnItemClickListener(this);
        ((FavoriteProductModelView)holder.itemView).setChecked(checkedItems.get(position));
        ((FavoriteProductModelView)holder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, item,position);
            }
        });

    }

    @Override public int getItemCount() {
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
    public void onItemClick(View view, ProductModel productModel,int position) {
        boolean checked = !checkedItems.get(position);
        setItemCheck(position, checked);
//        notifyDataSetChanged();

        if (mListener != null) {
            mListener.onAdapterItemClick(this, view, productModel,position);
        }
    }

    private void setItemCheck(int position, boolean checked) {
        if(checkedItems.get(position) == true)
        {
            checkedItems.put(position,false);
        }
        else checkedItems.put(position,true);

        notifyDataSetChanged();

        //true 하나이상 선택되어 있다면 버튼 활성화
        if(getCheckedItems().size() >0)
        {
            ((MainActivity)mContext).setVisibleFittingBtn(View.VISIBLE);
        }
        //버튼 비활성화
        else
        {
            ((MainActivity)mContext).setVisibleFittingBtn(View.GONE);
        }
    }

    public ArrayList<ProductModel> getCheckedItems()
    {
        ArrayList<ProductModel> models = new ArrayList<ProductModel>();
        if (checkedItems.size() != 0) {
            for (int i = items.size() -1; i > -1 ; i--) {
                if (checkedItems.get(i)) {
                    models.add(items.get(i));
                }
            }
        }
        return models;
    }


    protected static class ViewHolderItem extends RecyclerView.ViewHolder {

        CheckItemView itemView;

        public ViewHolderItem(FavoriteProductModelView itemView) {
            super(itemView);

        }

    }
}