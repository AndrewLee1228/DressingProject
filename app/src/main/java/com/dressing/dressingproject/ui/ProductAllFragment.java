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
import com.dressing.dressingproject.ui.adapters.ProductBasicAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.SimpleRecyclerAdapter;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.FavoriteResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductSearchResult;
import com.dressing.dressingproject.ui.models.SearchItem;
import com.dressing.dressingproject.ui.models.SucessResult;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductAllFragment extends Fragment implements SimpleRecyclerAdapter.OnItemClickListener {

    ProductBasicAllRecyclerAdapter mAdapter;
    private ProgressWheel mProgressWheel;

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

        mProgressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);

        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_all_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        //어뎁터 바인딩
        mAdapter = new ProductBasicAllRecyclerAdapter();
        mAdapter.setOnAdapterItemListener(new ProductBasicAllRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(ProductBasicAllRecyclerAdapter adapter,final View view, ProductModel productModel, int position) {
                switch (view.getId())
                {
                    case R.id.item_search_product_favorite:
                        //찜하기 해제
                        if(productModel.isFavorite())
                        {

                            NetworkManager.getInstance().requestDeleteFavorite(getContext(), productModel, null, new NetworkManager.OnResultListener<SucessResult>() {

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
                            NetworkManager.getInstance().requestPostProductFavorite(getContext(), productModel, new NetworkManager.OnResultListener<FavoriteResult>() {

                                @Override
                                public void onSuccess(FavoriteResult favoriteResult) {
                                    //찜하기 요청이 정삭적으로 처리 되었으므로
                                    //뷰의 셀렉트 상태를 변경한다.
                                    view.setSelected(true);
                                    AndroidUtilities.MakeFavoriteToast(getActivity());
                                }

                                @Override
                                public void onFail(int code) {
                                    //찜하기 요청 실패
                                }

                            });
                        }
                        break;
                    case R.id.item_search_product_map:
                        Toast.makeText(getContext(), "지도열기", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        SearchItem searchItem = new SearchItem();
        searchItem.brandNum = "";

        mProgressWheel.setVisibility(View.VISIBLE);

        //네트워크 데이터요청
        NetworkManager.getInstance().requestGetSearchProduct(getContext(), searchItem ,new NetworkManager.OnResultListener<ProductSearchResult>() {

            @Override
            public void onSuccess(ProductSearchResult result)
            {

                mProgressWheel.setVisibility(View.GONE);

                if(result.code == 200 && result.msg.equals("Success"))
                {
                    mAdapter.addList(result.list);
                }
                else
                {
                    Toast.makeText(getActivity(), "상품 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }

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
