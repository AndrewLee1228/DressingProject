package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.ProductSearchAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.SimpleRecyclerAdapter;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.CodiResult;
import com.dressing.dressingproject.ui.models.ProductFavoriteResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.util.AndroidUtilities;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductAllFragment extends Fragment implements SimpleRecyclerAdapter.OnItemClickListener {

    ProductSearchAllRecyclerAdapter mAdapter;

    public static ProductAllFragment newInstance(ArrayList<CategoryModel> categoryModels,ArrayList<CategoryModel> subCategoryModels) {

        ProductAllFragment fragment = new ProductAllFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoryModels", categoryModels);
        args.putSerializable("subCategoryModels",subCategoryModels);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("categoryModels");
        ArrayList<CategoryModel> subCategoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("subCategoryModels");

        Toast.makeText(getActivity(), "category : " + categoryModels.get(0).getCategoryText() + "\n sub category : " + Integer.toString(subCategoryModels.size()), Toast.LENGTH_SHORT).show();

        View view = inflater.inflate(R.layout.fragment_product_all,container,false);


        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_all_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        //어뎁터 바인딩
        mAdapter = new ProductSearchAllRecyclerAdapter();
        mAdapter.setOnAdapterItemListener(new ProductSearchAllRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(ProductSearchAllRecyclerAdapter adapter,final View view, ProductModel productModel, int position) {
                switch (view.getId())
                {
                    case R.id.item_search_product_favorite:
                        if (view.isSelected() == false) {
                            productModel.setIsFavorite(true);
                        } else {
                            productModel.setIsFavorite(false);
                        }

                        NetworkManager.getInstance().requestUpdateProductFavorite(getContext(), productModel, new NetworkManager.OnResultListener<ProductFavoriteResult>() {

                            @Override
                            public void onSuccess(ProductFavoriteResult productFavoriteResult)
                            {
                                if (productFavoriteResult.getSelectedState())
                                {
                                    view.setSelected(true);
                                    AndroidUtilities.MakeFavoriteToast(getContext());
                                }
                                else
                                    view.setSelected(false);
                            }

                            @Override
                            public void onFail(int code) {
                                //찜하기 실패
                            }

                        });
                        break;
                    case R.id.item_search_product_map:
                        Toast.makeText(getContext(), "지도열기", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        //네트워크 데이터요청
        NetworkManager.getInstance().getNetworkDetailCodi(getContext(), new NetworkManager.OnResultListener<CodiResult>() {

            @Override
            public void onSuccess(CodiResult result) {
                mAdapter.addList(result.items);
            }

            @Override
            public void onFail(int code) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view) {
        Intent intent = new Intent(getActivity(),DetailProductActivity.class);
        startActivity(intent);
    }
}
