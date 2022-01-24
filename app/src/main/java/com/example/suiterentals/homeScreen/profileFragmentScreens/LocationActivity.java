package com.example.suiterentals.homeScreen.profileFragmentScreens;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.suiterentals.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double longit, latit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Intent intent=getIntent();
        latit= Double.parseDouble(intent.getStringExtra("latitude"));
        longit= Double.parseDouble(intent.getStringExtra("longitude"));

        Log.d("LAT",intent.getStringExtra("latitude"));

        mMap = googleMap;

        LatLng location = new LatLng(latit, longit);
        mMap.addMarker(new
                MarkerOptions().position(location).title("Suite"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,13));

    }
}