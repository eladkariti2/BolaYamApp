package com.app.bolayam;

import java.util.Locale;

import com.application.CustomApplication;
import com.application.utils.AppData;
import com.flurry.android.FlurryAgent;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class BolaYam extends CustomApplication{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setApplicationLocale(new Locale("he"));
		FlurryAgent.setLogEnabled(true);
		FlurryAgent.setLogLevel(2);
		FlurryAgent.init(this,getResources().getString(R.string.flurry_api_key));//"GZ97GPKXH3R3RRF99JRG");
	}

}
