package com.dressing.dressingproject.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.FavoriteProductAdapter;
import com.dressing.dressingproject.ui.models.FavoriteProductResult;
import com.dressing.dressingproject.ui.models.FitResult;
import com.dressing.dressingproject.ui.models.ProductModel;

/**
 * Created by lee on 15. 11. 16.
 */
public class FavoriteProductFragment extends Fragment
{

    private View mView;
    private FavoriteProductAdapter mFavoriteProductAdapter;

    public static FavoriteProductFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteProductFragment fragment = new FavoriteProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_favorite,container,false);
        initRecyclerView();
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).setVisibleFittingBtn(View.GONE);
//        if(mFavoriteProductAdapter !=null &&mFavoriteProductAdapter.getCheckedItems().size() >0)
//        {
//            ((MainActivity)getActivity()).setVisibleFittingBtn(View.VISIBLE);
//        }
    }

    public FavoriteProductAdapter getAdapter()
    {
        return mFavoriteProductAdapter;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_favorite_product_recyclerview);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        mFavoriteProductAdapter = new FavoriteProductAdapter(getContext());
        mFavoriteProductAdapter.setOnAdapterItemListener(new FavoriteProductAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(FavoriteProductAdapter adapter, View view, ProductModel productModel, int position) {
                productModel.setFit(!productModel.isFit());
                NetworkManager.getInstance().requestGetFitProduct(getContext(), productModel, new NetworkManager.OnResultListener<FitResult>() {
                    @Override
                    public void onSuccess(FitResult result) {
                        if (result.isFit()) {
                            Toast.makeText(getActivity(), "Fit!", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "Fit 해제!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(int code) {

                    }
                });
            }
        });

        recyclerView.setAdapter(mFavoriteProductAdapter);

        // apply animation
         recyclerView.setItemAnimator(null);

        //상품찜 목록 로드
        NetworkManager.getInstance().requestGetFavoriteProduct(getContext(), new NetworkManager.OnResultListener<FavoriteProductResult>() {

            @Override
            public void onSuccess(FavoriteProductResult result) {
                mFavoriteProductAdapter.addList(result.items);
            }

            @Override
            public void onFail(int code) {

            }
        });


    }
}