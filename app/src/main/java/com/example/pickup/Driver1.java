package com.example.pickup;
// take ID from prev and pass it
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Driver1 extends AppCompatActivity {
    private int id;
    private int av;
    SQLiteDatabase db;
    private Button goOnline;
    private TextView DcarType;
    private TextView DcarPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver1);
        goOnline = findViewById(R.id.goOnline);
        DcarType = findViewById(R.id.carTypeD);
        DcarPlate = findViewById(R.id.carplateD);

        av =1;
        id=1;



        // take id
        Bundle dataC = getIntent().getExtras();
        if (dataC != null) {
            //id = dataC.getInt("id");
            id = 1;
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

        //    db.execSQL("INSERT INTO Driver VALUES(1,1,'3',45322344322,'teeeee','rtreeerr','ettreee',6454,645555,'gfffghff');");




        veiwOrder();


        goOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.execSQL("UPDATE Driver SET Availability='"+av+"' WHERE DID='"+id+"' ");

                Intent ij= new Intent(Driver1.this, MainActivity.class);
                startActivity(ij);
            }
        });
    }

    public void veiwOrder() {
        Cursor c = db.rawQuery("SELECT * FROM Driver WHERE Availability='" + av + "'", null);
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
        }
        while (c.moveToNext()) {
            DcarPlate.setText(c.getString(7));
            DcarType.setText(c.getString(9));



        }
    }


    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}