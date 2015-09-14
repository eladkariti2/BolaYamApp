package com.app.bolayam.fragments;

import android.location.Location;
import android.support.v4.app.Fragment;

import com.app.bolayam.interfaces.BolaYamLocationI;


public class MapFragmnet extends Fragment implements BolaYamLocationI{

	@Override
	public void onLocationLoaded(Location location) {
		// TODO Auto-generated method stub
		
	}

//	LocationManager _locationManager;
//	APParkingModel _locationModel ;
//	Button _updateLocation,_saveLocation,_loadLocation;
//	View _saveContainer,_layoutContainer,_loadContainer;
//	ImageView _locationImage ;
//	EditText _locationDescriptionEditText ;	
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View layout = inflater.inflate(R.layout.locations_layout, container, false);
//		
//		_saveContainer = layout.findViewById(R.id.save_location);
//		_loadContainer = layout.findViewById(R.id.load_location);
//		_layoutContainer = layout.findViewById(R.id.choose_action_container);
//		
//		_locationDescriptionEditText = (EditText)layout.findViewById(R.id.location_description_edit_text);
//		_saveLocation = (Button)layout.findViewById(R.id.save_location_button);
//		_loadLocation = (Button)layout.findViewById(R.id.load_location_button);
//		_updateLocation = (Button)layout.findViewById(R.id.upadte_location_button);
//		
//		_locationModel = new APParkingModel();
//		return layout;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		initComponent();
//
//	}	
//
//
//
//
//	/***
//	 * function to check if the GPS is active, and to set the location manger. 
//	 */
//	private void initComponent() {
//		((MainFragmentActivity)getActivity()).startLocationListener(this);
//		
//		_saveLocation.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				_saveContainer.setVisibility(View.VISIBLE);
//				_layoutContainer.setVisibility(View.GONE);
//				_loadContainer.setVisibility(View.GONE);
//			}
//		});
//		
//		_loadLocation.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//
//				_saveContainer.setVisibility(View.GONE);
//				_layoutContainer.setVisibility(View.GONE);
//				_loadContainer.setVisibility(View.VISIBLE);
//			}
//		});
//		
//		_updateLocation.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				//Check if the device has camera, if not then hide picture container
//				PackageManager pm = getActivity().getPackageManager();
//				boolean hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
//				if(!hasCamera){
//					DataBaseUtil.saveUserLocationInDB(getActivity(),_locationModel);
//				}else{
//					saveLocationWithIMG();
//				}
//			}
//		});
//
//		_locationDescriptionEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {	
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				String name = new StringBuilder(s).toString();
//				_locationModel.setLocationDescription(name);
//			}
//		});
//	}
//
//	@Override
//	public void onLocationLoaded(Location location) {
//		// TODO Auto-generated method stub
////		Log.d("MapFragmnet", "Location - latitude: " + location.getLatitude() +", longitude: " + location.getLongitude());
////
////		((MainFragmentActivity)getActivity()).stopLocationListener();
////
////		String addressText = MapUtil.getAddrres(getActivity(),location.getLatitude(),location.getLongitude() );
////
////		TextView address = (TextView)getActivity().findViewById(R.id.address_name);
////		address.setText(addressText);
////
////		_locationModel.setAddress(addressText);
//	}
//
//
//	private void saveLocationWithIMG() {
//
//		final Dialog dialog = new Dialog(getActivity());
//		dialog.setContentView(R.layout.save_location_dialog);
//		dialog.setCanceledOnTouchOutside(true);
//
//		Button cancelButton = (Button)dialog.findViewById(R.id.cancel_button);
//		Button okButton = (Button)dialog.findViewById(R.id.ok_button);
//		_locationImage = (ImageView)dialog.findViewById(R.id.location_image);
//
//		_locationImage.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dispatchTakePictureIntent();
//
//			}
//		});
//
//		cancelButton.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				boolean result = DataBaseUtil.saveUserLocationInDB(getActivity(),_locationModel);
//				displaySaveLocationToast(result);
//				dialog.hide();
//			}
//		});
//
//		okButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {		
//				boolean isAdded = DataBaseUtil.saveUserLocationInDB(getActivity(),_locationModel);
//				displaySaveLocationToast(isAdded);		
//				dialog.hide();
//			}
//		});
//
//		//dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
//		dialog.show();
//	}
//
//
//
//
//
//	private void dispatchTakePictureIntent() {
//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//			//startActivityForResult(takePictureIntent,APConstant.REQUEST_IMAGE_CAPTURE);
//		}
//	}
//
//
//	//Handle the picture result.
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if ( resultCode == getActivity().RESULT_OK) {
//			Bundle extras = data.getExtras();
//
//			Time now = new Time();
//			now.setToNow();
//			String path = "" + now.toMillis(false);
//
//			Bitmap imageBitmap = (Bitmap) extras.get("data");
//			if (StorageUtil.saveLocationPicture(getActivity(), path,imageBitmap)){
//				_locationModel.setImage(path);
//			}
//
//			_locationImage.setImageBitmap(imageBitmap);
//
//
//		}
//	}
//
//	private void displaySaveLocationToast(boolean isAdded) {
//		if (isAdded){
//			Toast.makeText(getActivity(), getActivity().getString(R.string.location_item_added_location_success),Toast.LENGTH_LONG).show();
//		}else{
//			Toast.makeText(getActivity(), getActivity().getString(R.string.location_item_added_location_unsuccess),Toast.LENGTH_LONG).show();
//		}
//
//	}
//
//	
//	//	@Override
//	//	public void onDestroyView() {
//	//	    super.onDestroyView();
//	//	    SupportMapFragment f = (SupportMapFragment) getFragmentManager()
//	//	                                         .findFragmentById(R.id.map);
//	//	    if (f != null) 
//	//	        getFragmentManager().beginTransaction().remove(f).commit();
//	//	}
//
//	public static Fragment getInstance(){
//		MapFragmnet f = new MapFragmnet();
//		return f;
//	}

	
}
