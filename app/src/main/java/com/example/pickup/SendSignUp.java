package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;



import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSignUp extends AppCompatActivity {



    EditText editFName,editLName,editEmail, editPhone,editPassword,editCpassword, cardnum,cvv;
    int id;

    Button SignUp , login;
    static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendsginup);

        editFName = findViewById(R.id.NameeditText);
        editPhone = findViewById(R.id.PhoneeditText);
        editLName = findViewById(R.id.lastNameeditText);
        editEmail = findViewById(R.id.EmaileditText);
        editPassword = findViewById(R.id.PasswordeditText);
        editCpassword = findViewById(R.id.CpasswordeditText);
        cardnum = findViewById(R.id.CareditText);
        cvv = findViewById(R.id.cvvtText);
        SignUp = findViewById(R.id.SignButton);
        login = findViewById(R.id.button);



        db = openOrCreateDatabase("PickUp.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Client(CID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "C_Fname VARCHAR,C_Lname VARCHAR,C_Email VARCHAR,C_Password VARCHAR,Card_no INTEGER,C_phone VARCHAR,CVV INTEGER);");



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendSignUp.this,LogIn.class);
                startActivity(intent);
                finish();
            }});


    }


    public void onClick(View view) {
        if(view==SignUp) {
            if(editFName.getText().toString().trim().length()==0|| editLName.getText().toString().trim().length()==0||
                    editPhone.getText().toString().trim().length()==0 ||  editPassword.getText().toString().trim().length()==0 ||
                    editCpassword.getText().toString().trim().length()==0 ||    editEmail.getText().toString().trim().length()==0) {
                Toast.makeText(SendSignUp.this,"Please enter all the fields",Toast.LENGTH_LONG).show();
                return;
            }
            else if ((!editFName.getText().toString().matches("^[a-zA-Z]+$")) ||(!editLName.getText().toString().matches("^[a-zA-Z]+$")) ){

                editFName.setError(" Only letters accepted");
                return;
            }
            //else if (!editEmail.getText().toString().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$")){
              //  editEmail.setError("Unaccepted email ِformat");
                //return;
            //}
            else if (editPhone.getText().length()!=10 ){
                editPhone.setError("Phone number must be 10 diget");
                return;
            }
            else if (!editPhone.getText().toString().matches("05[0-9]{8}")){
                editPhone.setError("Unaccepted Phone number ِformat");
                return;
            }
            else if (editPassword.getText().toString().length()!=6 || editPassword.getText().toString().length()!=6 ){
                editPassword.setError("Password length must be 6");
                return;

            }
            else if (!editPassword.getText().toString().matches(editCpassword.getText().toString())){

                editCpassword.setError("Password not matches");                return;
            }

            db.execSQL("INSERT INTO Client(C_Fname ,C_Lname ,C_Email ,C_Password ,Card_no ,C_phone ,CVV )" +
                    "VALUES('"+editFName.getText()+"','"+editLName.getText()+"','"+editEmail.getText()+"','"+editPassword.getText()+"','"+cardnum.getText()+"',' "+editPhone.getText()+"','"+cvv.getText()+"');");


            showMessage("registered","you have registered successfully" ); //long time
            clearText();

            // move to next activity
            Intent intent = new Intent(SendSignUp.this,MapsActivity.class);
            Bundle dataC= new Bundle();
            dataC.putInt("id",id);
            startActivity(intent);



        }
        else
            showMessage("Error","Something went wrong please try again !" ); //long time
        clearText();


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