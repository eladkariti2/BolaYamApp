package com.app.bolayam.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.app.bolayam.activities.MainFragmentActivity;
import com.app.bolayam.interfaces.BolaYamLocationI;

public class APLocationListenr  implements LocationListener{

	BolaYamLocationI _context;
	
	public APLocationListenr(BolaYamLocationI listener){
		_context = listener;
	}
	
	@Override
	public void onLocationChanged(Location location) {
			
		_context.onLocationLoaded(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
