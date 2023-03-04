package com.example.location.firebase.fragment;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.collection.ArraySet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.location.DirectionsJSONParser;
import com.example.location.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment implements OnMapReadyCallback,LocationSource.OnLocationChangedListener {
    FusedLocationProviderClient mFusedLocationClient;
    SupportMapFragment map_fragment;

    Marker markerName;
    LatLng mOrigin;
    LatLng mDestination;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Context context;
    Polyline mPolyline;
    int PERMISSION_ID = 44;
    ImageView currentLoc;
    Location locationSpl;
    GoogleMap map_view;
    MarkerOptions markerOptions;
    private static final int LOCATION_SETTINGS_REQUEST = 100;
    Handler handler = new Handler(Looper.myLooper());

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            getLastLocation();

        }
    };

    private ArraySet<Object> MarkerPoints;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (checkPermissions()) {
            setAutocompleteFragment();
        }else{
            requestPermissions();
        }
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.Api_Key));
            PlacesClient placesClient = Places.createClient(requireContext());
        }
        map_fragment = (SupportMapFragment) requireActivity().getSupportFragmentManager().
                findFragmentById(R.id.map_fragment);
        if (map_fragment != null) {
            map_fragment.getMapAsync(this);
        }
        return view;
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
                            Log.d("TAG", "LATTITUDE: "+location.getLatitude());
                            Log.d("TAG", "LOGITUDE: "+location.getLongitude());

                            //Toast.makeText(LocationActivity.this,"Longitude"+locationSpl.getLatitude() +"Latitude"+locationSpl.getLongitude(),Toast.LENGTH_LONG).show();

//                            tv_lattitude.setText("Latitude : "+location.getLatitude());
//                            tv_langitude.setText("Longitude : "+location.getLongitude());

                            //getLocationFromAddress(tv_langitude.getText().toString());

                            markerOptions = new MarkerOptions();

                            map_view.addMarker(markerOptions.position(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude())));

                            map_view.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude()),10.0f));

                        }
                    }
                });
            } else
            {
                Toast.makeText(requireContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }



    private boolean checkPermissions(){
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }


    private boolean isLocationEnabled()
    {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            handler.postDelayed(runnable,5000);

        }else {
            requestPermissions();
        }
    }

    /*public void onMapSearch(View view) {

        AutocompleteSupportFragment autocompleteFragments = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_edittext);
       // EditText locationSearch = (EditText) findViewById(R.id.editText);
        @SuppressLint("ResourceType") String location = autocompleteFragments.getText(R.id.search_button).toString();
        List<Address> addressList = null;

        if (location != null) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                Toast.makeText(this, "Error search :- Wrong input", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            Address address;
            address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            map_view.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
            map_view.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }else {
            Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
        }
    }*/

    private void setAutocompleteFragment(){

        AutocompleteSupportFragment autocompleteFragments = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_edittext_fragment);
        assert autocompleteFragments != null;
        autocompleteFragments.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragments.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull final Place place) {
                if (markerName != null) {
//                    markerName.remove();
                }
//                requestPermissions();
                mDestination= new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude());
                markerName = map_view.addMarker(new MarkerOptions().position(new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude())).title(place.getName()));
                map_view.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),10.0f));
                mDestination = place.getLatLng();
                drawRoute();
            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });

    }
    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map_view = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {


            }
        });
        map_view = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000);
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                enableLoc();
            } else {
                checkPermissions();
                checkLocationPermission();
            }
        } else {


            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            map_view.setMyLocationEnabled(true);
        }

    }
    private void enableLoc() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(requireActivity()).checkLocationSettings(builder.build());
        SettingsClient client = LocationServices.getSettingsClient(requireActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    //Toast.makeText(LocationActivity.this, "location connected completed", Toast.LENGTH_SHORT).show();
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    map_view.setMyLocationEnabled(true);
                    updateLocation();


                    //Toast.makeText(GoogleMapActivity.this, "Add your function here!", Toast.LENGTH_SHORT).show();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(
                                        requireActivity(),
                                        LOCATION_SETTINGS_REQUEST);
                            }
                            catch(ClassCastException | IntentSender.SendIntentException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;
                    }
                }
            }
        });
    }
    private void updateLocation() {
        if (mLastLocation != null) {
            LatLng mOrigin = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            drawRoute();
        }
    }

    private void drawRoute() {
        String url = getDirectionsUrl(mOrigin, mDestination);
      DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }


    private String  getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" +origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" +dest.latitude + "," + dest.longitude;

        // Key
        String key = "key=" + getString(R.string.Api_Key);
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&"  + "&" + mode;
        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.Api_Key);

        return url;
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception on download", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                Log.d("DownloadTask", "DownloadTask --->1:: " + data);
                data = downloadUrl(url[0]);
                Log.d("DownloadTask", "DownloadTask :  --->2::" + data);

            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadTask", "DownloadTask :  --->3::" + result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                Log.d("DownloadTask", "DownloadTask :  --->4::" + jObject);

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();


                List<HashMap<String, String>> path = result.get(i);


                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(locationSpl.getLatitude(),locationSpl.getLongitude());

                    points.add(position);
                }


                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }


            if (lineOptions != null) {
                if (mPolyline != null) {
                    mPolyline.remove();
                }
                mPolyline = map_view.addPolyline(lineOptions);

            } else
                Toast.makeText(requireContext(), "No route is found", Toast.LENGTH_LONG).show();
        }




    }
    LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);

                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mOrigin = latLng;
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Chennai");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = map_view.addMarker(markerOptions);
                map_view.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }



        }
    };
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableLoc();

        }
    }


}

