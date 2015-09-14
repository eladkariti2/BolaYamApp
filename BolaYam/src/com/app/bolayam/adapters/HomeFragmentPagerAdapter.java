package com.app.bolayam.adapters;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.bolayam.R;
import com.app.bolayam.beachholder.BeachImageHolder;
import com.app.bolayam.fragments.BeachesFragment;
import com.app.bolayam.fragments.FeedFragment;
import com.viewpagerindicator.IconPagerAdapter;

public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter{

	private Context mContext;
	private String[] mTitlesText = new String[2];
	private Integer[] mTitlesImages = new Integer[2];
	private Map<String, Fragment> mPageReferenceMap = new HashMap<String, Fragment>();
	
	BeachImageHolder mBeachHolder;
	
	public HomeFragmentPagerAdapter(Context context,FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		mContext = context;
		
		mTitlesText[0] = mContext.getResources().getString(R.string.home_feed);
		mTitlesText[1] = mContext.getResources().getString(R.string.feed_list);
		
		mTitlesImages[0] = R.drawable.home_icon;
		mTitlesImages[1] = R.drawable.boat_icon;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		if (!mPageReferenceMap.containsKey(position + "")){
			fragment = getNewEpgPageFragmentInstance(position);
			mPageReferenceMap.put(position + "", fragment);
		}else{
			fragment = mPageReferenceMap.get(position + "");
		}
		
		return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		String title = "";
		title = mTitlesText[position];
		return title;
	}

	@Override
	public int getIconResId(int index) {
		int resID = mTitlesImages[index];
		return resID;
	}	
	
	public Fragment getNewEpgPageFragmentInstance(int position){
		Fragment f = null;
		switch (position) {
		case 0:
			 f = new BeachesFragment();
			break;
		case 1:
			f = new FeedFragment();
			break;
	
		default:
			break;
		}
		return f; 
	}

}
