package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PackageS extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView price;
    private int id;
    private double latD;
    private double logD;
    private double result;
    private String desS;
    private String packageS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_s);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        price= (TextView) findViewById(R.id.priceT);

        Bundle dataP= getIntent().getExtras();
        if(dataP!= null){

            id= dataP.getInt("id");
            latD= dataP.getDouble("latD");
            logD= dataP.getDouble("logD");
            desS=dataP.getString("desS");

        }



    }
    public void radioBEvnet(View v){
        Boolean check= ((RadioButton)v).isChecked();

        switch (v.getId()){
            case R.id.small_p:
                result= 6*6;
                price.setText("Approximate price: "+ result+"SR");
                packageS="small";
                break;
            case R.id.mid_p:
                result= 9*6;
                price.setText("Approximate price: "+ result+"SR");
                packageS="Medium";
                break;
            case R.id.larg_p:
                result= 12*6;
                price.setText("Approximate price: "+ result+"SR");
                packageS="Larg";
                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng des = new LatLng(latD, logD);
        mMap.addMarker(new MarkerOptions().position(des));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(des,100);
        mMap.animateCamera(cameraUpdate);
    }

    public void nextP(View v){
        int e= v.getId();
        if(price.getText()==""){

            showMessage("Error", "please, choose package size");



        }else {
            Intent nPage = new Intent(this, CurrentLoc.class);
            Bundle dataC = new Bundle();
            dataC.putInt("id", id);
            dataC.putDouble("latD", latD);
            dataC.putDouble("logD", logD);
            dataC.putString("desS",desS);
            dataC.putDouble("price", result);
            dataC.putString("packageS", packageS);
            nPage.putExtras(dataC);
            startActivity(nPage);
        }
    }

    public void backB(View v){
        //chanage des
        Intent nPage= new Intent(this , MapsActivity.class);
        Bundle dataC= new Bundle();
        dataC.putInt("id",id);
        dataC.putDouble("latD",latD);
        dataC.putDouble("logD",logD);
        dataC.putString("desS",desS);
        dataC.putDouble("price",result);
        nPage.putExtras(dataC);
        startActivity(nPage);
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
