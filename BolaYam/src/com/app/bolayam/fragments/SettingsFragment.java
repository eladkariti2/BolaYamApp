package com.app.bolayam.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.app.bolayam.R;
import com.app.bolayam.activities.MainFragmentActivity;
import com.app.bolayam.util.BolaYamConst;
import com.app.bolayam.view.animation.ResizeHeightAnimation;
import com.application.activities.FacebookAuthenticationActivity;
import com.application.activities.FacebookLoginActivity;
import com.application.facebook.util.FacebookUtil;
import com.application.utils.OSUtil;
import com.application.utils.PreferenceUtil;

public class SettingsFragment extends Fragment {

	private static final String TAG = "SettingsFragment";
	View mFBLogoutContainer;
	View mBeachesAreaInfoButtonContainer;
	View mGameInfoButtonContainer;
	View mGameDescription;
	View mBeachAreaDescription;
	Switch mGameSwitch;
	TextView mSouthArea;
	TextView mNorthArea;
	TextView mCenterArea;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.settings_layout, container, false);;
		mFBLogoutContainer = view.findViewById(R.id.logout_fb_container);
		mGameSwitch = (Switch)view.findViewById(R.id.game_switch);;
		mSouthArea = (TextView)view.findViewById(R.id.sout_area);;
		mNorthArea = (TextView)view.findViewById(R.id.north_area);;
		mCenterArea = (TextView)view.findViewById(R.id.center_area);;
		mBeachesAreaInfoButtonContainer = view.findViewById(R.id.area_description_button);
		mBeachAreaDescription = view.findViewById(R.id.beaches_area_description);
		mGameInfoButtonContainer = view.findViewById(R.id.game_description_button);
		mGameDescription = view.findViewById(R.id.game_description);

		setFacebookLogout();
		setGameSwitch();
		initBeachesArea();


		return view;
	}

	private void initBeachesArea() {
		mSouthArea.setTag(BeachArea.south);
		mNorthArea.setTag(BeachArea.north);
		mCenterArea.setTag(BeachArea.center);
		mSouthArea.setOnClickListener(onBeachTextAreaClick);
		mNorthArea.setOnClickListener(onBeachTextAreaClick);
		mCenterArea.setOnClickListener(onBeachTextAreaClick);

		mBeachesAreaInfoButtonContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean toOpen = View.VISIBLE != mBeachAreaDescription.getVisibility();
			    setAnimation(mBeachAreaDescription,toOpen,toOpen ? OSUtil.convertDPToPixels(50) : 0);
			}
		});

		setBeachesArea();
	}



	private void setBeachesArea() {
		String chosenArea = PreferenceUtil.getInstance().getStringPref(BolaYamConst.USER_AREA_CHOOSE, BeachArea.center.toString());
		if( BeachArea.center.toString().equalsIgnoreCase(chosenArea)){
			updateToNotChosenArea(mSouthArea);
			updateToNotChosenArea(mNorthArea);
			updateToChosenArea(mCenterArea);
		}else if (BeachArea.south.toString().equalsIgnoreCase(chosenArea)){
			updateToNotChosenArea(mCenterArea);
			updateToNotChosenArea(mNorthArea);
			updateToChosenArea(mSouthArea);
		}else{
			updateToNotChosenArea(mCenterArea);
			updateToNotChosenArea(mSouthArea);
			updateToChosenArea(mNorthArea);
		}
	}

	private void updateToChosenArea(TextView textView) {
		textView.setTextColor(Color.WHITE);
		textView.setBackgroundColor(Color.parseColor("#118cff"));
	}

	private void updateToNotChosenArea(TextView textView) {
		textView.setTextColor(Color.BLACK);
		textView.setBackgroundResource(OSUtil.getDrawableResourceIdentifier("beach_area_text_bg_selector"));
	}

	private void setGameSwitch() {
		boolean isParticipant = PreferenceUtil.getInstance().getBooleanPref(BolaYamConst.IS_PARTICIAENT_IN_GAME,true);
		mGameSwitch.setChecked(isParticipant);

		mGameSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferenceUtil.getInstance().setBooleanPref(BolaYamConst.IS_PARTICIAENT_IN_GAME,isChecked);
				updateServer(isChecked);
			}


		});

		mGameInfoButtonContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean toOpen = View.VISIBLE != mGameDescription.getVisibility();
			    setAnimation(mGameDescription,toOpen,toOpen ? OSUtil.convertDPToPixels(50) : 0);
			}
		});
	}

	private void updateServer(boolean isChecked) {
		// TODO Auto-generated method stub
		Log.d(TAG, "user change game participant to : " + isChecked );
	}

	private void setFacebookLogout() {
		// TODO Auto-generated method stub
		mFBLogoutContainer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FacebookUtil.clearToken();
				Intent i = new Intent(getActivity(),FacebookLoginActivity.class);
				startActivityForResult(i,0);				
			}
		});
	}

	protected  void setAnimation(final View view,final boolean toOpen, int heightInPixels) {
		
		ResizeHeightAnimation resizeAnim = new ResizeHeightAnimation(view,heightInPixels);
		view.setVisibility(View.VISIBLE);
		resizeAnim.setFillAfter(true);
		resizeAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
					
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				if(!toOpen){
					view.setVisibility(View.GONE);
				}
			}
		});
		resizeAnim.setDuration(1000);

		view.setAnimation(resizeAnim);
		resizeAnim.start();
		
		//view.setVisibility(toOpen ? View.VISIBLE : View.GONE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == FacebookAuthenticationActivity.FACEBOOK_AUTH_RESULT){
			((MainFragmentActivity)getActivity()).addMainFragment(true);
		}else{
			getActivity().finish();
		}
	}

	public static Fragment getInstance() {
		Fragment f = new SettingsFragment();
		return f;
	}


	public enum BeachArea{
		center,north,south
	}

	OnClickListener onBeachTextAreaClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BeachArea area = (BeachArea) v.getTag();

			switch (area) {
			case center:
				PreferenceUtil.getInstance().setStringPref(BolaYamConst.USER_AREA_CHOOSE, BeachArea.center.toString());
				break;
			case north:
				PreferenceUtil.getInstance().setStringPref(BolaYamConst.USER_AREA_CHOOSE, BeachArea.north.toString());
				break;
			case south:
				PreferenceUtil.getInstance().setStringPref(BolaYamConst.USER_AREA_CHOOSE, BeachArea.south.toString());
				break;

			default:
				break;
			}
			setBeachesArea();
		}
	};
}
