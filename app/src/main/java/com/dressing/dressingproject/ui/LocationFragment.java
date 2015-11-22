package com.dressing.dressingproject.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.LocationRecyclerAdapter;
import com.dressing.dressingproject.ui.models.MallModel;
import com.dressing.dressingproject.ui.models.MallResult;
import com.dressing.dressingproject.ui.widget.LocationHeaderView;
import com.dressing.dressingproject.ui.widget.StoreLocationView;
import com.dressing.dressingproject.util.AndroidUtilities;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 20.
 */
public class LocationFragment extends Fragment {

    private TextView mLocationText;
    private LocationRecyclerAdapter mAdapter;

    public LocationFragment() {

    }

    public static LocationFragment newInstance() {

        Bundle args = new Bundle();

        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_location,container,false);
        //리싸이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_location_recyclerview);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new LocationRecyclerAdapter(this);
        mAdapter.setOnAdapterItemListener(new LocationRecyclerAdapter.OnAdapterItemListener() {
            @Override
            public void onAdapterItemClick(LocationRecyclerAdapter adapter, View view, MallModel mallModel, int position) {
                ((StoreLocationView)view).setChecked(!mallModel.isSelected());
                mallModel.setSelected(!mallModel.isSelected());

                //헤더뷰에 추가
                if(adapter.mVisiblewHeader)
                {
                    ((LocationHeaderView)adapter.mHeaderHolder.itemView).addTagText(adapter.GetTagText());
                }
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(AndroidUtilities.dp(4)));

        //그리드 레이아웃 Span 개수 Controll
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                return mAdapter.isPositionHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }

        });

        mLocationText = (TextView)view.findViewById(R.id.fragment_location_text);

        return view;
    }

    public void HideText(int visible) {
        if(mLocationText != null)
        {
            mLocationText.setVisibility(visible);
            mAdapter.Clear();
        }
    }

    //네트워크로 위치 요청을 보내서 상점들을 리스트로 받음
    public void SetLocation(String city, String gu)
    {
        NetworkManager.getInstance().requestGetShoppingMall(getContext(),city,gu, new NetworkManager.OnResultListener<MallResult>() {

            @Override
            public void onSuccess(MallResult result) {
                ArrayList<MallModel> list = new ArrayList<MallModel>();
                Toast.makeText(getActivity(), "정상적으로 요청이 처리됨.", Toast.LENGTH_SHORT).show();
                for(int i =0 ; i <20 ;i++)
                {
                    MallModel mallModel = new MallModel();
                    mallModel.mallName = "test"+i;
                    list.add(mallModel);
                }
                //mAdapter.addMallList(result.mallList);
                mAdapter.addMallList(list);
            }

            @Override
            public void onFail(int code) {

            }
        });
    }

    /**
     * divider 클래스
     * http://stackoverflow.com/questions/28531996/android-recyclerview-gridlayoutmanager-column-spacing
     */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int halfSpace;

        public SpacesItemDecoration(int space) {
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//            if (parent.getPaddingStart() < halfSpace) {
//                parent.setPadding(halfSpace,halfSpace, halfSpace, halfSpace);
//                parent.setClipToPadding(false);
//            }
//
//            if(parent.getPaddingEnd() < halfSpace)
//            {
//                parent.setPadding(halfSpace,halfSpace, halfSpace,halfSpace);
//                parent.setClipToPadding(false);
//            }

            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;

        }
    }
}
