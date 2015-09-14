package com.app.bolayam.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bolayam.view.SideMenuBolaYam.NavMenuItemType;
import com.application.adapters.ImageBaseAdapter;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageHolderBuilder;
import com.application.picasoimageloader.PicasoHalper;
import com.application.utils.OSUtil;

public class MenuAdapter extends ImageBaseAdapter {

	public MenuAdapter(Context context, ArrayList<ImageHolder> data,
			Mapper mapper) {
		super(context, data, mapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = super.getView(position, convertView, parent);

		TextView title = (TextView)v.findViewById(OSUtil.getResourceId("menu_text"));
		ImageView image = (ImageView)v.findViewById(OSUtil.getResourceId("menu_img"));

		title.setText(getItem(position).getImageTitle());
		
		image.setImageResource(getImageResource(getItem(position)));
		
		return v;
	}

	private int getImageResource(ImageHolder item) {
		// TODO Auto-generated method stub
		int resourceID = 0;
		String type = item.getExtension(ImageHolderBuilder.TYPE);
		if(NavMenuItemType.About.toString().equals(type)){
			resourceID = OSUtil.getDrawableResourceIdentifier("ic_action_about");
		}else if(NavMenuItemType.Settings.toString().equals(type)){
			resourceID = OSUtil.getDrawableResourceIdentifier("ic_action_settings");
		}else if(NavMenuItemType.Parking.toString().equals(type)){
			resourceID = OSUtil.getDrawableResourceIdentifier("ic_action_place");
		}else if(NavMenuItemType.Share.toString().equals(type)){
			resourceID = OSUtil.getDrawableResourceIdentifier("ic_action_share");
		}else if(NavMenuItemType.Posts.toString().equals(type)){
			resourceID = OSUtil.getDrawableResourceIdentifier("ic_action_about");
		}else if(NavMenuItemType.Beaches.toString().equals(type)){
			resourceID = OSUtil.getDrawableResourceIdentifier("ic_action_about");
		}
		
		
		return resourceID;
	}

}
