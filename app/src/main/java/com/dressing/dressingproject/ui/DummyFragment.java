package com.dressing.dressingproject.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.adapters.SimpleRecyclerAdapter;
import com.dressing.dressingproject.ui.models.VersionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 2.
 */
public class DummyFragment extends Fragment {
    int mColor;
    SimpleRecyclerAdapter adapter;

    public DummyFragment() {

    }

    @SuppressLint("ValidFragment")
    public DummyFragment(int color) {
        this.mColor = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy,container,false);

        //Fragment 배경 세팅
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
        frameLayout.setBackgroundColor(mColor);

        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        //레이아웃 매니저
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //어뎁터가 변경되어도 리싸이클러뷰의 크기에 영향을 주지 않는다.
        recyclerView.setHasFixedSize(true);

        //더미데이터 불러오기
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }

        //더미데이터 어뎁터 바인딩
        adapter = new SimpleRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
