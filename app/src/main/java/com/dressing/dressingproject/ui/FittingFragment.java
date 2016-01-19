package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.dressing.dressingproject.ui.models.FittingListResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by lee on 15. 11. 2.
 */

public class FittingFragment extends Fragment {

    private ProductFittingHeaderRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isLastItem;
    private boolean isUpdate;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressWheel mProgressWheel;

    public FittingFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitting,container,false);

        //Fragment 배경 세팅
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fragment_fitting_dummyfrag_bg);
        frameLayout.setBackgroundColor(0xFFFFFF);

        mProgressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);

        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_blue));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.Clear();
                requestFittingList(0,10);
            }
        });

        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_fitting_recyclerview);

        //레이아웃 매니저
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
//        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(),SimpleDividerItemDecoration.FITTING));

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

                        break;
                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLinearLayoutManager.getItemCount();
                int lastVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItemCount > 0 && lastVisibleItemPosition != RecyclerView.NO_POSITION && (totalItemCount - 1 <= lastVisibleItemPosition)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });


        // Adapter
        recyclerView.setAdapter(mAdapter);
        mAdapter.Clear();


        requestFittingList(0,10);

        return view;
    }

    private void getMoreItem() {
        //로딩 UI 보여주기
        if (!isUpdate) {
            int startIndex = mAdapter.getStartIndex();
            if (startIndex != -1) {
                isUpdate = true;
                requestFittingList(startIndex,10);
            }
        }
    }

    private void requestFittingList(int startIndex , int display) {
        mRefreshLayout.setRefreshing(true);
        NetworkManager.getInstance().requestGetFitting(getContext(),startIndex,display ,new NetworkManager.OnResultListener<FittingListResult>() {
            @Override
            public void onSuccess(FittingListResult result) {
                if (result.code == 200) {
                    int size = result.list.size();
                    if(size >0 )
                    {
                        mAdapter.addFitList(result.list);
                        mAdapter.setTotalPrice(result.totalPrice);
                    }
                    mProgressWheel.setVisibility(View.GONE);

                    isUpdate = false;
                    mRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            }

            @Override
            public void onFail(int code) {
                isUpdate = false;
            }
        });
    }

}
