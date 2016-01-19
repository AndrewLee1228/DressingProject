package com.dressing.dressingproject.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.manager.PropertyManager;
import com.dressing.dressingproject.ui.models.AnalysisResult;
import com.dressing.dressingproject.ui.models.LoginInfo;
import com.dressing.dressingproject.util.FontManager;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

/**
 * Created by lee on 15. 11. 29.
 */
public class StyletasteFragment extends Fragment
{

    private RadarChart mChart;
    private View mView;

    public StyletasteFragment() {

    }

    public static StyletasteFragment newInstance() {

        Bundle args = new Bundle();

        StyletasteFragment fragment = new StyletasteFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_style_taste, container, false);

        //네트워크 요청
        NetworkManager.getInstance().requestAnalysis(getActivity(), new NetworkManager.OnResultListener<AnalysisResult>() {

            @Override
            public void onSuccess(AnalysisResult analysisResult) {
                if (analysisResult.code == 200) {
                    LayoutInit(analysisResult);
                }
            }

            @Override
            public void onFail(int code) {
                //찜하기 요청 실패
            }

        });
        return mView;
    }

    private void LayoutInit(AnalysisResult analysisResult) {
        ArrayList<String> sortedKeyword= analysisResult.sortedKeyword;
        TextView keyword1 = (TextView) mView.findViewById(R.id.keyword1);
        keyword1.setText(sortedKeyword.get(0));
        TextView keyword2 = (TextView) mView.findViewById(R.id.keyword2);
        keyword2.setText(sortedKeyword.get(1));
        TextView keyword3 = (TextView) mView.findViewById(R.id.keyword3);
        keyword3.setText(sortedKeyword.get(2));
        TextView keyword4 = (TextView) mView.findViewById(R.id.keyword4);
        keyword4.setText(sortedKeyword.get(3));
        TextView keyword5 = (TextView) mView.findViewById(R.id.keyword5);
        keyword5.setText(sortedKeyword.get(4));
        TextView keyword6 = (TextView) mView.findViewById(R.id.keyword6);
        keyword6.setText(sortedKeyword.get(5));

        PropertyManager propertyManager = PropertyManager.getInstance();
        LoginInfo loginInfo = propertyManager.getLoginInfo();

        //사용자 닉네임
        String nick = String.format("%s님 취향 분석",loginInfo.getNickName());
        TextView userNick = (TextView) mView.findViewById(R.id.nav_user_nick);
        userNick.setText(nick);

        //사용자 이미지
        String userImgURL = loginInfo.getImg();
        final ImageView userImg = (ImageView) mView.findViewById(R.id.chart_user_img);
        Glide.with(this).load(userImgURL).asBitmap().centerCrop().into(new BitmapImageViewTarget(userImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userImg.setImageDrawable(circularBitmapDrawable);
            }
        });

        mChart = (RadarChart) mView.findViewById(R.id.chart);

        mChart.setDescription("");
        mChart.setRotationAngle(60);

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        setData(analysisResult);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(FontManager.getInstance().getTypeface(getActivity(), FontManager.NOTO));
        xAxis.setTextSize(9f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setTypeface(FontManager.getInstance().getTypeface(getActivity(), FontManager.NOTO));
        yAxis.setStartAtZero(true);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTypeface(FontManager.getInstance().getTypeface(getActivity(), FontManager.NOTO));
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

//    private String[] mParties = new String[] {
//            "빈티지", "캐쥬얼", "남성적", "모던", "세련", "댄디"
//    };

    public void setData(AnalysisResult analysisResult) {

        String[] parties = new String[6];
        for (int i = 0; i < 6; i++) {
            parties[i] = analysisResult.sortedLiking.get(i);
        }
        float mult = 150;
        int cnt = 6;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < cnt; i++) {
            //mult 해당 값

            switch (i)
            {
                case 0:
                    yVals1.add(new Entry((float) 130 , i));
                    break;
                case 1:
                    yVals1.add(new Entry((float) 150 , i));
                    break;
                case 2:
                    yVals1.add(new Entry((float) 120 , i));
                    break;
                case 4:
                    yVals1.add(new Entry((float) 180 , i));
                    break;
                default:
                    yVals1.add(new Entry((float) 90 , i));
                    break;
            }
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(parties[i % parties.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "선호취향");
        set1.setColor(Color.parseColor("#fbc02d"));
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(xVals, sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);

        mChart.setData(data);

        mChart.invalidate();
    }
}
