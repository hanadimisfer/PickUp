package com.example.pickup;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private  Button button;
    private Button button2;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button     = (Button) findViewById(R.id.button);
        button2    = (Button) findViewById(R.id.button2);
        textView= findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("", "your Riad is accepted");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("", "This location has been  rejected");
            }

        });


    }

    public String getAdress(Context ctx, double lat , double langl){
        String fullAdd=null;
        try{
            Geocoder geo= new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat,langl,1);
            if(addresses.size()>0){
                Address address= addresses.get(0);
                fullAdd= address.getAddressLine(0);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return fullAdd;
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
        double lat=26.3856740;
        double lon=50.1899695;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat ,lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        String ladd;
        ladd = getAdress(this, lat,lon);
        textView.setText(ladd);
        Marker f = mMap.addMarker(new MarkerOptions().position(sydney).title("Pick up Location"));
        f.showInfoWindow();
        mMap.setBuildingsEnabled(true);
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}