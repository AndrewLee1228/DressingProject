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
import com.dressing.dressingproject.ui.models.CodiModel;

/**
 * Created by lee on 15. 11. 3.
 * 코디추천 프래그먼트
 */
public class RecommendCodiFragment extends Fragment implements RecommendCodiAdapter.OnItemClickListener {

    private View mView;
    private RecyclerView mRecyclerView;

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

        RecommendCodiAdapter adapter = new RecommendCodiAdapter(NetworkManager.getRecommendList());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(View view, CodiModel codiModel) {
//        DetailActivity.navigate(this, view.findViewById(R.id.image), codiModel);
        Intent intent = new Intent(getActivity(),DetailCodiActivity.class);
        startActivity(intent);
    }
}
