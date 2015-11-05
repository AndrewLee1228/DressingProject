package com.dressing.dressingproject.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.widget.CollapsingTitleLayout;

/**
 * Created by lee on 15. 11. 5.
 */
public class DetailCodiFragment extends Fragment{

    private static ScrollView movieSummary;
    private static CollapsingTitleLayout collapsingTitleLayout;
    private static Toolbar toolbar;
    private static ImageView collapsingTitleLayoutImage;

    public DetailCodiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codidetail,container,false);
        movieSummary = (ScrollView) view.findViewById(R.id.movie_summary);
        collapsingTitleLayout = (CollapsingTitleLayout)view.findViewById(R.id.backdrop_toolbar);
        collapsingTitleLayoutImage = (ImageView) view.findViewById(R.id.backdrop_toolbar_image);
        toolbar = (Toolbar) view.findViewById(R.id.fragment_detail_codi_toolbar);
        //툴바 백버튼 및 종료 액션 세팅
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        //반응형 배경
        ColorDrawable toolbarBackground = (ColorDrawable) toolbar.getBackground();
        int colorInt = toolbarBackground.getColor();
        final int[] toolbarColours = {
                Color.red(colorInt),
                Color.green(colorInt),
                Color.blue(colorInt)
        };
        collapsingTitleLayout.setBackground(toolbarBackground);
        toolbar.setBackground(null);
        collapsingTitleLayout.setTitle("여기에 타이틀 \r\n제목이 들어간다.");

        // 스크롤바의 패딩 제한 이상으로 그릴 수 있도록 해준다. (화면 그릴 때 타이틀바의 갭을 채운다.)
        movieSummary.setClipToPadding(false);

        movieSummary.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                ScrollView movieSummary = DetailCodiFragment.movieSummary;
                CollapsingTitleLayout collapsingTitle = DetailCodiFragment.collapsingTitleLayout;
                Toolbar toolbar = DetailCodiFragment.toolbar;
                ImageView collapsingTitleImage = DetailCodiFragment.collapsingTitleLayoutImage;

                // calculate the new size of the collapsing title
                int scrollY = movieSummary.getScrollY();
                int titleHeight = collapsingTitle.getHeight();
                int toolbarHeight = toolbar.getHeight();
                int heightRemaining = titleHeight - scrollY;
                float percent;
                if (heightRemaining > toolbarHeight) {
                    percent = scrollY / (float) (titleHeight - toolbarHeight);
                } else {
                    percent = 1.0f;
                }
                // if the user flicks it can cause a negative percent, which causes the colour filter to flash black
                percent = Math.max(0.0f, percent);

                // set the size of the title bar
                collapsingTitle.setScrollOffset(percent);
                // tint the image on collapse cos it looks neat
                //하단 투명하게
//                collapsingTitleImage.setColorFilter(Color.argb((int) (170f * percent), toolbarColours[0], toolbarColours[1], toolbarColours[2]));
            }
        });


        return view;
    }

    public static DetailCodiFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putString(MovieDetailsFragment.KEY_MOVIE_ID, movieId);
//        args.putString(MovieDetailsFragment.KEY_MOVIE_TITLE, movieTitle);
//        args.putBoolean(MovieDetailsFragment.KEY_AS_ACTIVITY, asActivity);
//
//        MovieDetailsFragment fragment = new MovieDetailsFragment();
//        fragment.setArguments(args);
        DetailCodiFragment fragment = new DetailCodiFragment();
        return fragment;
    }
}
