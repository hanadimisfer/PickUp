package com.example.pickup;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SQLiteDatabase db;
    int a;
    String size,name,from;
    double lon,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db=openOrCreateDatabase("Picker6", Context.MODE_PRIVATE, null);
        Bundle extras = getIntent().getExtras();
        from = extras.getString("from");
        //  a= extras.getInt("cid"); اجيب السيندر اي دي
        lat = extras.getDouble("lat");
        lon= extras.getDouble("long1");
        TextView From2 = (TextView) findViewById(R.id.From2); //
        From2.setText(from);
        String To2 = extras.getString("To");
        TextView To = (TextView) findViewById(R.id.To2); //
        To.setText(To2);

        Cursor c=db.rawQuery("SELECT pSize FROM Product WHERE CID="+a, null);
        Cursor c2=db.rawQuery("SELECT Name FROM Client WHERE CID="+a, null);
        c.moveToFirst();
        c2.moveToFirst();
        size=c.getString(0);
        name=c2.getString(0);



        TextView sender = (TextView) findViewById(R.id.SenderName2); //
        sender.setText(name);

        TextView sizep = (TextView) findViewById(R.id.PackageSize2); //
        sizep.setText(size);

    }

    public void onButtonClick(View v){
        Intent myIntent = new Intent(getBaseContext(),MapsActivity3.class);
        myIntent.putExtra("lat",lat);
        myIntent.putExtra("long1",lon);

        startActivity(myIntent);
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

        // Add a marker in Sydney and move the camera
        LatLng f = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(f,7));

        mMap.addMarker(new MarkerOptions().position(f).title(from));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(f));
    }

    public void back(View view) {

        Intent myIntent = new Intent(getBaseContext(),MapsActivity3.class);
        myIntent.putExtra("lat",lat);
        myIntent.putExtra("long1",lon);

        startActivity(myIntent);
    }
}