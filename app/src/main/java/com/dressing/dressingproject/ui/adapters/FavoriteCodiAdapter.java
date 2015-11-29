package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.MainActivity;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.widget.BaseModelFrameLayout;
import com.dressing.dressingproject.ui.widget.CheckItemView;
import com.dressing.dressingproject.ui.widget.FavoriteCodiModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteCodiAdapter extends RecyclerViewBaseAdapter implements BaseModelFrameLayout.OnItemClickListener
{
    private final Context mContext;
    SparseBooleanArray checkedItems = new SparseBooleanArray();
    private List<CodiModel> items = new ArrayList<CodiModel>();

    public interface OnAdapterItemListener {
        public void onAdapterItemClick(FavoriteCodiAdapter adapter, View view, CodiModel codiModel,int position);
    }

    OnAdapterItemListener mListener;
    public void setOnAdapterItemListener(OnAdapterItemListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        FavoriteCodiModelView view = new FavoriteCodiModelView(parent.getContext());
        return new ViewHolderItem(view);
    }

    public FavoriteCodiAdapter(Context context) {
        mContext =context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {


        final CodiModel item = items.get(position);
        ((FavoriteCodiModelView)holder.itemView).setCodiItem(this,checkedItems,position,item);
        ((FavoriteCodiModelView)holder.itemView).setPosition(position);
        ((FavoriteCodiModelView)holder.itemView).setOnItemClickListener(this);
        ((FavoriteCodiModelView)holder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, item,position);
            }
        });

    }

    @Override public int getItemCount() {
        return items.size();
    }

    public void add(CodiModel item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<CodiModel> itemlist) {
        items.addAll(itemlist);
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, CodiModel codiModel,int position) {
        boolean checked = !checkedItems.get(position);
        setItemCheck(position, checked);
        notifyDataSetChanged();

        if (mListener != null) {
            mListener.onAdapterItemClick(this, view, codiModel,position);
        }
    }

    private void setItemCheck(int position, boolean checked) {
        if(checkedItems.get(position) == true)
        {
            checkedItems.put(position,false);
        }
        else checkedItems.put(position,true);

//        notifyDataSetChanged();
        checkItems();

    }

    public void checkItems()
    {
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

    public ArrayList<CodiModel> getCheckedItems()
    {
        ArrayList<CodiModel> models = new ArrayList<CodiModel>();
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

        public ViewHolderItem(FavoriteCodiModelView itemView) {
            super(itemView);

        }

    }
}
