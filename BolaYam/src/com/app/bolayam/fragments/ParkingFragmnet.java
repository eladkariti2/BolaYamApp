package com.app.bolayam.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Intent;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bolayam.R;
import com.app.bolayam.util.BolaYamConst;
import com.application.CustomApplication;
import com.application.models.GenericModel;
import com.application.models.ParkingModel;
import com.application.picasoimageloader.PicasoHalper;
import com.application.text.APConstant;
import com.application.utils.MapUtil;
import com.application.utils.OSUtil;
import com.application.utils.PreferenceUtil;
import com.application.utils.StringUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

public class ParkingFragmnet extends Fragment implements ConnectionCallbacks,
		OnConnectionFailedListener {

	ImageView mAddPhoto;
	ImageView mSaveImage;
	ImageView mParkingImage;
	TextView mParkingTitle;
	TextView mGoToParking;
	EditText mDescription;
	View mProgressBar;
	View mTopContainer;
	View mBottomContainer;

	Uri mImageUri = null;
	GoogleApiClient mGoogleApiClient;
	ParkingModel mCurrentParking;
	ParkingModel mUserParking;
	private Bitmap mImageToPost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.parking_fragmnet_layout,
				container, false);
		;
		mSaveImage = (ImageView) view.findViewById(R.id.save_location);
		mParkingImage = (ImageView) view.findViewById(R.id.parking_pic);
		mAddPhoto = (ImageView) view.findViewById(R.id.add_photo);
		mParkingTitle = (TextView) view.findViewById(R.id.paking_title);
		mGoToParking = (TextView) view.findViewById(R.id.go_to_parking);
		mDescription = (EditText) view.findViewById(R.id.paking_text);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		mTopContainer = view.findViewById(R.id.parking_top_container);
		mBottomContainer = view.findViewById(R.id.parking_bottom_container);

		mTopContainer.setVisibility(View.GONE);
		mBottomContainer.setVisibility(View.GONE);
		
		return view;
	}

	private void initParking() {
		// TODO Auto-generated method stub
		String parkingJson = PreferenceUtil.getInstance().getStringPref(
				BolaYamConst.USER_PARKING, null);
		if (StringUtil.isEmpty(parkingJson)) {
			mCurrentParking = new ParkingModel();
			mUserParking = new ParkingModel();
		} else {
			mUserParking = new Gson().fromJson(parkingJson, ParkingModel.class);
			updateParkingView();
		}
	}

	private void updateParkingView() {
		// TODO Auto-generated method stub
		mParkingTitle.setText(mUserParking.getName());
		mDescription.setText(mUserParking.getDescription());
		
		
		
		try {
			mImageUri = Uri.parse(mUserParking.getImage());
			mImageToPost = MediaStore.Images.Media.getBitmap(
			        getActivity().getContentResolver(), mImageUri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initParking();
		buildGoogleApiClient();
	
		mSaveImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCurrentParking.setDescription(mDescription.getText().toString());
				String parkingJson = new Gson().toJson(mCurrentParking);
				PreferenceUtil.getInstance().setStringPref(
						BolaYamConst.USER_PARKING, parkingJson);

				Toast.makeText(
						CustomApplication.getAppContext(),
						getActivity()
								.getString(
										OSUtil.getStringResourceIdentifier("parking_saved")),
						Toast.LENGTH_SHORT).show();
				initParking();
			}
		});
		
		mAddPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dispatchTakePictureIntent(true);
			}
		});
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}

	}

	private void setUserParkingImage() {
		// TODO Auto-generated method stub

	}

	private void dispatchTakePictureIntent(boolean fromCamera) {
		if (fromCamera) {
			ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, "Beach_lcation");
			values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
			mImageUri = getActivity().getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			
			OSUtil.launchCamera(this,getActivity(), mImageUri);
		} else {
			OSUtil.launchGallery(getActivity());
		}
	}

	public void navigateWithWalkMode(String longitude, String latitude) {
		Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + ","
				+ latitude + "&mode=w");
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(mapIntent);
		} else {
			// open browser
			String url = "http://maps.google.com/maps?saddr=START_ADD&amp;daddr=DEST_ADD&amp;ll=START_ADD";

			startActivity(new Intent(Intent.ACTION_VIEW)
					.setData(Uri.parse(url)));
		}
	}

	public static Fragment getInstance() {
		Fragment f = new ParkingFragmnet();
		return f;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == getActivity().RESULT_OK) {
			
			if(requestCode == APConstant.REQUEST_IMAGE_CAPTURE_CAMERA){
				 try {
					 mImageToPost = MediaStore.Images.Media.getBitmap(
                                getActivity().getContentResolver(), mImageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
			}else if (requestCode == APConstant.REQUEST_IMAGE_CAPTURE_GALLERY){
				Uri imageUri = data.getData();
				try {
					mImageToPost = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			updateParkingImage();
			mParkingImage.setImageBitmap(mImageToPost);
			
		}
		else{
			Log.e("ParkingFragment", "Failed to captuer image");
		}
	}

	private void updateParkingImage() {
		// TODO Auto-generated method stub
		mCurrentParking.setImage(mImageUri.toString());
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			Location location = LocationServices.FusedLocationApi
					.getLastLocation(mGoogleApiClient);
			if (location != null) {
				String address = MapUtil.getAddrres(getActivity(),
						location.getLatitude(), location.getLongitude());
				mParkingTitle.setText(address);
				mCurrentParking.setName(address);
				mCurrentParking.setLatitude("" + location.getLatitude());
				mCurrentParking.setLongitude("" + location.getLongitude());
				
			}
		}
		stopLoader();
	}

	private void stopLoader() {
		// TODO Auto-generated method stub
		mTopContainer.setVisibility(View.VISIBLE);
		mBottomContainer.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}
}
