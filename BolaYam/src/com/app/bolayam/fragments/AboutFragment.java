package com.app.bolayam.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bolayam.R;

public class AboutFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.about_layout, container, false);;
		
		
		return view;
	}

	public static Fragment getInstance() {
		Fragment f = new AboutFragment();
		return f;
	}
	
}
