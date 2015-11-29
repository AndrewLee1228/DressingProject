package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.ProductBasicAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.ProductBasicHeaderRecyclerAdapter;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductSearchResult;
import com.dressing.dressingproject.ui.models.SearchItem;
import com.dressing.dressingproject.ui.widget.ProductSearchColorHeaderView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductColorFragment extends Fragment {

    ProductBasicHeaderRecyclerAdapter mAdapter;
    private ProgressWheel mProgressWheel;

    public static ProductColorFragment newInstance(ArrayList<CategoryModel> categoryModels, ArrayList<CategoryModel> subCategoryModels) {

        ProductColorFragment fragment = new ProductColorFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoryModels", categoryModels);
        args.putSerializable("subCategoryModels", subCategoryModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("categoryModels");
        ArrayList<CategoryModel> subCategoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("subCategoryModels");

        View view = inflater.inflate(R.layout.fragment_product_color, container, false);

        mProgressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);

        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_color_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        //더미데이터 어뎁터 바인딩
        mAdapter = new ProductBasicHeaderRecyclerAdapter();
        mAdapter.setHeaderFlag(ProductBasicHeaderRecyclerAdapter.TYPE_HEADER_COLOR);
        mAdapter.setOnAdapterItemListener(new ProductBasicAllRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(ProductBasicAllRecyclerAdapter adapter, View view, ProductModel productModel, int position) {
                switch (view.getId()) {
                    case R.id.rb_colorbox_red:
                    case R.id.rb_colorbox_black:
                    case R.id.rb_colorbox_blue:
                    case R.id.rb_colorbox_brown:
                    case R.id.rb_colorbox_gray:
                    case R.id.rb_colorbox_green:
                    case R.id.rb_colorbox_navy:
                    case R.id.rb_colorbox_orange:
                    case R.id.rb_colorbox_white:
                    case R.id.rb_colorbox_yellow:
                        //Is HeaderView?
                        if (mAdapter.getHeaderVisibleStatus()) {
                            /**
                             * Check 목록을 확인한다.
                             */
                            RecyclerView.ViewHolder headerViewHolder = mAdapter.getHeaderViewHolder();
                            CheckBox[] checkBoxes = ((ProductSearchColorHeaderView) headerViewHolder.itemView).getCheckBoxArray();
                            String colorHax = "";

                            for (CheckBox checkBox : checkBoxes) {
                                if (checkBox.isChecked()) {
                                    if (checkBox.getTag() != null)
                                        colorHax += "," + (String) checkBox.getTag();
                                }
                            }

                            if (!colorHax.isEmpty()) {

                                //Check값 확인 Toast message!
                                Toast.makeText(getActivity(), colorHax, Toast.LENGTH_SHORT).show();

                                /**
                                 * Check된 뷰에서 추출한 color hex 값을 세팅하여 네트워크 요청!
                                 */
                                //브랜드 필터를 세팅하여 네트워크 요청 보내기!
                                SearchItem searchItem = new SearchItem();
                                searchItem.color = "";

                                //ProgressWheel Visible
                                mProgressWheel.setVisibility(View.VISIBLE);

                                NetworkManager.getInstance().requestGetSearchProduct(getContext(), searchItem, new NetworkManager.OnResultListener<ProductSearchResult>() {

                                    @Override
                                    public void onSuccess(ProductSearchResult result) {
                                        if (result.code == 200 && result.msg.equals("Success"))
                                        {
                                            mProgressWheel.setVisibility(View.GONE);
                                            mAdapter.Clear();
                                            mAdapter.addList(result.list);

                                        } else {
                                            Toast.makeText(getActivity(), "상품 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFail(int code) {

                                    }

                                });
                            }
                            //checked 된 colorHax가 없다면 adapter Clear
                            else
                            {
                                mAdapter.Clear();
                            }


                        }
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        return view;
    }

}
