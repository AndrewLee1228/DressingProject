package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.FavoriteCodiAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.FavoriteCodiResult;
import com.dressing.dressingproject.ui.models.FitDeleteResult;
import com.dressing.dressingproject.ui.models.FitModel;
import com.dressing.dressingproject.ui.models.FitResult;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteCodiFragment extends Fragment
{

    private View mView;
    private FavoriteCodiAdapter mFavoriteCodiAdapter;
    private ProgressWheel mProgressWheel;

    public static FavoriteCodiFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteCodiFragment fragment = new FavoriteCodiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FavoriteCodiAdapter getAdapter()
    {
        return mFavoriteCodiAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_codi_favorite,container,false);
        initRecyclerView();

        mProgressWheel = (ProgressWheel)mView.findViewById(R.id.progress_wheel);

        return mView;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_favorite_codi_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mFavoriteCodiAdapter = new FavoriteCodiAdapter(getContext());
        mFavoriteCodiAdapter.setOnAdapterItemListener(new FavoriteCodiAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(FavoriteCodiAdapter adapter, final View view, CodiModel codiModel, int position) {
                if(!codiModel.isFit())
                {
                    codiModel.setFit(!codiModel.isFit());
                    NetworkManager.getInstance().requestGetFitCodi(getContext(), codiModel, new NetworkManager.OnResultListener<FitResult>() {
                        @Override
                        public void onSuccess(FitResult result) {
                            if (result.isFit()) {
                                //Toast.makeText(getContext(), "Fit!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //Toast.makeText(getContext(), "Fit 해제!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(int code) {

                        }
                    });
                }
                else
                {
                    codiModel.setFit(!codiModel.isFit());
                    FitModel fitModel = new FitModel();
                    fitModel.setFlag(FitModel.FIT_CODI);
                    fitModel.fittingNum = Integer.parseInt(codiModel.getCodiNum());
                    NetworkManager.getInstance().requestDeleteFit(getContext(), fitModel, new NetworkManager.OnResultListener<FitDeleteResult>() {
                        @Override
                        public void onSuccess(FitDeleteResult result) {
                            if (result.code ==200) {
                                //Toast.makeText(getContext(), "Fit!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //Toast.makeText(getContext(), "Fit 해제!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(int code) {

                        }
                    });
                }

            }
        });

        recyclerView.setAdapter(mFavoriteCodiAdapter);



        request(false);


    }

    public void request(final boolean clear) {
        //코디찜 목록 로드
        NetworkManager.getInstance().requestGetFavoriteCodi(getContext(), new NetworkManager.OnResultListener<FavoriteCodiResult>() {

            @Override
            public void onSuccess(FavoriteCodiResult result) {
                if (result.code == 200) {
                    if(clear)mFavoriteCodiAdapter.Clear();
                    mFavoriteCodiAdapter.addList(result.items);
                    mProgressWheel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(int code) {

            }
        });
    }
}
