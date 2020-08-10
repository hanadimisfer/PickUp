package com.example.pickup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity4 extends FragmentActivity implements OnMapReadyCallback {
    double longitudeP;
    double latitudeP;

    double longitudeD;
    double latitudeD;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private Button mReq;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();
        latitudeD = extras.getDouble("lat");
        longitudeD = extras.getDouble("long1");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(MapsActivity4.this, Message.class);
                MapsActivity4.this.startActivity(mInHome);
                MapsActivity4.this.finish();
            }
        }, 10000);


    }


    private void onLocationChanged(Location location) {
        longitudeP=location.getLongitude();
        latitudeP=location.getLatitude();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        LatLng from = new LatLng(latitudeP,longitudeP);//curent location


        LatLng to = new LatLng(latitudeD,longitudeD); //pass value of sender location

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(from,7));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(to,10));
        Marker f = mMap.addMarker(new MarkerOptions().position(from).title("Your Location"));
        Marker t = mMap.addMarker(new MarkerOptions().position(to).title("Destintion"));
        f.showInfoWindow();
        t.showInfoWindow();
        Location fr=new Location("");
        fr.setLatitude(latitudeP);
        fr.setLongitude(longitudeP);
        Location de=new Location("");
        de.setLatitude(latitudeD);
        de.setLongitude(longitudeD);

        float distance=fr.distanceTo(de);
        mReq=(Button)findViewById(R.id.mReq);
        mReq.setText("The distance is " + String.valueOf(distance));


        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.getUiSettings().setZoomControlsEnabled(true);
        Polyline line =mMap.addPolyline(new PolylineOptions().add(from,to)
                .width(15).color(Color.YELLOW).geodesic(true));
    }


}