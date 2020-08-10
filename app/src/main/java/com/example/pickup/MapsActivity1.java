package com.example.pickup;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.database.Cursor;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class MapsActivity1 extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback
{
    SQLiteDatabase db;
    private EditText D_fname;
    private EditText  D_lname ;
    private EditText Car_Type;
    private EditText Car_Plate;
    private RatingBar Rate;
    private Button Availability;
    private int id;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private int av = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        D_fname = (EditText) findViewById(R.id.editText);
        D_lname = (EditText) findViewById(R.id.editText4);
        Car_Type = (EditText) findViewById(R.id.editText2);
        Car_Plate = (EditText) findViewById(R.id.editText3);
        Rate = (RatingBar) findViewById(R.id.ratingBar2);
        Availability = (Button) findViewById(R.id.button3);


        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);

        //create the database table
        db.execSQL("CREATE TABLE IF NOT EXISTS \"Driver\" (\n" +
                "\t\"DID\"\tINTEGER,\n" +
                "\t\"Availability\"\tINTEGER,\n" +
                "\t\"Rate\"\tTEXT,\n" +
                "\t\"D_Phone\"\tNUMERIC,\n" +
                "\t\"D_Fname\"\tTEXT,\n" +
                "\t\"D_Lname\"\tTEXT,\n" +
                "\t\"D_Email\"\tTEXT,\n" +
                "\t\"Car_Plate\"\tNUMERIC,\n" +
                "\t\"D_Password\"\tNUMERIC,\n" +
                "\t\"Car_Type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"DID\")\n" + ");");

        // take id
        Bundle dataC = getIntent().getExtras();
        if (dataC != null) {
            //id = dataC.getInt("id");
            id = 1;
        }

        veiwOrder();

        Availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.execSQL("UPDATE Driver SET Availability='" + av + "' WHERE DID='" + id + "' ");

            }
        });
    }

    public void veiwOrder(){
        Cursor c = db.rawQuery("SELECT p.Location, p.Destination, p.Price,p.Size, d.Rate FROM Package p INNER JOIN Driver d ON p.DID=d.DID WHERE p.DID='"+id+"'", null);
        if(c.getCount()==0) {
            showMessage("Error", "No records found");
        }


        StringBuffer bufferf=new StringBuffer();
        StringBuffer buffert=new StringBuffer();
        StringBuffer bufferp=new StringBuffer();
        StringBuffer buffers=new StringBuffer();
        StringBuffer bufferd=new StringBuffer();

        while (c.moveToNext()) {
            bufferf.append(c.getString(0)+"\n");
            // from.setText(c.getString(0));
            buffert.append(c.getString(1)+"\n");
            //to.setText(c.getString(1));
            bufferp.append(c.getString(2)+"\n");
            //price.setText(c.getString(2));
            buffers.append(c.getString(3)+"\n");
            //size.setText(c.getString(3));
            bufferd.append(c.getString(4)+"\n");
            // driver.setText(c.getString(4));
        }
        D_fname.setText(bufferf.toString());
        D_lname.setText(buffert.toString());
        Car_Type.setText(bufferp.toString());
        Car_Plate.setText(buffers.toString());
        Rate.setRating(Float.parseFloat(bufferd.toString()));
    }




    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public void onMapReady(GoogleMap Map) {
        mMap = Map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
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
}