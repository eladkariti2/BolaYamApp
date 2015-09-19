package com.app.bolayam.activities;

import android.content.Intent;

import com.application.activities.BaseSplashActivity;
import com.application.facebook.model.FBProfilePic;
import com.application.facebook.util.FacebookUtil;
import com.application.listener.AccountLoaderListener;
import com.application.loader.ModelLoader;
import com.application.models.AccountModel;
import com.application.utils.AppData;

public class IntroActivity extends BaseSplashActivity{

	@Override
	protected void onIntroLoaded() {
		loadAccount();
		
	}

	private void loadAccount() {
		// TODO Auto-generated method stub
		ModelLoader.updateOrCreateUser(new AccountLoaderListener(this));
	}

	
	private void openMainActivity() {
		Intent i = new Intent(this,MainFragmentActivity.class);
		startActivity(i);
		finish();
	}
	
	@Override
	public  void onAccountLoaded(AccountModel account){
		mAccount = account;
		AppData.getInstace().setAccount(account);
		openMainActivity();
	}
	
}
