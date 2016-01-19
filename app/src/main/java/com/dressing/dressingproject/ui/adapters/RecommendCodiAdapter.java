/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.widget.BaseModelFrameLayout;
import com.dressing.dressingproject.ui.widget.CodiModelModifyView;
import com.dressing.dressingproject.ui.widget.CodiModelView;

import java.util.ArrayList;
import java.util.List;

public class RecommendCodiAdapter extends RecyclerViewBaseAdapter implements BaseModelFrameLayout.OnItemClickListener{

    public static final int FLAG_STYLE_MODIFY = 0;
    public static final int FLAG_STYLE_RECOMMEND = 1;
    private final int mFlag;
    private List<CodiModel> items = new ArrayList<CodiModel>();


    public RecommendCodiAdapter(int flag) {
        mFlag = flag;
    }

    public void Clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public int getStartIndex() {
        return items.size()+1;
    }


    public interface OnAdapterItemListener {
        public void onAdapterItemClick(RecommendCodiAdapter adapter, View view, CodiModel codiModel,int position);
    }

    OnAdapterItemListener mListener;
    public void setOnAdapterItemListener(OnAdapterItemListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mFlag == RecommendCodiAdapter.FLAG_STYLE_MODIFY) {
            CodiModelModifyView view = new CodiModelModifyView(parent.getContext());
            return new ViewHolderItem(view);
        }
        //FLAG_STYLE_RECOMMEND
        else
        {
            CodiModelView view = new CodiModelView(parent.getContext());
            return new ViewHolderItem(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (mFlag == RecommendCodiAdapter.FLAG_STYLE_MODIFY) {
            CodiModel item = items.get(position);
            ((CodiModelModifyView)holder.itemView).setCodiItem(item);
            ((CodiModelModifyView)holder.itemView).setPosition(position);
            ((CodiModelModifyView)holder.itemView).setOnItemClickListener(this);
        }
        //FLAG_STYLE_RECOMMEND
        else
        {
            CodiModel item = items.get(position);
            ((CodiModelView)holder.itemView).setCodiItem(item);
            ((CodiModelView)holder.itemView).setPosition(position);
            ((CodiModelView)holder.itemView).setOnItemClickListener(this);
        }

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
        if (mListener != null) {
            mListener.onAdapterItemClick(this, view, codiModel,position);
        }
    }

    protected static class ViewHolderItem extends RecyclerView.ViewHolder {

        public ViewHolderItem(CodiModelView itemView) {
            super(itemView);
        }

        public ViewHolderItem(CodiModelModifyView itemView) {
            super(itemView);
        }
    }

}
