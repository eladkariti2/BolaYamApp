package com.app.bolayam.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.app.bolayam.R;
import com.app.bolayam.activities.BeacheDetailsActivity;
import com.app.bolayam.adapters.BeachListAdapter;
import com.app.bolayam.beachholder.BeachHolderBuilder;
import com.app.bolayam.beachholder.BeachImageHolder;
import com.application.adapters.ImageBaseAdapter.Mapper;
import com.application.imageholders.ImageHolder;
import com.application.utils.AppData;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class BeachesFragment extends Fragment  {
	
	ListView mBeacheList;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.beaches_home_layout, container, false);;
		
		mBeacheList = (ListView)view.findViewById(R.id.beach_list);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Mapper mapper = new Mapper("beach_layout", R.id.beach_img);
		
		ArrayList<ImageHolder> holders = BeachHolderBuilder.createBeachHolders(AppData.getAPAccount().getList(),"");
//		ArrayList<ImageHolder> holders = new ArrayList<ImageHolder>();
		final BeachListAdapter adapter = new BeachListAdapter(getActivity(), holders, mapper);
		
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
		swingBottomInAnimationAdapter.setInitialDelayMillis(800);
		swingBottomInAnimationAdapter.setAbsListView(mBeacheList);
		mBeacheList.setAdapter(swingBottomInAnimationAdapter);
		
		mBeacheList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(isAdded()){
					BeachImageHolder holder = (BeachImageHolder)view.getTag();
				
					BeacheDetailsActivity.startBeacheDetailsActivity(getActivity(), holder);
				}
			}
		});
		
	}
	
}
