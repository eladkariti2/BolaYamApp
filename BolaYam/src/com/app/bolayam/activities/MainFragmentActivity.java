package com.app.bolayam.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bolayam.R;
import com.app.bolayam.fragments.AboutFragment;
import com.app.bolayam.fragments.GetLuckyFragment;
import com.app.bolayam.fragments.HomeFragment;
import com.app.bolayam.fragments.ParkingFragmnet;
import com.app.bolayam.fragments.SettingsFragment;
import com.app.bolayam.interfaces.BolaYamLocationI;
import com.app.bolayam.listeners.APLocationListenr;
import com.app.bolayam.util.BolaYamConst;
import com.app.bolayam.view.BolaYamDrawerLayout;
import com.app.bolayam.view.SideMenuBolaYam;
import com.app.bolayam.view.SideMenuBolaYam.NavMenuItemType;
import com.application.facebook.model.FBProfilePic;
import com.application.facebook.util.FacebookUtil;
import com.application.picasoimageloader.PicasoHalper;
import com.flurry.android.ads.FlurryAdBanner;
import com.squareup.picasso.Picasso;

public class MainFragmentActivity extends FragmentActivity {

	protected BolaYamDrawerLayout mNavigationDrawer;
	protected View mToggleDrawerButton;
	private boolean isDrawerOpen = false;
	private SideMenuBolaYam sideMenu;
	private ImageView mGetLuckyImage;
	private TextView mUsername;
	APLocationListenr mLocationListener ;
	protected LocationManager mLocationManager;
	boolean mIsMapFragment;
	View mBannerContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Picasso.with(this).setIndicatorsEnabled(true);
		Picasso.with(this).setLoggingEnabled(true);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout_bola_yam);
		mBannerContainer = findViewById(R.id.banner_container);
		
		initBanner("Banner");
		
		mGetLuckyImage = (ImageView)findViewById(R.id.get_luckt_pic);

		mToggleDrawerButton = findViewById(R.id.top_bar_toggle_container);
		mToggleDrawerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleDrawerState();
			}
		});

		mNavigationDrawer = (BolaYamDrawerLayout) findViewById(R.id.content);
		ActionBarDrawerToggle navigationDrawerListener = new ActionBarDrawerToggle(
				this, 
				mNavigationDrawer,
				R.drawable.top_bar_drawer_toggle, 
				R.string.drawer_open, 
				R.string.drawer_closed) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				isDrawerOpen = false;
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				isDrawerOpen = true;
			}
		};

		mNavigationDrawer.setDrawerListener(navigationDrawerListener);

		createDrawer();
		addMainFragment(true);
		updateGame(true);
	}

	private void initBanner(String adName) {
		// TODO Auto-generated method stub
		FlurryAdBanner  mFlurryAdBanner = new FlurryAdBanner(this, (ViewGroup) mBannerContainer, adName);
		mFlurryAdBanner.displayAd();
		
	}


	// Add the home fragment to stack, if stack isn't empty then simply pop - clear its content
	public void addMainFragment(boolean isFirstFragment) {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			int id = fm.getBackStackEntryAt(0).getId();
			fm.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		else {

			Fragment main =   HomeFragment.getMainFragment();
			Bundle args = new Bundle();
			args.putBoolean(BolaYamConst.HOME_PAGE_FRAGMENT_FLAG, isFirstFragment);
			main.setArguments(args);
			addFragment(main, false, null);
		}

	}

	private void createDrawer() {	
		sideMenu = (SideMenuBolaYam)findViewById(R.id.drawer_container);
		sideMenu.init();
	}


	protected void addFragment(Fragment fragment, boolean addToBackStack,String tag) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		if (fm.getBackStackEntryCount() > 0) {
			fm.popBackStack();
		}

		if (tag != null) {
			fragmentTransaction.replace(R.id.content_frame, fragment,tag);
		}
		else {
			fragmentTransaction.replace(R.id.content_frame, fragment);
		}

		if (addToBackStack) {
			fragmentTransaction.addToBackStack(null);
		}

		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
		fragmentTransaction.commit();
	}

	private void toggleDrawerState() {
		if (isDrawerOpen) {
			mNavigationDrawer.closeDrawer(Gravity.LEFT);
		} else {
			mNavigationDrawer.openDrawer(Gravity.LEFT);
		}
	}

	public void setOnMenuClicked(NavMenuItemType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case Posts:
			addMainFragment(false);
			break;
		case Beaches:
			addMainFragment(true);
			break;
		case Parking:
			addParkingFragment();
			break;
		case Settings:
			addSettingFragment();
			break;
		case About:
			addAboutFragment();
			break;
		case Share:
			shareApp();
			break;
		default:
			break;
		}
		mNavigationDrawer.closeDrawers();
	}

	private void shareApp() {
		shareGeneral();	
	}

	private void shareGeneral() {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		String url = "http://www.one.co.il/";
		String defaultMessage = "Hi BolaYam";
		String defaultMessageBody = "Download BolaYam app.    ";


		intent.putExtra(Intent.EXTRA_SUBJECT, defaultMessage);
		intent.putExtra(Intent.EXTRA_TEXT, defaultMessageBody + "  " + url);
		intent.putExtra("sms_body", defaultMessageBody );

		intent.putExtra("com.facebook.platform.extra.contentTitle ","ELAD");
		intent.putExtra("com.facebook.platform.extra.contentDescription ","Kariti");
		
		intent.setType("text/plain");

		startActivity(Intent.createChooser(intent,"Send"));
	}

	private void addAboutFragment() {
		// TODO Auto-generated method stub
		Fragment map = AboutFragment.getInstance();
		addFragment(map, true, null);
	}

	private void addParkingFragment() {
		// TODO Auto-generated method stub
		Fragment map = ParkingFragmnet.getInstance();
		addFragment(map, true, null);
	}

	private void addSettingFragment() {
		// TODO Auto-generated method stub
		Fragment map = SettingsFragment.getInstance();
		addFragment(map, true, null);
	}

	private void addGetLucktFragment(){
		Fragment map = GetLuckyFragment.getInstance();
		addFragment(map, true, null);
	}
	
	public void stopLocationListener() {
		mLocationManager.removeUpdates(mLocationListener);
		mLocationListener = null;
	}

	public void startLocationListener(BolaYamLocationI listener) {
		// Acquire a reference to the system Location Manager
		mLocationListener = new APLocationListenr(listener);
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			buildAlertMessageNoGps();
		}

		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,mLocationListener);
	}

	private void buildAlertMessageNoGps() {
		String message = getString(R.string.gps_disable);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setPositiveButton(getString(R.string.ok_button), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

			}

		});

		builder.setNegativeButton(getString(R.string.cancel_button),  new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();		
			}
		});

		builder.setMessage(message);

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void updateGame(boolean isActive) {
		// TODO Auto-generated method stub
		mGetLuckyImage.setOnClickListener(isActive ? getLuckyClickListener : null);
	}

	
	OnClickListener getLuckyClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			addGetLucktFragment();
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	};


}
