package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationSource.OnLocationChangedListener, OnMapReadyCallback {
    TextView tv_lattitude, tv_langitude, tv_address,txtView;
    FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap googleMap;
    SearchView search_view;
    int PERMISSION_ID = 44;
    ImageView currentLoc;
    Location locationSpl;
    GoogleMap map_view;
    Handler handler = new Handler(Looper.myLooper());

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            getLastLocation();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentLoc = findViewById(R.id.currentLoc);
//        txtView = findViewById(R.id.txtView);


//        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String location = search_view.getQuery().toString();
//                List<Address> addressList = null;
//                if(location != null || location.equals("")){
//                    Geocoder geocoder = new Geocoder(LocationActivity.this);
//                    try{
//                        addressList = geocoder.getFromLocationName(location,1);
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//
//                    map_view.addMarker(new MarkerOptions().position(latLng).title(""));
//                    map_view.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

//        currentLoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getLastLocation();
//
//            }
//        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        tv_lattitude = findViewById(R.id.tv_lattitude);
        tv_langitude = findViewById(R.id.tv_langitude);
        tv_address = findViewById(R.id.tv_address);


        /*Places.initialize(getApplicationContext(), "AIzaSyCSLDGrjkwrVhqe2zs8a7R1FfY7XRKyfBI");

        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                txtView.setText(place.getName()+","+place.getId());
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                Log.i("TAG", "An error occurred: " + status);
            }
        });*/
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @SuppressLint("MissingPermission")
    private LatLng getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            locationSpl=location;
                            handler.removeCallbacks(runnable);
//                            handler.postDelayed(runnable,5000);
                            Log.d("TAG", "LATTITUDE: "+location.getLatitude());
                            Log.d("TAG", "LOGITUDE: "+location.getLongitude());

                            Toast.makeText(MainActivity.this,"Longitude"+locationSpl.getLatitude() +"Latitude"+locationSpl.getLongitude(),Toast.LENGTH_LONG).show();

                            tv_lattitude.setText("Latitude : "+location.getLatitude());
                            tv_langitude.setText("Longitude : "+location.getLongitude());

                            getLocationFromAddress(tv_langitude.getText().toString());

                            MarkerOptions markerOptions = new MarkerOptions();



                            map_view.addMarker(markerOptions.position(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude())));
//                           map_view.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),locationSpl.getLongitude())));
                            //map_view.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude()),10.0f));

//                            CameraPosition cameraPosition = new CameraPosition.Builder()
//                                    .target(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude()))
//                                    .zoom(50)
//                                    .bearing(90)
//                                    .tilt(30)
//                                    .build();
//                            map_view.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            //CameraUpdate center= ;
                            map_view.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude()),10.0f));
                        }
                    }
                });
            } else
            {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
        return null;
    }
    public LatLng getLocationFromAddress(String s) {

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(locationSpl.getLatitude(), locationSpl.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            tv_address.setText("Address : " +address+", "+city+", "+state);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            tv_lattitude.setText(  mLastLocation.getLatitude() + "");
            tv_langitude.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();

            handler.postDelayed(runnable,5000);

        }else {
            requestPermissions();
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationSpl=location;
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,5000);

        Log.d("TAG", "LATTITUDE: "+location.getLatitude());
        Log.d("TAG", "LOGITUDE: "+location.getLongitude());

        tv_lattitude.setText("Latitude : "+location.getLatitude());
        tv_langitude.setText("Longitude : "+location.getLongitude());

        getLocationFromAddress(tv_langitude.getText().toString());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map_view = googleMap;


    }

}



