package com.dressing.dressingproject.ui;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.RecyclerViewBaseAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiScoreResult;

/**
 * Created by lee on 15. 11. 10.
 */
public class ScoreDialogFragment extends DialogFragment implements RatingBar.OnRatingBarChangeListener {

    private CodiModel mItem;
    private RecyclerViewBaseAdapter mAdapter;
    private int mPosition;
    private ImageView mImageView;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;

    public  static  ScoreDialogFragment newInstance(float score)  {
        ScoreDialogFragment  f  =  new  ScoreDialogFragment ();
        Bundle args = new Bundle();
        args.putFloat("score", score);
        f.setArguments(args);
        return  f ;
    }

    public void setData(CodiModel item, RecyclerViewBaseAdapter adapter,int position)
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
        mImageView = (ImageView) customDialogView.findViewById(R.id.item_score_dialog_image);
        mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //코디상세화면에서는 스코어 다이얼로그에서 이미지 보여줄 필요 없으므로 GONE !
                if (mAdapter == null) mImageView.setVisibility(View.GONE);
                else
                {
                    mImageView.setVisibility(View.VISIBLE);
                    Glide.with(getActivity())
                            .load(Integer.parseInt(mItem.getImageURL()))
                                    //                .centerCrop()
                                    //                .placeholder(android.R.drawable.progress_horizontal)
                            .crossFade()
                            .thumbnail(0.1f)
                            .into(mImageView);
                }
                removeOnGlobalLayoutListener(mImageView.getViewTreeObserver(), mGlobalLayoutListener);
            }
        };
        mImageView.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
        RatingBar ratingBar = (RatingBar) customDialogView.findViewById(R.id.item_score_dialog_ratingBar);
        ratingBar.setOnRatingBarChangeListener(this);
        Bundle bundle = getArguments();
        float score = bundle.getFloat("score");
        if ( score != 0) {
            ratingBar.setRating(score);
        }

        builder.setView(customDialogView);
        builder.create();

        return builder.create();
    }

    private static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer == null) {
            return ;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            observer.removeGlobalOnLayoutListener(listener);
        } else {
            observer.removeOnGlobalLayoutListener(listener);
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar,float rating, boolean fromUser) {

        if (fromUser == true) {

            final FragmentActivity parentActivity =getActivity();

            //상세개별상품코디
            if (parentActivity instanceof DetailProductActivity || parentActivity instanceof MainActivity) {
                NetworkManager.getInstance().requestUpdateCodiScore(getActivity(), rating,mItem, new NetworkManager.OnResultListener<CodiScoreResult>() {

                    @Override
                    public void onSuccess(CodiScoreResult codiScoreResult) {
                        mItem.setUserScore(Float.toString(codiScoreResult.getRating()));
                        mAdapter.ItemChanged(mPosition);
                    }

                    @Override
                    public void onFail(int code) {
                       //평가실패
                    }
                });
            }
            //상세코디
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
