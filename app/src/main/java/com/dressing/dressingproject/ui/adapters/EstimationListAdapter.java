package com.dressing.dressingproject.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.StyleActivity;
import com.dressing.dressingproject.ui.StyleEstimationFragment;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

public class EstimationListAdapter extends BaseAdapter implements  RatingBar.OnRatingBarChangeListener {
	private final ListView mListView;
	private final int mHeight;
	private Context mContext;
	private LayoutInflater inflater;
	private List<CodiModel> mCodiItems =new ArrayList<CodiModel>();
	private ProgressWheel mProgressWheel;
	private ImageView mCodiView;
	private EstimationHolder viewHolder;
	private int mCount;

	public EstimationListAdapter(Context context, ListView mListView, int mHeight) {
		this.mContext = context;
		this.mListView = mListView;
		this.mHeight = mHeight;
	}

	@Override
	public int getCount() {
		return mCodiItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mCodiItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
		{
			if (inflater == null)
				inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_estimation_view, null);
			viewHolder = new EstimationHolder();
			//ProgressWheel
			viewHolder.ProgressWheel = (ProgressWheel)convertView.findViewById(R.id.progress_wheel);
			//아이템이미지뷰
			viewHolder.codiView = (ImageView)convertView.findViewById(R.id.item_estimation_view_img);
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mHeight);
			viewHolder.codiView.setLayoutParams(layoutParams);
			//RatingBar
			viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.item_estimation_ratingBar);
			viewHolder.ratingBar.setOnRatingBarChangeListener(this);

			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (EstimationHolder)convertView.getTag();
		}


		//--------------------------------------  아이템 세팅-----------------------------------------//

		//item load.
		CodiModel codiModel = mCodiItems.get(position);

		//이미지 로드
		if (codiModel.getImageURL() != null) {
			Glide.with(mContext)
					.load(codiModel.getImageURL())
					.asBitmap()
					.thumbnail(0.1f)
					.override(400, 400)
					.diskCacheStrategy(DiskCacheStrategy.RESULT)
					.into(new SimpleTarget<Bitmap>() {
							@Override
							public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
								//1.프로그레스 감추기
								viewHolder.ProgressWheel.setVisibility(View.GONE);
								//2.비트맵 세팅!
								viewHolder.codiView.setImageBitmap(bitmap);
							}
						});
		}
		//RattingBar
		viewHolder.ratingBar.setTag(position);

		/**
		 *		 아이템 score가 있다면 세팅한다.
		 *		 없다면 0으로 초기화.
		 */
		Float score = Float.parseFloat(codiModel.getEstimationScore());
		if(score > 0)
		{
			viewHolder.ratingBar.setRating(score);
		}
		else
			viewHolder.ratingBar.setRating(0);


		return convertView;
	}


	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
		int position = (int) ratingBar.getTag();
		if (fromUser) {
			CodiModel codiModel = mCodiItems.get(position);
			codiModel.setEstimationScore(Float.toString(rating));
			mListView.smoothScrollToPositionFromTop(position+1,-10,710);
			//카운트 증가감소!
			mCount = mCount+ 1;
			StyleEstimationFragment fragment = (StyleEstimationFragment) ((StyleActivity)mContext).GetCurrentPageFragment();
			fragment.setCountView(mCount);
		}
	}

	public void addList(ArrayList<CodiModel> coordinationList) {
		mCodiItems.addAll(coordinationList);
		notifyDataSetChanged();
	}

	public int getStartIndex() {
		return mCodiItems.size()+1;
	}

	private class EstimationHolder {
		public ProgressWheel ProgressWheel;
		public ImageView codiView;
		public RatingBar ratingBar;
	}
}
