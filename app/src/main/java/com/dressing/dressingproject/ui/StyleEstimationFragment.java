package com.dressing.dressingproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.EstimationListAdapter;
import com.dressing.dressingproject.ui.models.CoordinationResult;
import com.dressing.dressingproject.util.AndroidUtilities;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by lee on 15. 11. 29.
 */
public class StyleEstimationFragment extends Fragment{


    private ListView mListView;
    private int mHeight;
    private EstimationListAdapter mAdapter;
    private ProgressWheel mProgressWheel;


    boolean isUpdate = false;

    boolean isLastItem = false;
    private TextView mCountView;
    private boolean mIsFirst;
    private TextView mCompleteBtn;

    public StyleEstimationFragment() {

    }

    public static StyleEstimationFragment newInstance(boolean mIsFirst) {

        Bundle args = new Bundle();
        args.putBoolean("isFirst",mIsFirst);
        StyleEstimationFragment fragment = new StyleEstimationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_style_estimation,container,false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mIsFirst = bundle.getBoolean("isFirst");
        }

        mListView = (ListView) view.findViewById(R.id.list);
        mListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mListView.getViewTreeObserver().removeOnPreDrawListener(this);
//                Log.v("TAGTAG", "width : " + view.getWidth());
//                Log.v("TAGTAG", "height : " + view.getHeight());
                mHeight = mListView.getHeight()- AndroidUtilities.dp(100);
                initListView();
                return false;
            }
        });

        mCountView = (TextView)view.findViewById(R.id.item_count_text);

        mProgressWheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);

        mCompleteBtn = (TextView)view.findViewById(R.id.complete_btn);
        mCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    public void setCountView(int value)
    {
        mCountView.setText(Integer.toString(value));
        if(value > 4 && mIsFirst)
        {
            mCompleteBtn.setVisibility(View.VISIBLE);
        }
    }

    public void initListView()
    {
        mAdapter = new EstimationListAdapter(getContext(),mListView,mHeight);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0 && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)) {
                    isLastItem = true;
                } else {
                    isLastItem = false;
                }
            }
        });


        requestEstimationCodi(0,5); //네트워크 요청!
    }

    private void getMoreItem() {
        if (!isUpdate) {
            int startIndex = mAdapter.getStartIndex();
            if (startIndex != -1) {
                isUpdate = true;
                requestEstimationCodi(startIndex,5);
            }
        }
    }

    private void requestEstimationCodi(int start, int display) {
        //스타일 취향 요청!
        NetworkManager.getInstance().requestGetCoordination(getContext(),start,display, new NetworkManager.OnResultListener<CoordinationResult>() {
            @Override
            public void onSuccess(CoordinationResult result)
            {
                mProgressWheel.setVisibility(View.GONE);
                if(result.code == 400)
                {
                    Toast.makeText(getContext(), "네트워크 요청 실패! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
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
