package com.app.bolayam.activities;

import java.util.ArrayList;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bolayam.R;
import com.app.bolayam.adapters.FriendsListAdapter;
import com.app.bolayam.beachholder.BeachImageHolder;
import com.application.adapters.ImageBaseAdapter.Mapper;
import com.application.base.BaseActivity;
import com.application.helper.StaticObjectHalper;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageHolderBuilder;
import com.application.picasoimageloader.PicasoHalper;
import com.application.ui.HorizontalListView;
import com.application.utils.CustomImageLoader;
import com.application.utils.JsonUtil;

public class BeacheDetailsActivity extends BaseActivity {

	private static final String BEACH_HOLDER = "beachHolderTag";

	private  BeachImageHolder beachHolder;
	ImageView mGoWithWaze;
	HorizontalListView mFriendsList ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beach_details_layout);
		findViewById(R.id.get_luckt_pic).setVisibility(View.GONE);
		findViewById(R.id.top_bar_toggle_container).setVisibility(View.GONE);

		String beachHolderJson = getIntent().getExtras().getString(BEACH_HOLDER);
		beachHolder = (BeachImageHolder) JsonUtil.serialize(beachHolderJson, BeachImageHolder.class);
		initLayout();

	}

	private void initLayout() {
		// TODO Auto-generated method stub
		TextView beachTitle   = (TextView)findViewById(R.id.beach_name);
		TextView beachDesc   = (TextView)findViewById(R.id.beach_general_description);
		TextView wether  = (TextView)findViewById(R.id.temperature_value);
		TextView flag  = (TextView)findViewById(R.id.flag_color_value);
		TextView wave = (TextView)findViewById(R.id.wave_heigh_value);
		ImageView beachImg = (ImageView)findViewById(R.id.beach_image);
		mFriendsList = (HorizontalListView)findViewById(R.id.freinds_list);
		mGoWithWaze =   (ImageView)findViewById(R.id.go_with_waze_icon);	
	
		setWazeDirection();
		beachTitle.setText(beachHolder.getImageTitle());

		beachDesc.setText(beachHolder.getDescription());
		wether.setText(beachHolder.getWhether().getDescription());
		flag.setText(beachHolder.getWhether().getFlag().getDescription());
		wave.setText(beachHolder.getWhether().getWave().getDescription());
		CustomImageLoader loader = new CustomImageLoader(beachImg,beachHolder);
		loader.loadImage();
		
		updateFriendsList();
	}

	private void updateFriendsList() {
		// TODO Auto-generated method stub
		Mapper mapper = new Mapper("friend_item_horizontal_view", 0);
		FriendsListAdapter adapter = new FriendsListAdapter(this, getUsers(), mapper);
		mFriendsList.setAdapter(adapter);
	}

	private ArrayList<ImageHolder> getUsers() {
		// TODO Auto-generated method stub
		ArrayList<ImageHolder> userList = StaticObjectHalper.getStaticUsers();
		userList = sortList(userList);
		return userList;
	}

	private ArrayList<ImageHolder> sortList(ArrayList<ImageHolder> userList) {
		ArrayList<ImageHolder> sortedList = new ArrayList<ImageHolder>();
		ArrayList<ImageHolder> noFriendsList = new ArrayList<ImageHolder>();
		
		for(ImageHolder holder : userList){
			if(Boolean.parseBoolean(holder.getExtension(ImageHolderBuilder.IS_USER_FRIEND) )){
				sortedList.add(holder);
			}else{
				noFriendsList.add(holder);
			}
		}
		
		sortedList.addAll(noFriendsList);
		
		return sortedList;
	}

	private void setWazeDirection() {
		// TODO Auto-generated method stub
		mGoWithWaze.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
					String url = "waze://?ll=31.894307,34.811493&navigate=yes";
					Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
					startActivity( intent );
				}
				catch ( ActivityNotFoundException ex  )
				{
					Intent intent =
							new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
					startActivity(intent);
				}
			}
		});

	}

	public static void startBeacheDetailsActivity(Context context,BeachImageHolder holder) {
		// TODO Auto-generated method stub

		String json = JsonUtil.deserialize(holder, BeachImageHolder.class);
		Intent i = new Intent(context, BeacheDetailsActivity.class);

		i.putExtra(BEACH_HOLDER, json);
		context.startActivity(i);
	}

}
