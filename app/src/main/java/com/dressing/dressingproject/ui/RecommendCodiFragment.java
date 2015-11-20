package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.RecommendCodiAdapter;
import com.dressing.dressingproject.ui.models.FavoriteResult;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.RecommendCodiResult;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.util.AndroidUtilities;

/**
 * Created by lee on 15. 11. 3.
 * 코디추천 프래그먼트
 */
public class RecommendCodiFragment extends Fragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private RecommendCodiAdapter mAdapter;

    public RecommendCodiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recommend_codi,container,false);

        initRecyclerView();

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(mRecyclerView);
//        }

        return mView;
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_recommend_codi_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }


    private void setRecyclerAdapter(RecyclerView recyclerView) {

        mAdapter = new RecommendCodiAdapter();

        mAdapter.setOnAdapterItemListener(new RecommendCodiAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(RecommendCodiAdapter adapter,final View view, CodiModel codiModel, int position) {
                switch (view.getId())
                {
                    //item favorite click
                    //코디
                    case R.id.item_detail_product_view_image_favorite:
                        //찜하기 해제
                        if(codiModel.isFavorite())
                        {

                            NetworkManager.getInstance().requestDeleteFavorite(getActivity(), null, codiModel, new NetworkManager.OnResultListener<SucessResult>() {

                                @Override
                                public void onSuccess(SucessResult sucessResult) {
                                    //찜하기 해제 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(false);
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
                        ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.newInstance(Float.parseFloat(codiModel.getUserScore()));
                        scoreDialogFragment.setData(codiModel,adapter,position);
                        scoreDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                        break;
                    case R.id.item_detail_product_view_img:
                        Intent intent = new Intent(getContext(),DetailCodiActivity.class);
                        startActivity(intent);
                        break;
                }
            }

        });

        recyclerView.setAdapter(mAdapter);

        //개별상품로딩
        NetworkManager.getInstance().requestGetRecommendCodi(getContext(), new NetworkManager.OnResultListener<RecommendCodiResult>() {
            @Override
            public void onSuccess(RecommendCodiResult result) {
//                for (CodiModel item : result.items) {
//                    mDetailProductAdapter.add(item);
//                }
//
//
//                mAdapter.addList(result.list);
            }

            @Override
            public void onFail(int code)
            {

            }
        });
    }
}
