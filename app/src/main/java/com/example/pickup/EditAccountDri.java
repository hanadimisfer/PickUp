package com.example.pickup;
// take ID from prev and


// take id

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

public class EditAccountDri extends AppCompatActivity {

    private int id;
    SQLiteDatabase db;
    private Button edit_dri;
    private TextView DFname;
    private TextView DLname;
    private TextView DPhone ;
    private TextView Dpass ;
    private TextView DConpass;
    private TextView Car_type;
    private TextView Car_plate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_dri);


        edit_dri = findViewById(R.id.Dedit);
        DFname = findViewById(R.id.finame);
        DLname = findViewById(R.id.laname);
        DPhone = findViewById(R.id.phonee);
        Dpass = findViewById(R.id.dpass);
        DConpass = findViewById(R.id.dcPass);
        Car_type = findViewById(R.id.carT);
        Car_plate = findViewById(R.id.carP);

         /*Bundle pass= getIntent().getExtras();
        if(pass!= null){
            id= pass.getInt("driID");
        }*/
        Bundle pass= getIntent().getExtras();
        if(pass!= null){
            id= pass.getInt("driID");
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


        // take id
        Bundle dataC = getIntent().getExtras();
        if (dataC != null) {
            //id = dataC.getInt("id");
            id = 1;
        }
        veiwOrder();

        edit_dri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(DFname.getText().toString().trim().length()==0 ||
                        DLname.getText().toString().trim().length()==0 ||
                        DPhone.getText().toString().trim().length()==0 ||
                        Dpass.getText().toString().trim().length()==0 ||
                        DConpass.getText().toString().trim().length()==0 ||
                        Car_type.getText().toString().trim().length()==0 ||
                        Car_plate.getText().toString().trim().length()==0  )
                {

                    showMessage("Error", "Please Fill All the Fields");
                    return;
                }

                else if(!Dpass.getText().toString().matches(DConpass.getText().toString()))
                {

                    showMessage("Error", "Password Don't Match");
                    return;
                }
                else
                {
                    db.execSQL("UPDATE Driver SET D_Fname='"+DFname.getText()+"',D_Lname='"+DLname.getText()+"',D_Phone='"+DPhone.getText()+"',D_Password='"+Dpass.getText()+"',Car_Plate='"+Car_plate.getText()+"',Car_Type='"+Car_type.getText()+"'  WHERE DID='"+id+"' ");
                    showMessage("Success", "Record Modified");
                }

            }
        });


    }

    public void veiwOrder() {
        Cursor c = db.rawQuery("SELECT * FROM Driver WHERE DID='" + id + "'", null);
        if (c.getCount() == 0) {
            showMessage("Error", "No records found");

        }
        while (c.moveToNext()) {
            DPhone.setText(c.getString(3));
            DFname.setText(c.getString(4));
            DLname.setText(c.getString(5));
            Car_plate.setText(c.getString(7));
            Dpass.setText(c.getString(8));
            DConpass.setText(c.getString(8));
            Car_type.setText(c.getString(9));


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

        Intent ij= new Intent(EditAccountDri.this, MainActivity.class);
        startActivity(ij);
    }
}