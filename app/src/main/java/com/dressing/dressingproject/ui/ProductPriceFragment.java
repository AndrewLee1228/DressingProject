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
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.ProductBasicAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.ProductBasicHeaderRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.SimpleRecyclerAdapter;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductSearchResult;
import com.dressing.dressingproject.ui.models.SearchItem;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductPriceFragment extends Fragment implements SimpleRecyclerAdapter.OnItemClickListener {

    ProductBasicHeaderRecyclerAdapter mAdapter;
    private String mOldStart="";
    private String mOldEnd="";
    private ProgressWheel mProgressWheel;

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

        mProgressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);

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
                    case R.id.price_headerview_rangebar:

                        /**
                         * Rangebar 뷰에서 추출한 price값을 세팅하여 네트워크 요청!
                         */
                        //브랜드 필터를 세팅하여 네트워크 요청 보내기!
                        SearchItem searchItem = new SearchItem();
                        RangeBar rangeBar = (RangeBar) view.getTag();
                        searchItem.priceStart = Integer.toString(rangeBar.getLeftIndex()*2)+"0000";
                        searchItem.priceEnd = Integer.toString(rangeBar.getRightIndex()*2)+"0000";

                        //이전 검색 범위와 다를 경우에만 새로운 요청을 보낸다.
                        if(!mOldStart.equals(searchItem.priceStart)|| !mOldEnd.equals(searchItem.priceEnd))
                        {
                            mOldStart = searchItem.priceStart;
                            mOldEnd = searchItem.priceEnd;
                            Toast.makeText(getActivity(), "네트워크 요청 : 시작="+searchItem.priceStart+", 끝 ="+searchItem.priceEnd , Toast.LENGTH_SHORT).show();

                            //Wheel progress visible
                            mProgressWheel.setVisibility(View.VISIBLE);

                            NetworkManager.getInstance().requestGetSearchProduct(getContext(), searchItem, new NetworkManager.OnResultListener<ProductSearchResult>() {

                                @Override
                                public void onSuccess(ProductSearchResult result) {
                                    if (result.code == 200 && result.msg.equals("Success")) {
                                        mProgressWheel.setVisibility(View.GONE);
                                        mAdapter.Clear();
                                        mAdapter.addList(result.list);

                                    } else {
                                        Toast.makeText(getContext(), "상품 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFail(int code) {

                                }

                            });
                        }


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
