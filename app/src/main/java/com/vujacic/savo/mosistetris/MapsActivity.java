package com.vujacic.savo.mosistetris;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.vujacic.savo.mosistetris.login.MapData;
import com.vujacic.savo.mosistetris.login.User;
import com.vujacic.savo.mosistetris.login.UserData;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "mape";
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private LocationCallback locationCallback;

    private CameraPosition mCameraPosition;
    private Location mLastKnownLocation;

    private LocationRequest mlocationRequset;
    private boolean mRequestingLocationUpdates = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapData.getInstance().setEventListener(new MapData.UserUpdatedListener() {
            @Override
            public void onListUpdated() {
                addMyPlaceMarkers();
            }
        });


        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        this.initLokacija();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(new LatLng(get));
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.myplace));
//                    markerOptions.title(place.getName());
//                    Marker marker = mMap.addMarker(markerOptions);
//                    markerPlaceIdMap.put(marker,i);
                    User user = UserData.getInstance().getUser();
                    user.lat = location.getLatitude(); user.lng = location.getLongitude();
                    user.timeStamp = System.currentTimeMillis();
                    UserData.getInstance().update(user);

                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    //mMap.clear();
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    //mMap.addMarker(new MarkerOptions().position(latLng).title("Your location").anchor(0.0f, 1.0f));
                    MapData.getInstance().update(System.currentTimeMillis());
//                    addMyPlaceMarkers();
                }
            }
        };

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    static final int PERMISSION_ACCES_FINE_LOCATION = 1;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCES_FINE_LOCATION);
        }

//        mMap.setMyLocationEnabled(true);
//
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(6));

        getDeviceLocation();

    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), 18));
                        mMap.setMyLocationEnabled(true);
//                        mMap.setLocationSource(mFusedLocationProviderClient);
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationRequset = locationRequest;
    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mlocationRequset,
                locationCallback,
                Looper.getMainLooper());
    }


    void initLokacija() {
        this.createLocationRequest();
//        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mlocationRequset);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        resolvable.startResolutionForResult(MapsActivity.this,
//                                REQUEST_CHECK_SETTINGS);
                    } catch (Exception ex) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    private HashMap<Marker,Integer> markerPlaceIdMap;
    private void addMyPlaceMarkers() {
        mMap.clear();
        ArrayList<User> places = MapData.getInstance().getCloseUsers();
        markerPlaceIdMap = new HashMap<Marker,Integer>((int)((double)places.size()*1.2));
        for(int i=0;i<places.size();i++){
            User usr = places.get(i);
//            String lat=usr.lat;
//            String lon=usr.lng;
            LatLng loc = new LatLng(usr.lat,usr.lng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);
            //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.myplace));
            markerOptions.title(usr.name);
            Marker marker = mMap.addMarker(markerOptions);
            markerPlaceIdMap.put(marker,i);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this,FindPeopleActivity.class);
                Integer i = markerPlaceIdMap.get(marker);
                intent.putExtra("position", i.toString());
                startActivity(intent);
                finish();
                return true;
            }
        });
    }
}
