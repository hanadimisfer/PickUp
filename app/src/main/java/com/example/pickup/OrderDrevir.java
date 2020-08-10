package com.example.pickup;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderDrevir extends AppCompatActivity {

    SQLiteDatabase db;
    private TextView from;
    private TextView to;
    private TextView price;
    private TextView size;
    private TextView Rate;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drevir);
        from= (TextView) findViewById(R.id.from1T1);
        to= (TextView) findViewById(R.id.to1T1);
        price= (TextView) findViewById(R.id.price1T1);
        size= (TextView) findViewById(R.id.size1T1);
        Rate= (TextView) findViewById(R.id.textView3);


        //create the database
        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);

        //create the database table
        db.execSQL("CREATE TABLE IF NOT EXISTS \"Package\" (\n" +
                "\t\"OID\"\tINTEGER,\n" +
                "\t\"Destination\"\tTEXT,\n" +
                "\t\"Location\"\tTEXT,\n" +
                "\t\"Price\"\tNUMERIC,\n" +
                "\t\"Size\"\tINTEGER,\n" +
                "\t\"CID\"\tINTEGER,\n" +
                "\t\"DID\"\tNUMERIC,\n" +
                "\tPRIMARY KEY(\"OID\"),\n" +
                "\tFOREIGN KEY(\"DID\") REFERENCES \"Driver\"(\"DID\"),\n" +
                "\tFOREIGN KEY(\"CID\") REFERENCES \"Client\"(\"CID\")\n" +
                ");");


        // take id
        Bundle dataC= getIntent().getExtras();
        if(dataC!= null) {
            //id = dataC.getInt("id");
            id=1;
        }
        veiwOrder();
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
        from.setText(bufferf.toString());
        to.setText(buffert.toString());
        price.setText(bufferp.toString());
        size.setText(buffers.toString());
        Rate.setText(bufferd.toString());
    }


    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}