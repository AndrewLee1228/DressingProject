package com.dressing.dressingproject.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.BrandListAdapter;
import com.dressing.dressingproject.ui.adapters.ProductBasicAllRecyclerAdapter;
import com.dressing.dressingproject.ui.adapters.ProductBasicHeaderRecyclerAdapter;
import com.dressing.dressingproject.ui.models.BrandListData;
import com.dressing.dressingproject.ui.models.CategoryModel;
import com.dressing.dressingproject.ui.models.Chip;
import com.dressing.dressingproject.ui.models.MallModel;
import com.dressing.dressingproject.ui.models.MallResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductSearchResult;
import com.dressing.dressingproject.ui.models.SearchItem;
import com.dressing.dressingproject.ui.widget.ProductSearchBrandHeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductBrandFragment extends Fragment {

    ProductBasicHeaderRecyclerAdapter mAdapter;
    private BrandListAdapter mBrandListAdapter;
    private ArrayList<BrandListData> mBrandListDatas;

    public static ProductBrandFragment newInstance(ArrayList<CategoryModel> categoryModels, ArrayList<CategoryModel> subCategoryModels) {
        ProductBrandFragment fragment = new ProductBrandFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoryModels", categoryModels);
        args.putSerializable("subCategoryModels", subCategoryModels);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateFilter(List<Chip> chipList) {
        ArrayList<BrandListData> brandListDatas = new ArrayList<BrandListData>();
        BrandListData brandListData;

        if (chipList.size() != 0) {
            for (Chip chip : chipList) {
                chip.getText();
                brandListData = new BrandListData(chip.getCode(), chip.getImgURL(), chip.getName(), chip.getBrandNum(), chip.getSync());
                brandListDatas.add(brandListData);
            }

            SendRequest(brandListDatas);
        }
        /**
         * ChipView가 모두 지워짐.
         */
        else
        {
            //ChipView 지우
            RecyclerView.ViewHolder headerViewHolder = mAdapter.getHeaderViewHolder();
            ((ProductSearchBrandHeaderView) headerViewHolder.itemView).adapterClear();
            //리싸이클러뷰 어뎁터 초기화
            mAdapter.Clear();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("categoryModels");
        ArrayList<CategoryModel> subCategoryModels = (ArrayList<CategoryModel>) bundle.getSerializable("subCategoryModels");


        View view = inflater.inflate(R.layout.fragment_product_brand, container, false);

        //헤더뷰 데이터 로딩
//        mBrandSelectorDialogFragment = new BrandSelectorDialogFragment();


        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_brand_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        mAdapter = new ProductBasicHeaderRecyclerAdapter();
        mAdapter.setHeaderFlag(ProductBasicHeaderRecyclerAdapter.TYPE_HEADER_BRAND);
        mAdapter.setOnAdapterItemListener(new ProductBasicAllRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(ProductBasicAllRecyclerAdapter adapter, View view, ProductModel productModel, int position) {
                switch (view.getId()) {
                    case R.id.item_search_product_brand_search_btn:

                        //브랜드 리스트 요청!
                        NetworkManager.getInstance().requestGetBrandList(getContext(), new NetworkManager.OnResultListener<MallResult>() {

                            @Override
                            public void onSuccess(MallResult result) {
                                if (result.code == 200 && result.msg.equals("Success")) {
                                    showDialog(result.mallList);
                                } else
                                    Toast.makeText(getActivity(), "브랜드 리스트 요청 실패!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFail(int code) {
                                Toast.makeText(getActivity(), "브랜드 리스트 요청 실패! \n 네트워크 상태를 확인해 주세요.", Toast.LENGTH_SHORT).show();

                            }
                        });

                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        return view;
    }


    /**
     * Brand List Call
     *
     * @param mallList
     */
    public void showDialog(ArrayList<MallModel> mallList) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View customView = LayoutInflater.from(getActivity()).inflate(R.layout.brand_list_dialog_header_layout, null, false);
        TextView tv = (TextView) customView.findViewById(R.id.brand_list_dialog_header_view);
        tv.setText("브랜드 검색");

        ArrayList<BrandListData> brandListDatas = new ArrayList<BrandListData>();
        //MallDatas -> BrandListDatas
        for (int i = 0; i < mallList.size(); i++) {
            MallModel mallitem = mallList.get(i);
            brandListDatas.add(new BrandListData(i, mallitem.brandImg, mallitem.brandName, mallitem.brandNum, false));
        }

        final ListView listView = (ListView) customView.findViewById(R.id.brand_listview);
        mBrandListAdapter = new BrandListAdapter(getActivity(), brandListDatas); // Create data mBrandListDatas

        listView.setAdapter(mBrandListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

            }
        });
        dialog.setView(customView);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO 브랜드 요청확인
                //네트워크 데이터요청
                SendRequest(mBrandListAdapter.getCheckedItems());

                //Clear checked!
                //mBrandListAdapter.ClearChecked();


            }
        });
        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
    }

    private void SendRequest(ArrayList<BrandListData> brandListDatas) {

        mBrandListDatas = brandListDatas;
        String toastMsg = "";
//        for(BrandListData item: mBrandListDatas)
//        {
//            toastMsg += ","+item.getBrandNum();
//        }

        for (BrandListData item : mBrandListDatas) {
            item.getBrandNum();
            toastMsg += "," + item.getName();
        }
        Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_SHORT).show();

        //브랜드 필터를 세팅하여 네트워크 요청 보내기!
        SearchItem searchItem = new SearchItem();
        searchItem.brandNum = "";

        NetworkManager.getInstance().requestGetSearchProduct(getContext(), searchItem, new NetworkManager.OnResultListener<ProductSearchResult>() {

            @Override
            public void onSuccess(ProductSearchResult result) {
                if (result.code == 200 && result.msg.equals("Success")) {
                    mAdapter.Clear();
                    mAdapter.addList(result.list);
                    //ChipView 추가

                    //헤더뷰 보여지고 있다면 태그를 추가한다.
                    if (mAdapter.getHeaderVisibleStatus()) {
                        RecyclerView.ViewHolder headerViewHolder = mAdapter.getHeaderViewHolder();
                        ((ProductSearchBrandHeaderView) headerViewHolder.itemView).adapterClear();
                        for (BrandListData item : mBrandListDatas) {
                            ((ProductSearchBrandHeaderView) headerViewHolder.itemView).addTag(item);
                        }

                    }
                } else {
                    Toast.makeText(getActivity(), "상품 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFail(int code) {

            }

        });
    }


}
