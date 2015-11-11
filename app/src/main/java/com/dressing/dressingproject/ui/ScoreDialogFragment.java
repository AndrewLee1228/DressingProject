package com.dressing.dressingproject.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.DetailProductAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiScoreResult;

/**
 * Created by lee on 15. 11. 10.
 */
public class ScoreDialogFragment extends DialogFragment implements RatingBar.OnRatingBarChangeListener {

    private CodiModel mItem;
    private DetailProductAdapter mAdapter;
    private int mPosition;

    public  static  ScoreDialogFragment  getInstance (float score )  {
        ScoreDialogFragment  f  =  new  ScoreDialogFragment ();
        Bundle args = new Bundle();
        args.putFloat("score", score);
        f.setArguments(args);
        return  f ;
    }

    public void setData(CodiModel item, DetailProductAdapter adapter,int position)
    {
        mItem = item;
        mAdapter = adapter;
        mPosition = position;
    }

    public void setData(CodiModel item) {
        mItem = item;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(null);
        View customDialogView = inflater.inflate(R.layout.item_score_dialog, null, false);
        RatingBar ratingBar = (RatingBar) customDialogView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(this);
        Bundle bundle = getArguments();
        float score = bundle.getInt("score");
        if ( score != 0) {
            ratingBar.setRating(score);
        }

        builder.setView(customDialogView);
        builder.create();

        return builder.create();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar,float rating, boolean fromUser) {

        if (fromUser == true) {

            final FragmentActivity parentActivity =getActivity();

            //개별상품
            if (parentActivity instanceof DetailProductActivity) {
                NetworkManager.getInstance().requestUpdateCodiScore(getActivity(), rating,mItem, new NetworkManager.OnResultListener<CodiScoreResult>() {

                    @Override
                    public void onSuccess(CodiScoreResult codiScoreResult) {
                        mItem.setUserScore(Float.toString(codiScoreResult.getRating()));
                        mAdapter.notifyItemChanged(mPosition);
                    }

                    @Override
                    public void onFail(int code) {
                       //평가실패
                    }
                });
            }
            //코디
            else if (parentActivity instanceof DetailCodiActivity)
            {
                NetworkManager.getInstance().requestUpdateCodiScore(getActivity(), rating,mItem, new NetworkManager.OnResultListener<CodiScoreResult>() {

                    @Override
                    public void onSuccess(CodiScoreResult codiScoreResult) {
                        ((DetailCodiActivity)parentActivity).changeScore(Float.toString(codiScoreResult.getRating()),true);
                    }

                    @Override
                    public void onFail(int code) {
                        //평가실패
                    }
                });
            }



            dismiss();
        }
    }

}
