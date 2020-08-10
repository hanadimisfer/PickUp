package com.example.pickup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
        SearchView search;
        private GoogleMap mMap;
        private double latD,logD;
        private int id;
        Button find;
        String location;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_maps);

                // Pass to data
                Bundle dataC= getIntent().getExtras();
                if(dataC!= null){
                        id= dataC.getInt("id");
                        latD= dataC.getDouble("latD");
                        logD= dataC.getDouble("logD");
                      //  location=dataC.getString("location");
                }
                find = findViewById(R.id.Pickerbutton2);

                //Start SV
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                search=findViewById(R.id.sv_location);
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {

                                 location = search.getQuery().toString();
                                List<Address> addressList = null;
                                if (location != null || !location.equals("")){ //not show
                                        Geocoder geocoder = new Geocoder(MapsActivity.this) ;
                                        try {
                                                addressList = geocoder.getFromLocationName(location,1);
                                        }
                                        catch (IOException e) {
                                                Toast.makeText(MapsActivity.this,"Something went wrong please try again",Toast.LENGTH_LONG).show();
                                                e.printStackTrace();//change it
                                        }
                                        Address address = addressList.get(0);
                                        LatLng latLng = new LatLng(address.getLatitude(),address.getLatitude());
                                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.0f));
                                      //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.0f));

                                        latD= address.getLatitude();
                                        logD= address.getLatitude();

                                         }
                                return false; }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                                return false;
                        }});

                mapFragment.getMapAsync(this);


                //Pass to Maha's activity
            find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                           Intent nextP= new Intent(MapsActivity.this ,PackageS.class);
                            Bundle dataC= new Bundle();
                            dataC.putInt("id",id);
                            dataC.putDouble("latD",latD);
                            dataC.putDouble("logD",logD);
                            dataC.putString("location", location );

                            nextP.putExtras(dataC);
                            startActivity(nextP);
                  }});

        }





        @Override
        public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
                mMap.setOnMyLocationClickListener(onMyLocationClickListener);
                enableMyLocationIfPermitted();
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.setMinZoomPreference(10);

        }

        private void enableMyLocationIfPermitted() {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                } else if (mMap != null) {
                        mMap.setMyLocationEnabled(true);
                }
        }

        private void showDefaultLocation() {
                Toast.makeText(this, "Location permission not granted, " +
                                "showing default location",
                        Toast.LENGTH_SHORT).show();
                LatLng redmond = new LatLng(24.774265, 46.738586);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
                switch (requestCode) {
                        case LOCATION_PERMISSION_REQUEST_CODE: {
                                if (grantResults.length > 0
                                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                        enableMyLocationIfPermitted();
                                } else {
                                        showDefaultLocation();
                                }
                                return;
                        }

                }
        }

        private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
                new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                                mMap.setMinZoomPreference(15);
                                return false;
                        }
                };

        private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
                new GoogleMap.OnMyLocationClickListener() {
                        @Override
                        public void onMyLocationClick(@NonNull Location location) {

                                mMap.setMinZoomPreference(15);

                        }
                };

}

