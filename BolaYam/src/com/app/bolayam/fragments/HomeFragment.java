package com.app.bolayam.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bolayam.R;
import com.app.bolayam.adapters.HomeFragmentPagerAdapter;
import com.app.bolayam.util.BolaYamConst;
import com.app.bolayam.view.APTabPageIndicator;
import com.viewpagerindicator.TabPageIndicator;

public class HomeFragment extends Fragment{
	
	private ViewPager mPager;
	private APTabPageIndicator mTabsIndicator;
	private HomeFragmentPagerAdapter mMainPagerAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_fragment_layout, container, false);;
			
		mPager = (ViewPager) view.findViewById(R.id.main_pager);
		mTabsIndicator = (APTabPageIndicator)view.findViewById(R.id.main_tabs);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mMainPagerAdapter = new HomeFragmentPagerAdapter(getActivity(),getChildFragmentManager());
		mPager.setAdapter(mMainPagerAdapter);
		
		mTabsIndicator.setViewPager(mPager);
		mTabsIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		boolean openFirstFragment = getArguments().getBoolean(BolaYamConst.HOME_PAGE_FRAGMENT_FLAG);
		if(!openFirstFragment){
			mTabsIndicator.setCurrentItem(1);
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public static Fragment getMainFragment() {
		// TODO Auto-generated method stub
		Fragment f = new HomeFragment();
		return f;
	}
	

}
