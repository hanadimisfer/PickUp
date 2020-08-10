package com.example.pickup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;


import com.example.pickup.PermissionUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class CurrentLoc extends AppCompatActivity  implements OnMyLocationButtonClickListener, OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {


    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private TextView loca;
    private int id;
    private double latD;
    private double logD;
    private double price;
    private double latC;
    private double logC;
    private String currentS;
    private String desS;
    private String packageS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_loc);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        loca= (TextView) findViewById(R.id.yourloca);

        Bundle dataC= getIntent().getExtras();
        if(dataC!= null){
            id= dataC.getInt("id");
            latD= dataC.getDouble("latD");
            logD= dataC.getDouble("logD");
            price= dataC.getDouble("price");
            desS= dataC.getString("desS");
            packageS=dataC.getString("packageS");
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.

            //PermissionUtils.requestPermission(CurrentLoc.this , LOCATION_PERMISSION_REQUEST_CODE,Manifest.permission.ACCESS_FINE_LOCATION, true);
            PermissionUtils.requestPermission(CurrentLoc.this,LOCATION_PERMISSION_REQUEST_CODE,  Manifest.permission.ACCESS_FINE_LOCATION,true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        latC=location.getLatitude();
        logC=location.getLongitude();

        String ladd= getAdress(this, location.getLatitude(),location.getLongitude());
        loca.setText(ladd);
        currentS= (String) loca.getText();
    }

    public String getAdress(Context ctx, double lat , double langl){
        String fullAdd=null;
        try{
            Geocoder geo= new Geocoder(ctx, Locale.getDefault());
            List<android.location.Address> addresses = geo.getFromLocation(lat,langl,1);
            if(addresses.size()>0){
                Address address= addresses.get(0);
                fullAdd= address.getAddressLine(0);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return fullAdd;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }



    public void nextP(View v){
        // go to next page
        if(loca.getText()==""){
            showMessage("Error", "please, click your location");

        } else {
            Intent nextP = new Intent(CurrentLoc.this, Direction1.class);
            Bundle dataC = new Bundle();
            dataC.putInt("id", id);
            dataC.putDouble("latD", latD);
            dataC.putDouble("logD", logD);
            dataC.putDouble("price", price);
            dataC.putDouble("latC", latC);
            dataC.putDouble("logC", logC);
            dataC.putString("desS", desS);
            dataC.putString("packageS", packageS);
            dataC.putString("currentS", currentS);
            nextP.putExtras(dataC);
            startActivity(nextP);
        }
    }

    public void backB(View v){
        Intent nextP= new Intent(CurrentLoc.this , PackageS.class);
        Bundle dataC= new Bundle();
        dataC.putInt("id",id);
        dataC.putDouble("latD",latD);
        dataC.putDouble("logD",logD);
        dataC.putString("desS", desS);
        dataC.putDouble("price",price);
        dataC.putString("packageS", packageS);
        nextP.putExtras(dataC);
        startActivity(nextP);
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
