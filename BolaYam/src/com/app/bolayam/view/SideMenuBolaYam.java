package com.app.bolayam.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.bolayam.R;
import com.app.bolayam.activities.MainFragmentActivity;
import com.app.bolayam.adapters.MenuAdapter;
import com.application.adapters.ImageBaseAdapter.Mapper;
import com.application.facebook.util.FacebookUtil;
import com.application.imageholders.ImageHolder;
import com.application.imageholders.ImageHolderBuilder;
import com.application.imageholders.ImageLoader;
import com.application.picasoimageloader.PicasoHalper;
import com.application.ui.RoundedImageView;
import com.application.utils.CustomImageLoader;
import com.application.utils.OSUtil;

public class SideMenuBolaYam extends LinearLayout {

	MainFragmentActivity mContext;
	//private BackpressObserver itemsList;

	public SideMenuBolaYam(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(OSUtil.getLayoutResourceIdentifier("side_menu_layout"),this,true);
		mContext = (MainFragmentActivity) context;
	}

	public void init(){
		
		final RoundedImageView userImage = (RoundedImageView)findViewById(OSUtil.getResourceId("user_image"));
		CustomImageLoader loader = new CustomImageLoader(userImage,new ImageHolder(FacebookUtil.getUserProfile().getUrl()));
		loader.loadImage();

		ViewStub stub = (ViewStub) findViewById(R.id.drawer_list);
		stub.setLayoutResource(R.layout.simple_navigation_list); 
		
		ArrayList<ImageHolder> holders = new ArrayList<ImageHolder>();
		for(int i =0;  i<6 ; i++ ){
			String name = "";
			String image = "";
			NavMenuItemType type = null;
			if(0 == i){
				name = mContext.getString(OSUtil.getStringResourceIdentifier("feed_page"));
				type = NavMenuItemType.Posts;
			}else if (i == 1){
				name = mContext.getString(OSUtil.getStringResourceIdentifier("beaches_page"));
				type = NavMenuItemType.Beaches;
			}else if(i == 2){
				name = mContext.getString(OSUtil.getStringResourceIdentifier("parking"));
				type = NavMenuItemType.Parking;
			}else if(i == 3){
				name = mContext.getString(OSUtil.getStringResourceIdentifier("share"));
				type = NavMenuItemType.Share;
			}else if(i == 4){
				name = mContext.getString(OSUtil.getStringResourceIdentifier("settings"));
				type = NavMenuItemType.Settings;
			}else if(i == 5){
				name = mContext.getString(OSUtil.getStringResourceIdentifier("about"));
				type = NavMenuItemType.About;
			}
			ImageHolder h = new ImageHolder(null,name, "");
			h.addExtension(ImageHolderBuilder.TYPE, type.toString());
			holders.add(h);
		}
		
		Mapper mapper = new Mapper("menu_row_layout", R.id.menu_img);
		MenuAdapter adapter = new MenuAdapter(mContext, holders, mapper);
		
		ListView list = (ListView)stub.inflate();
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					
				// TODO Auto-generated method stub
					ImageHolder holder = (ImageHolder) view.getTag();
					NavMenuItemType type = getType (holder);
					mContext.setOnMenuClicked(type);
					
			}
		});
		
	}

	protected NavMenuItemType getType(ImageHolder holder) {
		// TODO Auto-generated method stub
		String type = holder.getExtension(ImageHolderBuilder.TYPE);
		NavMenuItemType result =  NavMenuItemType.Posts.toString().equalsIgnoreCase(type) ?
				NavMenuItemType.Posts : NavMenuItemType.Beaches.toString().equalsIgnoreCase(type) ? NavMenuItemType.Beaches 
						: NavMenuItemType.Parking.toString().equalsIgnoreCase(type) ? NavMenuItemType.Parking : NavMenuItemType.Share.toString().equalsIgnoreCase(type)
								? NavMenuItemType.Share : NavMenuItemType.Settings.toString().equalsIgnoreCase(type) ? NavMenuItemType.Settings :
									 NavMenuItemType.About ; 
				return result;
	}

	public enum NavMenuItemType {
		Posts,
		Beaches,
		Parking,
		Share,
		Settings,
		About
	}
		

}
