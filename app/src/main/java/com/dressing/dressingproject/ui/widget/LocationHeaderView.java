package com.dressing.dressingproject.ui.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.manager.PropertyManager;
import com.dressing.dressingproject.ui.LocationFragment;
import com.dressing.dressingproject.ui.adapters.MainChipViewAdapter;
import com.dressing.dressingproject.ui.adapters.OnChipClickListener;
import com.dressing.dressingproject.ui.models.Area;
import com.dressing.dressingproject.ui.models.BrandListData;
import com.dressing.dressingproject.ui.models.Chip;
import com.dressing.dressingproject.ui.models.LocalAreaInfo;
import com.dressing.dressingproject.ui.models.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 20.
 */
public class LocationHeaderView extends BaseSearchModelFrameLayout implements OnChipClickListener {
    private final LocationFragment mParentFragment;
    ArrayAdapter<String> mCityAdapter, mGuAdapter;
    private Spinner mCitySpinner;
    private Spinner mGuSpinner;
    List<Area> areaList = new ArrayList<Area>();
    private MainChipViewAdapter mAdapterLayout;
    private ChipView mTextChipLayout;
    private ArrayList<Chip> mTagList;

    public LocationHeaderView(LocationFragment locationFragment) {
        super(locationFragment.getContext());
        mParentFragment = locationFragment;
        init();
    }

    //ChipView 추가
    public void addTag(BrandListData item)
    {
        mAdapterLayout.add(new Tag(item.getName(),item.getCode(),item.getImg(),item.getBrandNum(),item.isSync()));
    }

    //어뎁터 초기화
    public void adapterClear()
    {
        mAdapterLayout.Clear();
    }


    private void init() {
        inflate(getContext(), R.layout.item_location_headerview, this);
//        Button searchBtn = (Button) findViewById(R.id.item_search_product_price_search_btn);
//        searchBtn.setOnClickListener(this);
        // Adapter
        mTagList = new ArrayList<Chip>();
        mAdapterLayout = new MainChipViewAdapter(getContext());

        mTextChipLayout = (ChipView) findViewById(R.id.text_chip_layout);
        mTextChipLayout.setAdapter(mAdapterLayout);
        mTextChipLayout.setChipLayoutRes(R.layout.chip_close);

        mTextChipLayout.setChipBackgroundColor(getResources().getColor(R.color.main_blue));
        mTextChipLayout.setChipBackgroundColorSelected(getResources().getColor(R.color.main_blue));
//        mTextChipLayout.setChipBackgroundColor(getResources().getColor(R.color.light_green));
//        mTextChipLayout.setChipBackgroundColorSelected(getResources().getColor(R.color.green));
        mTextChipLayout.setChipList(mTagList);
        mTextChipLayout.setOnChipClickListener(this);

        //스피너 초기화 그리고 어뎁터 바인딩
        mCityAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner);
        mCityAdapter.setDropDownViewResource(R.layout.item_spinner);
        mCitySpinner = (Spinner)findViewById(R.id.item_product_fitting_headerview_spinner_city);
        mCitySpinner.setAdapter(mCityAdapter);
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    PropertyManager.getInstance().setCityLocation(mCityAdapter.getItem(position));
                }
                Area area;
                mGuAdapter.clear();
                mGuAdapter.add("군/구");
                for (int i = 0; i < areaList.size(); i++) {
                    area = areaList.get(i);
                    if (area.upperDistName.equals(mCityAdapter.getItem(position))) {
                        NetworkManager.getInstance().requestGetLocalInfo(getContext(), 1, "M", area.upperDistCode, new NetworkManager.OnResultListener<LocalAreaInfo>() {
                            @Override
                            public void onSuccess(LocalAreaInfo result) {
                                for (Area s : result.areas.area) {
                                    if (!s.middleDistName.equals(""))
                                        mGuAdapter.add(s.middleDistName);
                                }
                            }

                            @Override
                            public void onFail(int code) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mGuAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner);
        mGuAdapter.setDropDownViewResource(R.layout.item_spinner);
        mGuSpinner = (Spinner)findViewById(R.id.item_product_fitting_headerview_spinner_gu);
        mGuSpinner.setAdapter(mGuAdapter);
        mGuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    PropertyManager propertyManager = PropertyManager.getInstance();
                    propertyManager.setGuLocation(mGuAdapter.getItem(position));
                    mParentFragment.HideText(View.GONE);

                    String city = propertyManager.getCityLocation();
                    String gu = propertyManager.getGuLocation();

                    mParentFragment.SetLocation(city,gu);

                }
                else
                {
                    mParentFragment.HideText(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //초기값
        mCityAdapter.add("시/도");
        mGuAdapter.add("군/구");

        //네트워크 요청
        NetworkManager.getInstance().requestGetLocalInfo(getContext(), 1, "L", 0, new NetworkManager.OnResultListener<LocalAreaInfo>() {
            @Override
            public void onSuccess(LocalAreaInfo result) {
                for (Area s : result.areas.area) {
                    mCityAdapter.add(s.upperDistName);
                    areaList.add(s);
                }
            }

            @Override
            public void onFail(int code) {

            }
        });
    }



    @Override
    public void onClick(View view)
    {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, null,getPosition());
        }
    }

    @Override
    public void onChipClick(Chip chip) {
        mTextChipLayout.remove(chip);
        LocationFragment fragment = (LocationFragment) mParentFragment;
//        fragment.updateFilter(mAdapterLayout.getChipList());
    }
}
