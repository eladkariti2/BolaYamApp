package com.app.bolayam.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.app.bolayam.R;
import com.app.bolayam.beachholder.BeachImageHolder;
import com.application.adapters.ImageBaseAdapter;
import com.application.imageholders.ImageHolder;
import com.application.utils.OSUtil;

public class BeachListAdapter extends ImageBaseAdapter {

	int selectedPosition = 0;
	
	public BeachListAdapter(Context context, ArrayList<ImageHolder> data,
			Mapper mapper) {
		super(context, data, mapper);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = super.getView(position, convertView, parent);
		
		BeachImageHolder holder = (BeachImageHolder)getItem(position);
		
		updateView(convertView,holder);
		
		convertView.setTag(holder);
		
		//convertView.setBackgroundColor(selectedPosition == position ? Color.BLACK : mContext.getResources().getColor(R.color.beach_layout_bg));
		
		return convertView;
	}

	private void updateView(View convertView, BeachImageHolder holder) {
		TextView beachName = (TextView)convertView.findViewById(R.id.beach_name);
		TextView updateText = (TextView)convertView.findViewById(R.id.last_updated_time);
		TextView mans = (TextView)convertView.findViewById(R.id.mans_ammount);
		TextView womens = (TextView)convertView.findViewById(R.id.womens_ammount);
		TextView distanceText = (TextView)convertView.findViewById(R.id.beach_distance);
		
		beachName.setText(holder.getImageTitle());
		
		double mansPrecentTemp = (double) holder.getMansNumber()/(holder.getWomenNumber() + holder.getMansNumber());
		double womensPrecentTemp = (double)holder.getWomenNumber()/(holder.getWomenNumber() + holder.getMansNumber());
		
		int mansPrecent = (int)(mansPrecentTemp*100);
		int womensPrecent = (int)(womensPrecentTemp*100);
		
		if(mansPrecent + womensPrecent != 100){
			womensPrecent++;
		}
		
		mans.setText(mansPrecent  + "%" );
		womens.setText(womensPrecent + "%");
		
	}
	


}
