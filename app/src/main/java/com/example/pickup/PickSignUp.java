package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class PickSignUp extends AppCompatActivity {


    //create public variables
    EditText editFName,editLName,editEmail, editPhone,editPassword,editCpassword,CarType,CarPlate;

    Button SignUp,login;
    SQLiteDatabase db;
    static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_sign_up);

        login = findViewById(R.id.button);
        editFName = findViewById(R.id.NameeditText);
        editPhone = findViewById(R.id.PhoneeditText);
        editLName = findViewById(R.id.lastNameeditText);
        editEmail = findViewById(R.id.EmaileditText);
        editPassword = findViewById(R.id.PasswordeditText);
        editCpassword = findViewById(R.id.CpasswordeditText);
        CarType = findViewById(R.id.CareditText);
        CarPlate = findViewById(R.id.PCareditText);
        SignUp = findViewById(R.id.MoreButton);


        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Driver(DID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Availability INTEGER,Rate INTEGER,D_Phone VARCHAR, D_Fname VARCHAR,D_Lname VARCHAR,D_Email VARCHAR, Car_Plate INTEGER,D_Password VARCHAR , Car_Type VARCHAR);");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickSignUp.this,LogIn.class);
                startActivity(intent);
                finish();
            }});

    }




    public void onClick(View view) {
        String cartype1 = CarType.getText().toString();
        String carmodel = CarPlate.getText().toString();

        String Cartype2 = "Small car";
        String Cartype3 = "Track";

        if(view==SignUp) {
            if(editFName.getText().toString().trim().length()==0|| editLName.getText().toString().trim().length()==0||
                    editPhone.getText().toString().trim().length()==0 ||  editPassword.getText().toString().trim().length()==0 ||
                    editCpassword.getText().toString().trim().length()==0 ||    editEmail.getText().toString().trim().length()==0
                    ||    CarType.getText().toString().trim().length()==0 ||    CarPlate.getText().toString().trim().length()==0) {
                Toast.makeText(PickSignUp.this,"Please enter all the fields",Toast.LENGTH_LONG).show();
                return;
            }
            else if ((!editFName.getText().toString().matches("^[a-zA-Z]+$")) ){

                editFName.setError(" Only letters accepted");

                return;
            }else if ((!editLName.getText().toString().matches("^[a-zA-Z]+$")) ){

                editLName.setError(" Only letters accepted");
                return;
            } else if (!editEmail.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
                editEmail.setError("Unaccepted email ِformat");

                return;}

            else if (editPhone.getText().length()!=10 ){
                editPhone.setError("Phone number must be 10 diget");
                return;
            }
            else if (!editPhone.getText().toString().matches("05[0-9]{8}")){
                editPhone.setError("Unaccepted Phone number ِformat");
                return;
            }
           // else if (!cartype1.equals(Cartype2) && !cartype1.equals(Cartype3)) {
             //   CarType.setError(" Please enter small car or Track!");

               // return; }
            //else if (carmodel.length() !=4 ) {
              //  CarPlate.setError("length must be 4v");
                //return;

            //}
            else if (editPassword.getText().toString().length()!=6 || editPassword.getText().toString().length()!=6 ){
                editPassword.setError("Password length must be 6");
                return;

            }
            else if (!editPassword.getText().toString().matches(editCpassword.getText().toString())){

                editCpassword.setError("Password not matches");
                return;
            }

            db.execSQL("INSERT INTO Driver(Availability ,Rate ,D_Phone , D_Fname ,D_Lname ,D_Email , Car_Plate ,D_Password  , Car_Type )" +
                    "VALUES(null,null, '"+editPhone.getText()+"','"+editFName.getText()+"','"+editLName.getText()+"','"+editEmail.getText()+"','"+CarPlate.getText()+" ','"+editPassword.getText()+" ','"+CarType.getText()+" ');");


            // delete it
            id =getHighestID();

            showMessage("registered","you have registered successfully" ); //long time
            clearText();
            // move to next activity
          //  Intent intent = new Intent(PickSignUp.this,#.class);
          //  Bundle dataC= new Bundle();
          //  dataC.putInt("id",id);
          //  startActivity(intent);
        }
        else
            showMessage("Error","Something went wrong please try again !" ); //long time
        clearText();


    }// delete it
    public int getHighestID() {
        final String MY_QUERY = "SELECT last_insert_rowid()";
        Cursor cur = db.rawQuery(MY_QUERY, null);
        cur.moveToFirst();
        int ID = cur.getInt(0);
        cur.close();
        return ID;
    }

    public void showMessage(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText() {
        editLName.setText("");
        editPassword.setText("");
        editFName.setText("");
        editPhone.setText("");
        editEmail.setText("");
        editCpassword.setText("");
        editFName.requestFocus();
    }






}

