package com.example.pickup;
// take ID  and latlag from prev and pass Destination,Location,Price,Size,CID,DID to rate page
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Direction1 extends FragmentActivity implements OnMapReadyCallback {

    private int id =1;
    private int av =1;
    private int driID;
    private int cosID;
    private String Destination;
    private String Location;
    private Double Price;
    private Double Size;
    private Double latD;
    private Double logD;
    private Double latC;
    private Double logC;
    SQLiteDatabase db;
    private GoogleMap mMap;
    private Button confirm;
    private TextView carType;
    private TextView carPlate;
    private TextView DriverFName;
    private TextView DriverLName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction1);
        confirm  = findViewById(R.id.confirm);
        carType = findViewById(R.id.car_Type);
        carPlate = findViewById(R.id.carPlate);
        DriverFName = findViewById(R.id.FName);
        DriverLName = findViewById(R.id.LName);
       // latD=42.988640;
      //  logD=-77.715551;
       // latC=42.969048;
       // logC=-77.472822;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // id
        Bundle pass= getIntent().getExtras();
        if(pass!= null){
            cosID= pass.getInt("id");
            Destination= pass.getString("desS");
            Location= pass.getString("currentS");
            Price= pass.getDouble("price");
            Size= pass.getDouble("packageS");
           latD= pass.getDouble("latD");
            logD= pass.getDouble("logD");
            latC= pass.getDouble("latC");
            logC= pass.getDouble("logC");

        }


        //create the database
        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);

        //create the database table
        // Driver Table
        db.execSQL("CREATE TABLE IF NOT EXISTS \"Driver\" (\n" +
                "\t\"DID\"\tINTEGER,\n" +
                "\t\"Availability\"\tINTEGER,\n" +
                "\t\"Rate\"\tTEXT,\n" +
                "\t\"D_Phone\"\tNUMERIC,\n" +
                "\t\"D_Fname\"\tTEXT,\n" +
                "\t\"D_Lname\"\tTEXT,\n" +
                "\t\"D_Email\"\tTEXT,\n" +
                "\t\"Car_Plate\"\tINTEGER,\n" +
                "\t\"D_Password\"\tNUMERIC,\n" +
                "\t\"Car_Type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"DID\")\n" +
                ");");
        //  db.execSQL("INSERT INTO Driver (DID,Availability,Rate,D_Phone,D_Fname,D_Lname,D_Email,Car_Plate,D_Password,Car_Type) VALUES(1,1,'4',6776555,'hffddd','srgdrfg','ertetrtr',35554,543354,'trty');");


        veiwOrder();


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent nextP= new Intent(Direction1.this , DriverRate.class);
                Bundle pass= new Bundle();
                pass.putInt("Cid",id);
                pass.putInt("Did",id);
                pass.putString("Destination",Destination);
                pass.putString("Location",Location);
                pass.putDouble("price",Price);
                pass.putDouble("packageS",Size);
                nextP.putExtras(pass);
                startActivity(nextP);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng from = new LatLng(latC,logC);
        LatLng to = new LatLng(latD,logD);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(from,7));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(to,10));
        Marker f = mMap.addMarker(new MarkerOptions().position(from).title("Current Location"));
        Marker t = mMap.addMarker(new MarkerOptions().position(to).title("Distination"));
        f.showInfoWindow();
        t.showInfoWindow();
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        Polyline line =mMap.addPolyline(new PolylineOptions().add(from,to)
                .width(15).color(Color.YELLOW).geodesic(true));
    }




    public void veiwOrder() {
        Cursor c = db.rawQuery("SELECT * FROM Driver WHERE DID ='" + id + "'", null);
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
            while (c.moveToNext()) {
                carType.setText(c.getString(10));
                carPlate.setText(c.getString(8));
                DriverFName.setText(c.getString(5));
                DriverLName.setText(c.getString(6));
                driID = c.getInt(0);
            }
        }
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public void Support(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:0536640810"));
        startActivity(i);}

    public void back(View v) {
        Intent ij = new Intent(Direction1.this, MainActivity.class);
        startActivity(ij);
    }

}