package com.example.suiterentals.homeScreen.profileFragmentScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.suiterentals.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;

public class LocationMainActivity extends AppCompatActivity {

    SupportMapFragment smf;
    FusedLocationProviderClient client;
    private GoogleMap mMap;
    Double longit, latit;
    String lon, lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_main);
        lat = getIntent().getStringExtra("latitude");
        lon = getIntent().getStringExtra("longitude");
        longit = Double.valueOf(lat);
        longit = Double.valueOf(lon);

        Log.d("TAGG",getIntent().getStringExtra("latitude"));

        try {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
            client = LocationServices.getFusedLocationProviderClient(this);

            Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    if (ActivityCompat.checkSelfPermission(LocationMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Task<Location> task = client.getLastLocation();
                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            LatLng userlocation = new LatLng(location.getLatitude(), location.getLongitude());
                            LatLng suitelatlong = new LatLng(latit, longit);
                            MarkerOptions markerOptions = new MarkerOptions().position(suitelatlong).title("Your Location");
                            //TODO: add both markets for location of current user locaiton by latlong and point location of suite and get distance
                            mMap.addMarker(markerOptions);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(suitelatlong, 10));
                        }
                    });
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                }
            }).check();
        } catch (Exception e) {
            Log.d("TAG", "onMapShowPage: " + e.getMessage());
        }
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.maps);
//        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
}