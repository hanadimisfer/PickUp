package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import static com.example.pickup.SendSignUp.db;

public class LogIn extends AppCompatActivity {
    EditText EmaileditText , PasswordeditText;
    Button loginButton;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    //SQLiteHelper sqLiteHelper;
    String TempPassword = "NOT_FOUND" ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //sqLiteHelper = new SQLiteHelper(this);
        EmaileditText = findViewById(R.id.EmaileditText);
        PasswordeditText = findViewById(R.id.PasswordeditText);
        loginButton = findViewById(R.id.loginButton);

        //Adding click listener to log in button.

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View V){
                if(login(EmaileditText.getText().toString(), PasswordeditText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Log In Success ! ", Toast.LENGTH_LONG).show();
                    Intent msg1 = new Intent(LogIn.this, MapsActivity.class);
                    startActivity(msg1);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Username/password combination", Toast.LENGTH_LONG).show();
                }

            }
        });
}

// no helper class //not work
    public boolean login(String email, String password){
        boolean i =false;

        String[] selectionArgs = new String[]{email, password};
        try
        {

            Cursor c = null;
           // c = db.rawQuery("SELECT email, password FROM Client where email=? and password=?", selectionArgs);
           c = sqLiteDatabaseObj.rawQuery("select * from Client where email =" + "\""+ EmaileditText.getText()+ "\""+" and password="+ "\""+ PasswordeditText.getText(),null);
            if(c.getCount()!=0) {
                while (c.moveToNext()) {
                   if( EmaileditText.getText().equals(c.getString(0))&
                    PasswordeditText.getText().equals(c.getString(1))){
                       i=true;
                       break;

                   }

                }

                Intent in= new Intent(LogIn.this, MapsActivity.class);
                startActivity(in);

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return i;} // end






    }





