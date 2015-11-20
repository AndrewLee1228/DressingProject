package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.daimajia.swipe.util.Attributes;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.ProductBasicAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.ProductFittingHeaderRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.SimpleDividerItemDecoration;
import com.dressing.dressingproject.ui.models.ProductModel;

/**
 * Created by lee on 15. 11. 2.
 */

public class FittingFragment extends Fragment {

    private ProductFittingHeaderRecyclerAdapter mAdapter;

    public FittingFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitting,container,false);

        //Fragment 배경 세팅
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fragment_fitting_dummyfrag_bg);
        frameLayout.setBackgroundColor(0xFFFFFF);

        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_fitting_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
//        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        mAdapter = new ProductFittingHeaderRecyclerAdapter();
        mAdapter.setHeaderFlag(ProductFittingHeaderRecyclerAdapter.TYPE_HEADER_FITTING);

        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((ProductFittingHeaderRecyclerAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mAdapter.setOnAdapterItemListener(new ProductBasicAllRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(ProductBasicAllRecyclerAdapter adapter, View view, ProductModel productModel, int position) {
                switch (view.getId()) {
                    case R.id.item_product_fitting_search_btn:
                        //네트워크 데이터요청
//                        NetworkManager.getInstance().requestGetDetailCodi(getContext(), new NetworkManager.OnResultListener<ProductResult>() {
//
//                            @Override
//                            public void onSuccess(ProductResult result) {
////                                mAdapter.addList(result.items);
//                            }
//
//                            @Override
//                            public void onFail(int code) {
//
//                            }
//                        });
                        break;
                    case R.id.item_fitting_product_map:
                        Log.i("Tast : ", Integer.toString(position-1));
                        break;
                }
            }
        });

        // Adapter
        recyclerView.setAdapter(mAdapter);

        mAdapter.addList(NetworkManager.getFavoriteProductItemsList().items);


        return view;
    }

}
