package com.app.bolayam.adapters;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bolayam.R;
import com.application.adapters.ImageBaseAdapter;
import com.application.adapters.ImageBaseAdapter.Mapper;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageHolderBuilder;
import com.application.picasoimageloader.PicasoHalper;
import com.application.ui.RoundedImageView;
import com.application.utils.OSUtil;
import com.google.android.gms.internal.di;

public class FriendsListAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<ImageHolder> mData;
	Mapper mMapper;
	 LayoutInflater inflater;
	
	public FriendsListAdapter(Context context,ArrayList<ImageHolder> data,Mapper mapper){
		mContext = context;
		mMapper = mapper ;
		mData = data;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size() /2 ;
	}

	@Override
	public ImageHolder getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = inflater.inflate(OSUtil.getLayoutResourceIdentifier(mMapper.itemLayoutName), parent, false);
		}
		
		int firstIndex = position * 2;
		int secondIndex = position * 2 +1;
		
		setLayout(convertView,firstIndex,1);
		setLayout(convertView,secondIndex,2);
		
		
		return convertView;
	}

	private void setLayout(View convertView, int index,int pictuer) {
		if(index < mData.size()){
			ImageHolder holder = getItem(index);
			
			ImageView image = (ImageView) convertView.findViewById(OSUtil.getResourceId("pic_" + pictuer));
			image.setTag(holder);
			image.setOnClickListener(imageClickListener);
			
			if(Boolean.parseBoolean(holder.getExtension(ImageHolderBuilder.IS_USER_FRIEND) )){
				image.setBackgroundResource(OSUtil.getDrawableResourceIdentifier("user_friend_bg" ));
				image.setPadding(OSUtil.convertDPToPixels(2), OSUtil.convertDPToPixels(2), OSUtil.convertDPToPixels(2), OSUtil.convertDPToPixels(2));
			}else{
				image.setBackgroundResource(OSUtil.getDrawableResourceIdentifier("user_not_friend_bg" ));
				image.setPadding(0,0,0,0);
			}
			
			PicasoHalper.loadImage(mContext, image,holder.getImageUrl());
			
		}
		
	}
	
	OnClickListener imageClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ImageHolder holder = (ImageHolder)v.getTag();
			showUserDialog(holder);
		}
	};

	protected void showUserDialog(final ImageHolder holder) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(mContext);
		dialog.setContentView(R.layout.fb_user_dialog);
		 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		TextView text = (TextView) dialog.findViewById(R.id.user_name);
		text.setText(holder.getImageTitle());
		
		RoundedImageView image = (RoundedImageView) dialog.findViewById(R.id.user_pic);
		PicasoHalper.loadImage(mContext, image, holder.getImageUrl());

		View dialogButton = dialog.findViewById(R.id.go_to_profile_container);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtil.OpenFacebookIntent(mContext,holder.getID());
				
				dialog.dismiss();
			}
		});

		dialog.show();
	  }
	
	
}
