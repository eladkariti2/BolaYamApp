package com.app.bolayam.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.bolayam.R;
import com.app.bolayam.beachholder.BeachImageHolder;
import com.application.base.BaseActivity;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageLoader;
import com.application.imageholders.ImageLoader.APImageListener;
import com.application.ui.APWebView;
import com.application.utils.JsonUtil;

public class FeedItemActivity extends BaseActivity implements APImageListener{

	public static final String ITEM_TYPE = "tpye";
	public static final String ITEM_URL = "url";

	APWebView mWebView;
	ImageView mImage;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_item_layout);

		String url  = getIntent().getExtras().getString(ITEM_URL);
		String typeString = getIntent().getExtras().getString(ITEM_TYPE);

		FeetItemType type;
		
		if(FeetItemType.IMAGE.toString().equals(typeString)){
			type = FeetItemType.IMAGE;
		}else{
			type = FeetItemType.LINK;
		}
		
		mWebView = (APWebView)findViewById(R.id.web_view);
		mImage = (ImageView)findViewById(R.id.item_image);

		init(url,type);
	}



	private void init(String url, FeetItemType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case LINK:
			loadUrl(url);
			break;
		case IMAGE:
			loadImage(url);
			break;
		default:
			break;
		}
	}



	private void loadImage(String url) {
		// TODO Auto-generated method stub
		mWebView.setVisibility(View.GONE);
		ImageHolder holder = new ImageHolder("", "", url);
		ImageLoader loader = new ImageLoader(holder, this);
		loader.loadImages();
	}



	private void loadUrl(String url) {
		// TODO Auto-generated method stub
		mImage.setVisibility(View.GONE);
		mWebView.loadUrl(url);
	}



	public static void startFeedItemActivity(Context context,String url,FeetItemType type) {

		Intent i = new Intent(context, FeedItemActivity.class);
		i.putExtra(ITEM_TYPE, type);
		i.putExtra(ITEM_URL, url);
		context.startActivity(i);
	}

	public static enum FeetItemType{
		IMAGE,LINK
	}

	
	private void adjustImageViewBound(Drawable drawable, ImageView imageView) {
		// TODO Auto-generated method stub

		int h = drawable.getIntrinsicHeight();
		int w = drawable.getIntrinsicWidth();
		float sizeFactor  = (float)h/(float)w;
		int heightInPx = (int) (sizeFactor * imageView.getWidth());
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightInPx);
		imageView.setLayoutParams(lp);
	}
	
	@Override
	public void handleException(Exception e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTaskComplete(ImageHolder[] result) {
		// TODO Auto-generated method stub
		Drawable drawable = result[0].getDrawable();
		adjustImageViewBound(drawable,mImage);
	}



	@Override
	public void onTaskStart() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onRequestSent(ImageHolder imageHolder) {
		// TODO Auto-generated method stub
		
	}
}
