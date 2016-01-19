package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.RecommendCodiAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.FavoriteResult;
import com.dressing.dressingproject.ui.models.RecommendCodiResult;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 3.
 * 코디추천 프래그먼트
 */
public class RecommendCodiFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private RecommendCodiAdapter mAdapter;
    private ProgressWheel mProgressWheel;
    private boolean isUpdate;
    private SwipeRefreshLayout mRefreshLayout;
    private boolean isLastItem;
    private GridLayoutManager mGridLayoutManager;

    public RecommendCodiFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recommend_codi,container,false);

        mProgressWheel = (ProgressWheel) mView.findViewById(R.id.progress_wheel);

        mRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.main_blue));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.Clear();
                networkRequest(0,5);
            }
        });

        initRecyclerView();
        setRecyclerAdapter(mRecyclerView);
        return mView;
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_recommend_codi_recycler);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }


    private void setRecyclerAdapter(RecyclerView recyclerView) {

        mAdapter = new RecommendCodiAdapter(RecommendCodiAdapter.FLAG_STYLE_RECOMMEND);

        mAdapter.setOnAdapterItemListener(new RecommendCodiAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(RecommendCodiAdapter adapter,final View view, final CodiModel codiModel, int position) {
                switch (view.getId())
                {
                    //item favorite click
                    //코디
                    case R.id.item_detail_product_view_image_favorite:
                        //찜하기 해제
                        if(codiModel.isFavorite())
                        {

                            NetworkManager.getInstance().requestDeleteFavorite(getActivity(),false, null, codiModel, new NetworkManager.OnResultListener<SucessResult>() {

                                @Override
                                public void onSuccess(SucessResult sucessResult) {
                                    //찜하기 해제 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(false);
                                    codiModel.setIsFavorite(false);
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        //찜하기
                        else
                        {
                            NetworkManager.getInstance().requestPostCodiFavorite(getContext(), codiModel, new NetworkManager.OnResultListener<FavoriteResult>() {

                                @Override
                                public void onSuccess(FavoriteResult favoriteResult) {
                                    //찜하기 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(true);
                                    codiModel.setIsFavorite(true);
                                    AndroidUtilities.MakeFavoriteToast(getContext());
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        break;
                    case R.id.item_recommend_view_frame_layout:
                        float value;
                        if (codiModel.isRated()) {
                            value = Float.parseFloat(codiModel.getEstimationScore());
                        }
                        else
                        {
                            value = Float.parseFloat(codiModel.getForeseeScore());
                        }
                        ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.newInstance(value);
                        scoreDialogFragment.setData(codiModel,adapter,position);
                        scoreDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                        break;
                    case R.id.item_detail_product_view_img:
                        Intent intent = new Intent(getContext(),DetailCodiActivity.class);
                        intent.putExtra("CodiModel",codiModel);
                        startActivity(intent);
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
                int totalItemCount = mGridLayoutManager.getItemCount();
                int lastVisibleItemPosition = mGridLayoutManager.findLastCompletelyVisibleItemPosition();
                if (totalItemCount > 0 && lastVisibleItemPosition != RecyclerView.NO_POSITION && (totalItemCount - 1 <= lastVisibleItemPosition)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });

        recyclerView.setAdapter(mAdapter);

        mProgressWheel.setVisibility(View.VISIBLE);

        mAdapter.Clear();
        networkRequest(0,5);

    }

    private void getMoreItem() {
        //로딩 UI 보여주기
        if (!isUpdate) {
            int startIndex = mAdapter.getStartIndex();
            if (startIndex != -1) {
                isUpdate = true;
                networkRequest(startIndex,10);
            }
        }
    }

    private void networkRequest(int startIndex, int display) {
        mRefreshLayout.setRefreshing(true);
        //개별상품로딩
        NetworkManager.getInstance().requestGetRecommendCodi(getContext(),startIndex,display , new NetworkManager.OnResultListener<RecommendCodiResult>() {
            @Override
            public void onSuccess(RecommendCodiResult result)
            {
                if(result.code == 200)
                {
                    ArrayList<CodiModel> list = result.list;
                    if(list.size() > 0)
                    {
                        mAdapter.addList(result.list);
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
                else
                {
                    Toast.makeText(getContext(), "네트워크 연결을 확인해 주세요. ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(int code)
            {

            }
        });
    }
}
