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

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.SimpleRecyclerAdapter;

import java.util.List;

/**
 * Created by lee on 15. 11. 4.
 */
public class ProductBrandFragment extends Fragment implements SimpleRecyclerAdapter.OnItemClickListener {

    SimpleRecyclerAdapter mAdapter;

    public ProductBrandFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_brand,container,false);


        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_brand_recyclerview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        //더미데이터 불러오기
        List<String> list = NetworkManager.getList();

        //더미데이터 어뎁터 바인딩
        mAdapter = new SimpleRecyclerAdapter(list);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onItemClick(View view) {
        Intent intent = new Intent(getActivity(),DetailProductActivity.class);
        startActivity(intent);
    }
}
