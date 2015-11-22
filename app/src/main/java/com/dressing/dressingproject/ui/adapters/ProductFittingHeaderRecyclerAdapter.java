package com.dressing.dressingproject.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.widget.BaseSearchModelFrameLayout;
import com.dressing.dressingproject.ui.widget.ProductFittingHeaderView;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by lee on 15. 11. 19.
 */
public class ProductFittingHeaderRecyclerAdapter extends ProductBasicAllRecyclerAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface
{

    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_HEADER_FITTING = 4;
    private int mHeaderFlag;
    private boolean mVisiblewHeader;
    private RecyclerView.ViewHolder mHeaderHolder;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType)
        {

            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitting_product, parent, false);
                return new ProductFittingViewHolder(view);
            case TYPE_HEADER_FITTING:
                view = new ProductFittingHeaderView(parent.getContext());
                return new ProductFittingHeaderViewHolder((BaseSearchModelFrameLayout) view);


        }

        throw new RuntimeException("there is no type that matches the type " + viewType);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if(holder instanceof ProductFittingViewHolder)
        {
            ProductModel item = items.get(position-1);
            ((ProductFittingViewHolder)holder).setProductItem(item);
            ((ProductFittingViewHolder)holder).mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

            // Drag From Right
            ((ProductFittingViewHolder)holder).mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, ((ProductFittingViewHolder) holder).mSwipeLayout.findViewById(R.id.item_fitting_product_bottom_wrapper));

            // Handling different events when swiping
            ((ProductFittingViewHolder)holder).mSwipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    //you are swiping.
                }

                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //when the BottomView totally show.
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });


            ((ProductFittingViewHolder)holder).mTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemManger.removeShownLayouts(((ProductFittingViewHolder) holder).mSwipeLayout);
                    items.remove(position - 1);
                    notifyDataSetChanged();
                    //헤더가 보이는지 검사!
                    //보이면 가격변화 세팅
                    if(mVisiblewHeader)
                    {
                        ((ProductFittingHeaderView)mHeaderHolder.itemView).SetPrice(GetTotalPrice());
                    }

//                    notifyItemRemoved(position - 1);
//                    notifyItemRangeChanged(position - 1, items.size()-1);
                        mItemManger.closeAllItems();

                }
            });
//             mItemManger is member in RecyclerSwipeAdapter Class

            mItemManger.bindView(((ProductFittingViewHolder)holder).itemView, position-1);

        }
        else if(holder instanceof ProductFittingHeaderViewHolder)
        {
            ((ProductFittingHeaderView)holder.itemView).setOnItemClickListener(this);
            ((ProductFittingHeaderView)holder.itemView).SetPrice(GetTotalPrice());
        }
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if(holder instanceof ProductFittingHeaderViewHolder)
        {
            mVisiblewHeader = true;
            mHeaderHolder =holder;
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(holder instanceof ProductFittingHeaderViewHolder)
        {
            mVisiblewHeader = false;
        }
    }

    public int GetTotalPrice()
    {
        int totalPrice = 0;
        for(ProductModel productModel : items)
        {
            totalPrice += Integer.parseInt(productModel.getProductPrice());
        }
        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if(isPositionHeader(position) ==true)
        {
            return mHeaderFlag;
        }
        else return TYPE_ITEM;

    }

    public boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setHeaderFlag(int headerFlag) {
        mHeaderFlag = headerFlag;
    }

    public void Clear() {
        items.clear();
        notifyDataSetChanged();
    }


    class ProductFittingHeaderViewHolder extends RecyclerView.ViewHolder {
        public ProductFittingHeaderViewHolder(BaseSearchModelFrameLayout productSearchHeaderView) {
            super(productSearchHeaderView);
        }
    }

    class ProductFittingViewHolder extends RecyclerView.ViewHolder
    {

        private final View mItemView;
        private ProductModel mItem;
        private ImageView mProductImg;
        private TextView mNameText;
        private TextView mPriceText;
        private TextView mNumText;
        private TextView mLocationText;
        private ImageView mMapImg;
        private ImageView mLogoImg;
        public SwipeLayout mSwipeLayout;
        public TextView mTvDelete;

        public ProductFittingViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mProductImg =(ImageView) itemView.findViewById(R.id.item_fitting_product_img);

            mNameText =(TextView) itemView.findViewById(R.id.item_fitting_product_name_text);
            mPriceText =(TextView)itemView.findViewById(R.id.item_fitting_product_price_text);
            mNumText =(TextView)itemView.findViewById(R.id.item_fitting_product_num_text);
            mLocationText=(TextView)itemView.findViewById(R.id.item_fitting_product_location_text);

            mMapImg =(ImageView)itemView.findViewById(R.id.item_fitting_product_map);
            mLogoImg =(ImageView)itemView.findViewById(R.id.item_fitting_product_logo);


            mSwipeLayout = (SwipeLayout)itemView.findViewById(R.id.item_fitting_product_swipe);
            mTvDelete = (TextView) itemView.findViewById(R.id.item_fitting_product_tvDelete);

        }



        public void setProductItem(ProductModel item) {
            mItem = item;
            mNameText.setText(item.getProductName());
            /**
             * java에서 원화 표시하기
             * Currency.getInstance(Locale.KOREA).getSymbol()
             * 여기서 Locale 설정을 바꾸면 해당 나라의 통화 심볼을 얻을 수 있습니다.
             */
            mPriceText.setText(String.format("가격 : %s %,d", Currency.getInstance(Locale.KOREA).getSymbol(), Integer.parseInt(item.getProductPrice())));
            mNumText.setText(String.format("제품번호 : %s",item.getProductNum()));
            mLocationText.setText(String.format("위치 : %s",item.getMallName()));

            //상품이미지 로드
            Glide.with(mItemView.getContext())
                    .load(Integer.parseInt(item.getProductImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                    .crossFade()
                    .thumbnail(0.1f)
                    .into(mProductImg);

            //상품로고 이미지 로드
            Glide.with(mItemView.getContext())
                    .load(R.mipmap.ic_launcher)
//                .load(Integer.parseInt(item.getProductLogoImgURL()))
//                .centerCrop()
//                .placeholder(android.R.drawable.progress_horizontal)
                    .crossFade()
                    .thumbnail(0.1f)
                    .into(mLogoImg);
        }

    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.item_fitting_product_swipe;
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }
}
