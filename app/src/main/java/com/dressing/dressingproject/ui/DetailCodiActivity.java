package com.dressing.dressingproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dressing.dressingproject.R;

public class DetailCodiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_codi);

        InitLayout();

//        Intent i = this.getIntent();
//        String movieId = i.getStringExtra(MovieDetailsFragment.KEY_MOVIE_ID);
//        String movieTitle = i.getStringExtra(MovieDetailsFragment.KEY_MOVIE_TITLE);
//
//        // create the fragment
        DetailCodiFragment fragment = DetailCodiFragment.newInstance();
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_detail_codi_fragment, fragment)
                .commit();


    }

    private void InitLayout() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.activiy_detail_codi_toolbar);
        toolbar.setBackground(null);
        toolbar.setVisibility(View.GONE);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("코디 상세보기");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
