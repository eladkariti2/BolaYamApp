package com.app.bolayam.adapters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bolayam.R;
import com.app.bolayam.activities.FeedItemActivity;
import com.app.bolayam.activities.FeedItemActivity.FeetItemType;
import com.application.activities.FeedPostDetailsActivity;
import com.application.adapters.ImageBaseAdapter;
import com.application.facebook.loader.APLikeRequest;
import com.application.facebook.util.FacebookUtil;
import com.application.imageholders.FBPostHolder;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageHolderBuilder;
import com.application.listener.AsyncTaskListener;
import com.application.picasoimageloader.PicasoHalper;
import com.application.utils.APUiUtil;
import com.application.utils.StringUtil;

public class FeedListAdapter extends ImageBaseAdapter{

	public FeedListAdapter(Context context, ArrayList<ImageHolder> data,Mapper mapper) {
		super(context, data, mapper);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);

		FBPostHolder holder = (FBPostHolder)getItem(position);

		TextView creationTimeTv = (TextView)view.findViewById(R.id.creation_time);
		TextView nameTv = (TextView) view.findViewById(R.id.user_name);
		TextView messageTv = (TextView) view.findViewById(R.id.post_text);
		TextView commentsNumberTv = (TextView) view.findViewById(R.id.comments_number);
		TextView likesNumberTv = (TextView) view.findViewById(R.id.likes_number);
		
		ImageView pictuerIV = (ImageView) view.findViewById(R.id.feed_image);
		ImageView likeImg = (ImageView) view.findViewById(R.id.like_image);
		
		View likeContainer = view.findViewById(R.id.like_text_container);
		View commentContainer = view.findViewById(R.id.comments_text_container);

		LikeOnClickListener likeClick = new LikeOnClickListener(likeImg);
		likeContainer.setOnClickListener(likeClick);

		final String postPicture = holder.getPictureURL();
		String name = holder.getUserName();
		String message = holder.getMessage();
		String dateStr = holder.getPostDate();
		int commentNumber = holder.getCommentNumber();
		int likeNumber = holder.getLikesNumber();

		String creationTime = "";
		if(!StringUtil.isEmpty(dateStr)){
			Date date = null;
			try {
				date = StringUtil.internetDF.parse(dateStr);
				creationTime = StringUtil.getRelationalDateString(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		likeImg.setBackgroundColor(Boolean.parseBoolean(holder.getExtension(ImageHolderBuilder.ME_LIKED_POST)) ? Color.RED : Color.BLUE);
		
		
		creationTimeTv.setText(creationTime);
		nameTv.setText(name);
		messageTv.setText(message);
		likesNumberTv.setText(likeNumber + "");
		commentsNumberTv.setText(commentNumber + "");
		
		if(StringUtil.isEmpty(postPicture)){
			pictuerIV.setVisibility(View.GONE);
		}
		else{
			pictuerIV.setVisibility(View.VISIBLE);
			if(holder.getWidth() > 0 && holder.getHeight() > 0){
				APUiUtil.adjustImageToFullScreenWidth(pictuerIV, holder.getWidth(), holder.getHeight());
			}
			PicasoHalper.loadImage(mContext, pictuerIV, postPicture);
			pictuerIV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FeedItemActivity.startFeedItemActivity(mContext, postPicture,FeetItemType.IMAGE);
				}
			});
			
		}
		view.setTag(holder);
		likeContainer.setTag(holder);
		commentContainer.setTag(holder);
		return view;
	}

	public void updateFeed(ArrayList<ImageHolder> holders) {
		// TODO Auto-generated method stub
		if(mData != null && mData.isEmpty()){
			mData.addAll(holders);
			notifyDataSetChanged();
		}else{
			mData.addAll(0,holders);
			notifyDataSetChanged();
		}
	}

	public boolean hasData() {
		// TODO Auto-generated method stub
		return mData != null && ! mData.isEmpty();
	}



	OnClickListener commentClick = new OnClickListener() {


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ImageHolder holder = (ImageHolder)v.getTag();
			FeedPostDetailsActivity.startFeedPostDetailsActivity(mContext, (FBPostHolder) holder);
		}
	};
	
	public class LikeOnClickListener implements OnClickListener{
		
		ImageView mView;
		
		public LikeOnClickListener(ImageView imageView){
			mView = imageView;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final FBPostHolder holder = (FBPostHolder)v.getTag();
			final boolean isLiked = Boolean.parseBoolean(holder.getExtension(ImageHolderBuilder.ME_LIKED_POST));
			APLikeRequest req = new APLikeRequest(holder.getID(),new AsyncTaskListener<Boolean>() {

				@Override
				public void handleException(Exception e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTaskComplete(Boolean result) {
					// TODO Auto-generated method stub
					mView.setBackgroundColor(!isLiked ? Color.RED : Color.BLUE);
					holder.addExtension(ImageHolderBuilder.ME_LIKED_POST,!isLiked +"");
					holder.setLikesNumber( !isLiked ?holder.getLikesNumber() + 1 : holder.getLikesNumber() - 1);  
					notifyDataSetChanged();
				}

				@Override
				public void onTaskStart() {
					// TODO Auto-generated method stub
					
				}
			}, isLiked);
			
			req.doQuery();
		}
		
		
	}
}
