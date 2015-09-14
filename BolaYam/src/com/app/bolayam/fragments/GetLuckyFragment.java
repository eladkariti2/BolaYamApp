package com.app.bolayam.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bolayam.R;
import com.app.bolayam.activities.MainFragmentActivity;
import com.application.ui.APWebView;

public class GetLuckyFragment extends Fragment {

	APWebView mWebView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.get_lucky_layout, container, false);;
		
		mWebView = (APWebView) view.findViewById(R.id.get_lucky_webview);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mWebView.loadUrl(getActivity(),"https://www.google.co.il/",false);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isAdded()){
			((MainFragmentActivity)getActivity()).updateGame(false);
		}
	}
	
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		if(isAdded()){
			((MainFragmentActivity)getActivity()).updateGame(true);
//		}
	}
	
	public static Fragment getInstance() {
		Fragment f = new GetLuckyFragment();
		return f;
	}

}
