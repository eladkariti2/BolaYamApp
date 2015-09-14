package com.app.bolayam.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.bolayam.R;
import com.app.bolayam.activities.MainFragmentActivity;
import com.app.bolayam.adapters.FeedListAdapter;
import com.application.activities.FeedPostActivity;
import com.application.activities.FeedPostDetailsActivity;
import com.application.adapters.ImageBaseAdapter.Mapper;
import com.application.datahandler.FBEventDataHandler;
import com.application.facebook.listener.FeedEventI;
import com.application.facebook.model.FBFeed;
import com.application.facebook.model.FBModel;
import com.application.facebook.model.FBPost;
import com.application.imageholders.FBPostHolder;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageHolderBuilder;
import com.application.messagebroker.APBrokerNotificationTypes;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class FeedFragment extends Fragment implements FeedEventI{

	ListView mfeedList;
	View mPostAction;
	View mPhotoAction;
	ProgressBar mProgressBar;
	View mPostEmptyContaoner;

	FeedListAdapter mFeedAdapter = null;
	Timer mFeedLoaderTimer;
	FBEventDataHandler mFBDataHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mFBDataHandler = new FBEventDataHandler(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.feed_home_layout, container, false);;
		mPostEmptyContaoner = view.findViewById(R.id.feed_empty_container);
		mfeedList = (ListView)view.findViewById(R.id.feed_list);
		mPostAction = view.findViewById(R.id.post_action);
		mPhotoAction = view.findViewById(R.id.photo_action); 
		mProgressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		if(mFeedAdapter != null && mFeedAdapter.hasData()){
			mProgressBar.setVisibility(View.GONE);
			mPostEmptyContaoner.setVisibility(mFeedAdapter.hasData() ? View.GONE : View.VISIBLE);
			mfeedList.setAdapter(mFeedAdapter);
		}

		mPostAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeedPostActivity.startFeedPostActivity(getActivity(), false);
			}
		});
		mPhotoAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FeedPostActivity.startFeedPostActivity(getActivity(), true);
			}
		});

		mfeedList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FeedPostDetailsActivity.startFeedPostDetailsActivity(getActivity(),(FBPostHolder)view.getTag());
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startTimmer();
	}

	private void startTimmer() {
		if(mFeedLoaderTimer == null){
			mFeedLoaderTimer = new Timer();
		}
		mFeedLoaderTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(isAdded()){
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mFBDataHandler.loadFeed();	
						}
					});	
				}
			}

		}, 0,30 * 1000);		
	}


	public void stopTimmer(){
		if(mFeedLoaderTimer != null){
			mFeedLoaderTimer.cancel();
			mFeedLoaderTimer = null;
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		stopTimmer();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void onFeedLoaded(ArrayList<ImageHolder> holders) {
		if(isAdded()){
			if(mFeedAdapter == null){
				Mapper mapper = new Mapper("facebook_feed_entry", R.id.user_image);
				mFeedAdapter = new FeedListAdapter(getActivity(), holders, mapper);
				SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(mFeedAdapter);
				swingBottomInAnimationAdapter.setInitialDelayMillis(800);
				swingBottomInAnimationAdapter.setAbsListView(mfeedList);
				mfeedList.setAdapter(swingBottomInAnimationAdapter);
			}else{
				mFeedAdapter.updateFeed(holders);
			}
			mProgressBar.setVisibility(View.GONE);
		}
	}


	public static Fragment getHomeFragment(	MainFragmentActivity mainFragmentActivity) {
		// TODO Auto-generated method stub
		Fragment homeFragmen = new FeedFragment();
		return homeFragmen;
	}

	@Override
	public void onLoaded(FBModel model) {
		// TODO Auto-generated method stub
		if(model!= null){
			List<FBPost> posts =   ((FBFeed)model).getPosts();
			ArrayList<ImageHolder> holders = new ArrayList<ImageHolder>();
			if(posts != null){
				holders = ImageHolderBuilder.createFBPostHolder(posts);
				onFeedLoaded(holders);
			}
		}

		if(mFeedAdapter != null && mFeedAdapter.hasData()){
			mPostEmptyContaoner.setVisibility( View.GONE );
			mProgressBar.setVisibility(View.GONE);
		}else{
			mPostEmptyContaoner.setVisibility( View.VISIBLE );
		}
	}

	@Override
	public void onEror() {
		// TODO Auto-generated method stub
		if(mFeedAdapter == null || !mFeedAdapter.hasData()){
			mPostEmptyContaoner.setVisibility( View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
		}
	}

}
