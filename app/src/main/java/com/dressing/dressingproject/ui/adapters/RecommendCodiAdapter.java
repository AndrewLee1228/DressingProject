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
import com.dressing.dressingproject.ui.widget.CodiModelView;

import java.util.ArrayList;
import java.util.List;

public class RecommendCodiAdapter extends RecyclerViewBaseAdapter implements BaseModelFrameLayout.OnItemClickListener{

    private List<CodiModel> items = new ArrayList<CodiModel>();

    public RecommendCodiAdapter() {
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

            CodiModelView view = new CodiModelView(parent.getContext());
            return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

            CodiModel item = items.get(position);
            ((CodiModelView)holder.itemView).setCodiItem(item);
            ((CodiModelView)holder.itemView).setPosition(position);
            ((CodiModelView)holder.itemView).setOnItemClickListener(this);

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
    }

}
