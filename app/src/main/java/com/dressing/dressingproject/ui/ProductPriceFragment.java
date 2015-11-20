package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.ProductBasicAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.ProductBasicHeaderRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.SimpleRecyclerAdapter;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.ProductModel;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductPriceFragment extends Fragment implements SimpleRecyclerAdapter.OnItemClickListener {

    ProductBasicHeaderRecyclerAdapter mAdapter;

    public static ProductPriceFragment newInstance(ArrayList<CategoryModel> categoryModels,ArrayList<CategoryModel> subCategoryModels) {

        ProductPriceFragment fragment = new ProductPriceFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoryModels", categoryModels);
        args.putSerializable("subCategoryModels",subCategoryModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("categoryModels");
        ArrayList<CategoryModel> subCategoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("subCategoryModels");

        View view = inflater.inflate(R.layout.fragment_product_price,container,false);


        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_price_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        //더미데이터 어뎁터 바인딩
        mAdapter = new ProductBasicHeaderRecyclerAdapter();
        mAdapter.setHeaderFlag(ProductBasicHeaderRecyclerAdapter.TYPE_HEADER_PRICE);
        mAdapter.setOnAdapterItemListener(new ProductBasicAllRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(ProductBasicAllRecyclerAdapter adapter, View view, ProductModel productModel, int position) {
                switch (view.getId()) {
                    case R.id.item_search_product_price_search_btn:
                        //네트워크 데이터요청
//                        NetworkManager.getInstance().requestGetDetailCodi(getContext(), new NetworkManager.OnResultListener<ProductResult>() {
//
//                            @Override
//                            public void onSuccess(ProductResult result) {
//                                mAdapter.addList(result.items);
//                            }
//
//                            @Override
//                            public void onFail(int code) {
//
//                            }
//                        });
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onItemClick(View view) {
        Intent intent = new Intent(getActivity(),DetailProductActivity.class);
        startActivity(intent);
    }
}
