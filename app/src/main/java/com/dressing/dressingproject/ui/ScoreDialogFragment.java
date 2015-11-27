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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.manager.NetworkManager;
import com.dressing.dressingproject.ui.adapters.RecyclerViewBaseAdapter;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.PostEstimationResult;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by lee on 15. 11. 10.
 */
public class ScoreDialogFragment extends DialogFragment implements RatingBar.OnRatingBarChangeListener {

    private CodiModel mItem;
    private RecyclerViewBaseAdapter mAdapter;
    private int mPosition;
    private ImageView mImageView;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private ProgressWheel mProgressWheel;

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
        mProgressWheel = (ProgressWheel) customDialogView.findViewById(R.id.progress_wheel);
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
                            .load(mItem.getImageURL())
                                    //                .centerCrop()
                                    //                .placeholder(android.R.drawable.progress_horizontal)
                            .crossFade()
                            .thumbnail(0.1f)
                            .override(400, 400)
                            .diskCacheStrategy (DiskCacheStrategy.RESULT)
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                                    mProgressWheel.setVisibility(View.GONE);
                                    return false;
                                }
                            })
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


                /**
                 * 상품의 별점 요청은 다음의 두가지 입니다.
                 * 선호취향평가 요청과 별점평가 수정
                 * 아직 평가되지 않은 코디의 경우 선호취향평가 요청을 보내고
                 * 이미 평가된 코디의 별점을 변경하고자 할 경우 별점평가 수정 요청을 보냅니다.
                 */

                 NetworkManager.getInstance().requestPostEstimation(getActivity(), rating, mItem, new NetworkManager.OnResultListener<PostEstimationResult>() {

                     @Override
                     public void onSuccess(PostEstimationResult postEstimationResult) {

                         if (postEstimationResult.code == 200)
                         {
                             //DetailCodiActivity
                             if (parentActivity instanceof DetailCodiActivity) {
                                 ((DetailCodiActivity)parentActivity).changeScore(Float.toString(postEstimationResult.getRating()),true);
                             //MainActivity & DetailProductActivity
                             } else {
                                 mItem.setUserScore(Float.toString(postEstimationResult.getRating()));
                                 mAdapter.ItemChanged(mPosition);
                             }

                         } else
                             Toast.makeText(getActivity(), "평가 요청에 실패하였습니다!", Toast.LENGTH_SHORT).show();
                     }

                     @Override
                     public void onFail(int code) {
                         //평가실패
                         Toast.makeText(getActivity(), "네트워크 연결상태가 불안정합니다!", Toast.LENGTH_SHORT).show();
                     }
                 });

            dismiss();
        }
    }

}
