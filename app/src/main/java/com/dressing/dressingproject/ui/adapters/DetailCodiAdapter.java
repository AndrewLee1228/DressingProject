package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 5.
 */
public class DetailCodiAdapter extends RecyclerView.Adapter<DetailCodiAdapter.ViewHolder> implements View.OnClickListener {

    private List<ProductModel> items = new ArrayList<ProductModel>();
    private OnItemClickListener onItemClickListener;

    public DetailCodiAdapter() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_codi_view, parent, false);
        ImageView productView = (ImageView) v.findViewById(R.id.item_detail_codi_view_image);
        productView.setOnClickListener(this);
        ImageView favoriteView = (ImageView) v.findViewById(R.id.item_detail_codi_view_image_favorite);
        favoriteView.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ProductModel item = items.get(position);
//        holder.productView.setImageBitmap(item.getProductImgURL());
        holder.productView.setImageResource(Integer.parseInt(item.getProductImgURL()));
        holder.productName.setText(item.getProdutcName());
        holder.productPrice.setText(item.getProductPrice());
        //커스컴뷰로만들자
//        holder.productFavoriteView.setImageBitmap();
//        Picasso.with(holder.image.getContext()).load(item.getImage()).into(holder.image);
        holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return items.size();
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

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productView;
        public TextView productName;
        public TextView productPrice;
        public ImageView productFavoriteView;

        public ViewHolder(View itemView) {
            super(itemView);
            productView = (ImageView) itemView.findViewById(R.id.item_detail_codi_view_image);
            productName = (TextView) itemView.findViewById(R.id.item_detail_codi_view_image_text);
            productPrice = (TextView) itemView.findViewById(R.id.item_detail_codi_view_image_price_text);
            productFavoriteView = (ImageView) itemView.findViewById(R.id.item_detail_codi_view_image_favorite);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ProductModel item);

    }
}
