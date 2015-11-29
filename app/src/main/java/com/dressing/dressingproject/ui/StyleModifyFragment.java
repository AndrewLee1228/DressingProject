package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.dressing.dressingproject.ui.models.EstimationResult;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by lee on 15. 11. 29.
 */
public class StyleModifyFragment extends Fragment {
    private View mView;
    private ProgressWheel mProgressWheel;
    private RecyclerView mRecyclerView;
    private RecommendCodiAdapter mAdapter;

    public StyleModifyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_style_modify,container,false);

        mProgressWheel = (ProgressWheel)mView.findViewById(R.id.progress_wheel);

        inti();

        return mView;
    }

    public static StyleModifyFragment newInstance() {

        Bundle args = new Bundle();

        StyleModifyFragment fragment = new StyleModifyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void inti()
    {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_style_modify_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mAdapter = new RecommendCodiAdapter(RecommendCodiAdapter.FLAG_STYLE_MODIFY);

        mAdapter.setOnAdapterItemListener(new RecommendCodiAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(RecommendCodiAdapter adapter, final View view, final CodiModel codiModel, int position) {
                switch (view.getId())
                {
                    case R.id.item_recommend_view_frame_layout:
                        ScoreDialogFragment scoreDialogFragment = ScoreDialogFragment.newInstance(Float.parseFloat(codiModel.getUserScore()));
                        scoreDialogFragment.setData(codiModel,adapter,position);
                        scoreDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                        break;
                }
            }

        });

        mRecyclerView.setAdapter(mAdapter);

        mProgressWheel.setVisibility(View.VISIBLE);

        networkRequest(0,10);

    }

    private void networkRequest(int start,int display) {

        //코디평가수정 리스트 요청!
        NetworkManager.getInstance().requestGetEstimation(getContext(),start,display, new NetworkManager.OnResultListener<EstimationResult>() {
            @Override
            public void onSuccess(EstimationResult result)
            {

                if(result.code == 400)
                {
                    Toast.makeText(getActivity(), "네트워크 요청 실패! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mProgressWheel.setVisibility(View.GONE);
                    mAdapter.addList(result.coordinationList);
                }
            }

            @Override
            public void onFail(int code)
            {

            }
        });

    }
}
