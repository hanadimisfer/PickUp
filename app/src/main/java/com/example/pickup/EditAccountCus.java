package com.example.pickup;
// take ID from prev and

// take id
/*Bundle dataC = getIntent().getExtras();
        if (dataC != null) {
        //id = dataC.getInt("id");
        id = 1;
        } */
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

public class EditAccountCus extends AppCompatActivity {
    private int id;
    SQLiteDatabase db;
    private Button edit_Cus;
    private TextView Fname;
    private TextView Lname;
    private TextView Phone;
    private TextView passs;
    private TextView Conpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_cus);
        edit_Cus = findViewById(R.id.edit);
        Fname = findViewById(R.id.fName);
        Lname = findViewById(R.id.lName);
        Phone = findViewById(R.id.phone);
        passs = findViewById(R.id.pass);
        Conpass = findViewById(R.id.conPass);
        id =1;


        Bundle pass= getIntent().getExtras();
        if(pass!= null){
            id= pass.getInt("driID");
        }
        //create the database
        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);

        //create the database table
        // Client Table
        db.execSQL("CREATE TABLE IF NOT EXISTS \"Client\" (\n" +
                "\t\"CID\"\tINTEGER,\n" +
                "\t\"C_Fname\"\tTEXT,\n" +
                "\t\"C_Lname\"\tTEXT,\n" +
                "\t\"C_Email\"\tTEXT,\n" +
                "\t\"C_Password\"\tNUMERIC,\n" +
                "\t\"Card_no\"\tNUMERIC,\n" +
                "\t\"C_Phone\"\tNUMERIC,\n" +
                "\t\"CVV\"\tNUMERIC,\n" +
                "\tPRIMARY KEY(\"CID\")\n" +
                ");");


        veiwOrder();

        edit_Cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Fname.getText().toString().trim().length()==0 ||
                        Lname.getText().toString().trim().length()==0 ||
                        Phone.getText().toString().trim().length()==0 ||
                        passs.getText().toString().trim().length()==0 )
                {
                    showMessage("Error", "Please fill all the fields");
                    return;
                }
                else if(!passs.getText().toString().matches(Conpass.getText().toString()))
                {

                    showMessage("Error", "Password Don't Match");
                    return;
                }
                else

                {
                    db.execSQL("UPDATE Client SET C_Fname='"+Fname.getText()+"',C_Lname='"+Lname.getText()+"',C_Phone='"+Phone.getText()+"',C_Password='"+passs.getText()+"' WHERE CID='"+id+"' ");
                    showMessage("Success", "Record Modified");


                }

            }
        });


    }
    public void veiwOrder() {
        Cursor c = db.rawQuery("SELECT * FROM Client WHERE CID='" + id + "'", null);
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");
        }
        while (c.moveToNext()) {
            Fname.setText(c.getString(1));
            Lname.setText(c.getString(2));
            Phone.setText(c.getString(6));
            passs.setText(c.getString(4));
            Conpass.setText(c.getString(4));
        }
    }


    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void Cancel(View v){

        Intent ij= new Intent(EditAccountCus.this, MainActivity.class);
        startActivity(ij);
    }

}