package com.shoaibnwar.iwsm.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.shoaibnwar.iwsm.Adapters.CustomeAdapterForImage;
import com.shoaibnwar.iwsm.Adapters.PlaceArrayAdapter;
import com.shoaibnwar.iwsm.Listeners.AsyncTaskCompleteListener;
import com.shoaibnwar.iwsm.Models.ProductModel;
import com.shoaibnwar.iwsm.R;
import com.shoaibnwar.iwsm.Services.HttpRequester;
import com.shoaibnwar.iwsm.Services.UserService;
import com.shoaibnwar.iwsm.Utils.Logger;
import com.shoaibnwar.iwsm.Utils.ServiceProgressDialog;
import com.shoaibnwar.iwsm.Utils.SharedPrefs;
import com.shoaibnwar.iwsm.Utils.Urls;
import com.shoaibnwar.iwsm.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class MainMaps extends FragmentActivity implements OnMapReadyCallback, AsyncTaskCompleteListener,
        GoogleMap.OnMarkerClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    HeatmapTileProvider mProvider;
    SupportMapFragment mapFragment;


    UserService userService;
    GPSTracker gpsTracker;
    private static final int ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    String req_id;

    private Geocoder geocoder;
    private List<Address> addresses;

    Double latitude, longitude;

    SharedPreferences sharedPreferences;

    JSONArray jsonArray;
    TextView markerTitle, markerGuests, markerPrice, heatMapTV;
    ImageView markerImg;
    TextView cabIntensity, rvIntensity, rrIntensity;
    RelativeLayout markerLayout;
    Animation rotation;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(31.398160, 74.180831), new LatLng(31.430610, 74.972090));
    protected LatLng mCenterLocation = new LatLng(31.398160, 74.180831);
    boolean isServiceInProgressFlag;

    //edited by shoaib anwar
    RecyclerView rc_list;
    CustomeAdapterForImage customeAdapterForImage;
    LinearLayoutManager linearLayoutManager;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

    RelativeLayout rl_list;
    Button bt_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);

        sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, Context.MODE_PRIVATE);

        isServiceInProgressFlag = true;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        rotation = AnimationUtils.loadAnimation(MainMaps.this, R.anim.rotate);
        rotation.setFillAfter(true);

        gpsTracker = new GPSTracker(this);
        userService = new UserService(MainMaps.this);
        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 10, 280);

        View compassButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
        RelativeLayout.LayoutParams crlp = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();
// position on right bottom
        crlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        crlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        crlp.setMargins(0, 200, 10, 0);

                mGoogleApiClient = new GoogleApiClient.Builder(MainMaps.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        ArrayList<LatLng> locations = generateLocations();
        mProvider = new HeatmapTileProvider.Builder().data(locations).build();
        mProvider.setRadius(HeatmapTileProvider.DEFAULT_RADIUS);

        //edit by shoaib anwar
        rc_list = findViewById(R.id.rc_list);
        rc_list.bringToFront();
        linearLayoutManager = new LinearLayoutManager(MainMaps.this, LinearLayoutManager.HORIZONTAL, false);
        rc_list.setLayoutManager(linearLayoutManager);


        rl_list = (RelativeLayout) findViewById(R.id.rl_list);
        bt_list = (Button) findViewById(R.id.bt_list);



        tvHideShowClickListener();
        tvListClickHandler();



    } // onCreate finishes



    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }

    private ArrayList<LatLng> generateLocations() {
        ArrayList<LatLng> locations = new ArrayList<LatLng>();
        double lat;
        double lng;
        Random generator = new Random();
        for (int i = 0; i < 500; i++) {
            lat = generator.nextDouble() / 3;
            lng = generator.nextDouble() / 3;
            if (generator.nextBoolean()) {
                lat = -lat;
            }
            if (generator.nextBoolean()) {
                lng = -lng;
            }
            locations.add(new LatLng(mCenterLocation.latitude + lat, mCenterLocation.longitude + lng));
        }

        return locations;
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e("Location", "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);

            Log.i("name", place.getName().toString());
            Log.i("coordinates", place.getLatLng().toString());

            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));

            geocoder = new Geocoder(MainMaps.this, Locale.getDefault());
            double latitude = place.getLatLng().latitude;
            double longitude = place.getLatLng().longitude;
            Log.e("TAG", "the late are here: " + latitude);
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            } catch (IOException e) {
                e.printStackTrace();
            }

            String address = addresses.get(0).getAddressLine(0);
            Log.e("TAG", "the address of latlng is: " + address);
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(address).snippet("Your pick up location"));

            Utils.hideSoftKeyboard(MainMaps.this);

//            mAutocompleteTextView.clearFocus();

            /*//HttpRequest
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("url", Urls.GET_ASSETS);
            map.put("Lat", String.valueOf(latitude));
            map.put("Long", String.valueOf(longitude));
            map.put("Radius", "30");
            map.put("AssetType", SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.CURRENT_TAB));
            new HttpRequester(MainMaps.this, map, 1, MainMaps.this);*/
        }
    };




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
     /*   mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                int visibility = markerLayout.getVisibility();
                if (visibility == View.VISIBLE) {
                    markerLayout.setVisibility(View.GONE);
                }
            }
        });*/
        SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.CURRENT_TAB, "all");
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        final LatLng latlng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        //mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.user_pin)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.latitude, gpsTracker.longitude), 15));

//        Toast.makeText(MainMaps.this, "" + mMap.getCameraPosition().target, Toast.LENGTH_SHORT).show();

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {


                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    }
                    else {
                        if (ActivityCompat.checkSelfPermission(MainMaps.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            return;
                        }
                        manager.addGpsStatusListener(new GpsStatus.Listener() {
                            @Override
                            public void onGpsStatusChanged(int event) {
                                switch(event) {
                                    case GpsStatus.GPS_EVENT_STARTED:
                                        Toast.makeText(MainMaps.this, "Please wait for GPS to get connected...", Toast.LENGTH_SHORT).show();
                                        break;
                                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                                        Toast.makeText(MainMaps.this, "GPS locked! You can now find your current location.", Toast.LENGTH_SHORT).show();
                                        manager.removeGpsStatusListener(this);
                                        break;
                                }
                            }

                        });
                    }
//                Toast.makeText(MainMaps.this, ""+googleMap.getCameraPosition().target, Toast.LENGTH_SHORT).show();


                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(MainMaps.this, Locale.getDefault());
                    try {
                        latitude = googleMap.getCameraPosition().target.latitude;
                        longitude = googleMap.getCameraPosition().target.longitude;
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null) {
                        try {
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName();
                            Log.d("address is::", address);


                            SharedPreferences mySharedPRes = getSharedPreferences("tem", 0);
                            SharedPreferences.Editor editor = mySharedPRes.edit();
                            editor.putString("address", knownName  + ", "+city);
                            editor.putString("city", address);
                            editor.putString("lat", latitude.toString());
                            editor.putString("lng", longitude.toString());
                            editor.commit();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
/*
                    //HttpRequest
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("url", Urls.GET_ASSETS);
                    map.put("Lat", String.valueOf(latitude));
                    map.put("Long", String.valueOf(longitude));
                    map.put("Radius", "30");
                    map.put("AssetType", SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.CURRENT_TAB));
                    new HttpRequester(MainMaps.this, map, 1, MainMaps.this);
//                BaseService.handleProgressBar(true);*/
                }


        });

        tempDataLoading(latitude, longitude);


    } // onMapReady finished



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        if(serviceCode == 1)
        {
            isServiceInProgressFlag = false;

            try {
                Logger.log(response);
                mMap.clear();
//                BaseService.dismissProgressDialog();
                JSONObject obj = new JSONObject(response);
                jsonArray = new JSONArray();

                if (dataList.size()>0){
                    dataList.clear();
                }
                jsonArray = obj.getJSONArray("Table");
                for (int i=0; i<jsonArray.length() ; i++)
                {
                    String viewId = jsonArray.getJSONObject(i).get("Vid").toString();
                    String lat = jsonArray.getJSONObject(i).get("V_Lat").toString();
                    String lng = jsonArray.getJSONObject(i).get("V_Long").toString();
                    String objType = jsonArray.getJSONObject(i).get("VType").toString();
                    String guests = jsonArray.getJSONObject(i).get("V_MaxGuest").toString();
                    String price = jsonArray.getJSONObject(i).get("Price").toString();
                    //set marker

                    ///by shoaib anwar

                    JSONObject finalobject = jsonArray.getJSONObject(i);

                    String Vid = finalobject.getString("Vid");
                    String VType = finalobject.getString("VType");
                    String V_ThumbImg = finalobject.getString("V_ThumbImg");
                    String Bed = finalobject.getString("Bed");
                    String V_MaxGuest = finalobject.getString("V_MaxGuest");
                    String V_Model = finalobject.getString("V_Model");
                    String V_Title = finalobject.getString("V_Title");
                    String V_City = finalobject.getString("V_City");
                    String V_Lat = finalobject.getString("V_Lat");
                    String V_Long = finalobject.getString("V_Long");
                    String Count = finalobject.getString("Count");
                    String Distance = finalobject.getString("Distance");
                    String Price = finalobject.getString("Price");

                    HashMap<String, String> resltdata = new HashMap<>();
                    resltdata.put("Vid", Vid);
                    resltdata.put("VType", VType);
                    resltdata.put("V_ThumbImg", V_ThumbImg);
                    resltdata.put("vid", Bed);
                    resltdata.put("V_MaxGuest", V_MaxGuest);
                    resltdata.put("V_Model", V_Model);
                    resltdata.put("V_Title", V_Title);
                    resltdata.put("V_City", V_City);
                    resltdata.put("V_Lat", V_Lat);
                    resltdata.put("V_Long", V_Long);
                    resltdata.put("Count", Count);
                    resltdata.put("Distance", Distance);
                    resltdata.put("Price", Price);

                    dataList.add(resltdata);

                    ///end of edit by shoib anwar

                    LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    if (SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.CURRENT_TAB).equals("all"))
                    {


                        if (objType.equals("Cab"))
                            //mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.googlecab)).title(jsonArray.getJSONObject(i).get("V_Title").toString())).setTag(viewId);
                        if (objType.equals("rr"))
                            //mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.googlehome)).title(jsonArray.getJSONObject(i).get("V_Title").toString())).setTag(viewId);
                        if (objType.equals("rv"));
                            //mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.googlerv)).title(jsonArray.getJSONObject(i).get("V_Title").toString())).setTag(viewId);
                    }

                }

                int atest =  0;
                int rrNumbers = 0;
                int rvNumbers = 0;
                float lowestDistance = 100;
                for(int i=0; i<dataList.size();i++){
                    String cabsNumbers = dataList.get(i).get("VType");
                    if(cabsNumbers.equals("Cab")){
                        String distance = dataList.get(i).get("Distance");
                        float dis = Float.valueOf(distance);
                        if (dis<lowestDistance){
                            lowestDistance = dis;
                        }
                        atest++;
                    }
                    if(cabsNumbers.equals("rr")){
                        rrNumbers++;
                    }
                    if(cabsNumbers.equals("rv")){
                        rvNumbers++;
                    }


                }

                Log.e("TAG", "the number of asset availabel ares Cab: " + atest);
                Log.e("TAG", "the number of asset availabel ares rr: " + rrNumbers);
                Log.e("TAG", "the number of asset availabel ares rv: " + rvNumbers);
                Log.e("TAG", "lowest distance is: " + lowestDistance);

                int time = (int)lowestDistance;
                if (time<=0){
                    cabIntensity.setText("1");
                }else {
                    time= time+1;
                    cabIntensity.setText(""+time);
                }


                rrIntensity.setText(""+rrNumbers);
                rvIntensity.setText(""+rvNumbers);



                //edit by shoaib anwar
                customeAdapterForImage = new CustomeAdapterForImage(getApplicationContext(), dataList);
                rc_list.setAdapter(customeAdapterForImage);



                //end of eidt by shoaib anwar

                //HttpRequest
                HashMap<String, String> map3 = new HashMap<String, String>();
                map3.put("url", Urls.GET_REQUEST_INTENSITY);
                map3.put("Lat", String.valueOf(latitude));
                map3.put("Long", String.valueOf(longitude));
                new HttpRequester(MainMaps.this, map3, 5, MainMaps.this);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(serviceCode == 5)
        {
            JSONObject obj = null;
            JSONArray intensityJsonArray = null;
            try {
                obj = new JSONObject(response);
                intensityJsonArray = new JSONArray();
                intensityJsonArray = obj.getJSONArray("Table");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(intensityJsonArray.length() > 0)
            {
                try {
                    String cab_intensity = intensityJsonArray.getJSONObject(0).get("Cab Intensity").toString();
                    String rr_intensity = intensityJsonArray.getJSONObject(0).get("Room Intensity").toString();
                    String rv_intensity = intensityJsonArray.getJSONObject(0).get("RV Intensity").toString();

                  /*  cabIntensity.setText(cab_intensity);
                    rrIntensity.setText(rr_intensity);
                    rvIntensity.setText(rv_intensity);*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if(serviceCode == 2)
        {
            ServiceProgressDialog.dismissDialog();
            Logger.log(response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray jsonArray = new JSONArray();
                jsonArray = obj.getJSONArray("Table");
                req_id = jsonArray.getJSONObject(0).get("RequestId").toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //show Dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.app_name);
            alert.setMessage("Your request received successfully Please wait for response of Driver.");
            Toast.makeText(MainMaps.this, ""+req_id, Toast.LENGTH_SHORT).show();
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }

        if(serviceCode == 3)
        {
            //Alert Dialog for showing information...
            ServiceProgressDialog.dismissDialog();
            Logger.log(response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray jsonArray = new JSONArray();
                jsonArray = obj.getJSONArray("Table");
                String name = jsonArray.getJSONObject(0).get("DriverName").toString();
                String contactNo = jsonArray.getJSONObject(0).get("DriverMobile").toString();
                String V_regNo = jsonArray.getJSONObject(0).get("VRegNo").toString();
                String V_model = jsonArray.getJSONObject(0).get("V_Model").toString();
                Toast.makeText(MainMaps.this, "Found "+name, Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Driver found!");
                alert.setMessage("" + Html.fromHtml("Name: " + name + "<br>" + "Contact No: " + contactNo + "<br>" + "Vehicle Reg. No: " + V_regNo + "<br>" + "Vehicle model: " + V_model));
                //Toast.makeText(MainMaps.this, ""+req_id, Toast.LENGTH_SHORT).show();
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    /*                                            //show Dialog
                                                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                                                alert.setTitle(R.string.app_name);
                                                alert.setMessage("Your request received successfully Please wait for response of Driver.");
                                                Toast.makeText(MainMaps.this, ""+req_id, Toast.LENGTH_SHORT).show();
                                                alert.setPositiveButton("OK", null);
                                                alert.show();*/
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int position = Integer.parseInt(marker.getTag().toString());
//                Toast.makeText(MainMaps.this, ""+position, Toast.LENGTH_SHORT).show();
                for (int i=0; i<jsonArray.length() ; i++) {
                   /* try {
                        detailViewId = jsonArray.getJSONObject(i).get("Vid").toString();
                        if(detailViewId.equals(String.valueOf(position)))
                        {
                            detailType = jsonArray.getJSONObject(i).get("VType").toString();
                            detailTitle = jsonArray.getJSONObject(i).get("V_Title").toString();
                            detailImgURL = jsonArray.getJSONObject(i).get("V_ThumbImg").toString();
                            detailGuests = jsonArray.getJSONObject(i).get("V_MaxGuest").toString();
                            detailPrice = jsonArray.getJSONObject(i).get("Price").toString();

                            //setmarker image through sownloading URL
                            new DownloadImageTask(markerImg).execute(detailImgURL);
                            markerTitle.setText(detailTitle);
                            markerGuests.setText(detailGuests);
                            markerPrice.setText(detailPrice);
                            markerLayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
                return false;
            }
        });
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            try {
                bmImage.setImageBitmap(result);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void showLocationOffDialog() {

        AlertDialog locationAlertDialog;
        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                MainMaps.this);
        gpsBuilder.setCancelable(false);
        gpsBuilder
                .setTitle("No GPS")
                .setMessage("Please turn GPS services ON")
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                dialog.dismiss();
                                Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(viewIntent);

                            }
                        })

                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                dialog.dismiss();
                                finish();
                            }
                        });
        locationAlertDialog = gpsBuilder.create();
        locationAlertDialog.show();
    }

    private void showInternetDialog() {
        AlertDialog internetDialog;
        AlertDialog.Builder internetBuilder = new AlertDialog.Builder(MainMaps.this);
        internetBuilder.setCancelable(false);
        internetBuilder
                .setTitle("No Internet")
                .setMessage("Please turn Internet services ON")
                .setPositiveButton("Enable 3G/4G",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Enable Wifi",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // User pressed Cancel button. Write
                                // Logic Here
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        })
                .setNeutralButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                finish();
                            }
                        });
        internetDialog = internetBuilder.create();
        internetDialog.show();
    }


    private void tvHideShowClickListener(){


    }


    private void tempDataLoading(double latitude, double longitude){

        for (int i=0; i<6; i++){

            HashMap<String, String> resltdata = new HashMap<>();
            resltdata.put("Vid", String.valueOf(i));
            resltdata.put("VType", String.valueOf(i));
            resltdata.put("V_ThumbImg", "https://firebasestorage.googleapis.com/v0/b/gotukxisupply.appspot.com/o/Campaigns%2F5790b821-5a49-4d61-870a-511c46a097cd?alt=media&token=80492100-b454-465b-b3e8-5079c88f2544");
            resltdata.put("vid", String.valueOf(i));
            resltdata.put("V_MaxGuest", String.valueOf(i));
            resltdata.put("V_Model", String.valueOf(i));
            resltdata.put("V_Title", String.valueOf(i+1));
            resltdata.put("V_City", String.valueOf(i));
            resltdata.put("V_Lat", String.valueOf(i));
            resltdata.put("V_Long", String.valueOf(i));
            resltdata.put("Count", String.valueOf(i));
            resltdata.put("Distance", String.valueOf(i));

            resltdata.put("Price", String.valueOf(i));
            latitude  = latitude+0.001199;
            longitude = longitude+0.001188;
            LatLng latLng = new LatLng(latitude,longitude);

            mMap.addMarker(new MarkerOptions().position(latLng).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.markersmall))
                    .title("Title " + String.valueOf(i))).setTag(String.valueOf(i));



            dataList.add(resltdata);

        }

        customeAdapterForImage = new CustomeAdapterForImage(getApplicationContext(), dataList);
        rc_list.setAdapter(customeAdapterForImage);


    }

    private void tvListClickHandler(){

        bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dataList.size()>0) {
                    Intent verticallActivity = new Intent(MainMaps.this, VerticalListActivity.class);
                    verticallActivity.putExtra("mylist", dataList);
                    startActivity(verticallActivity);
                }
            }
        });
    }

}
