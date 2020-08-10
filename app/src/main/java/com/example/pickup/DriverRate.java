package com.example.pickup;
// take info from prev
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class DriverRate extends AppCompatActivity {
    SQLiteDatabase db;
    private int driID;
    private int cosID;
    private String Destination;
    private String Location;
    private Double Price;
    private Double Size;
    String rating ;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rate);
        Button getRating = findViewById(R.id.goOnline);
        ratingBar = findViewById(R.id.ratingBar);
        rating = "" + ratingBar.getRating();
  Bundle pass= getIntent().getExtras();
        if(pass!= null){
            driID= pass.getInt("driID");
            cosID= pass.getInt("cosID");
            Destination= pass.getString("Destination");
            Location= pass.getString("Location");
            Price= pass.getDouble("price");
            Size= pass.getDouble("size");

        }

        driID= 6;
        cosID= 3;
        Destination= "tuu";
        Location= "tyyt";
        Price= 575.9;
        Size= 576.7;


        //create the database
        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);


        // Package Table
        db.execSQL("CREATE TABLE IF NOT EXISTS \"Package\" (\n" +
                "\t\"OID\"\tINTEGER ,\n"  +
                "\t\"Destination\"\tTEXT,\n" +
                "\t\"Location\"\tTEXT,\n" +
                "\t\"Price\"\tNUMERIC,\n" +
                "\t\"Size\"\tINTEGER,\n" +
                "\t\"CID\"\tINTEGER,\n" +
                "\t\"DID\"\tNUMERIC,\n" +
                "\t PRIMARY KEY (\"OID\") ,\n" +
                "\tFOREIGN KEY(\"DID\") REFERENCES  \"Driver\"(\"DID\"),\n" +
                "\tFOREIGN KEY(\"CID\") REFERENCES \"Client\"(\"CID\")\n" +
                ");");


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


        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Inser into package
                db.execSQL("INSERT INTO Package (Destination,Location,Price,Size,CID,DID) VALUES('"+Destination+"','"+Location+"','"+Price+"','"+Size+"','"+cosID+"','"+driID+"');");

                // Update Driver rate into package
                db.execSQL("UPDATE Driver SET Rate='"+rating+"' WHERE DID='"+driID+"' ");

                Intent ij= new Intent(DriverRate.this, MainActivity.class);
                startActivity(ij);

            }
        });
    }

}