package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Message extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    public void onButtonClick(View view) {
        Intent myIntent = new Intent(getBaseContext(),Message.class);// اسم اللي بعدي
        startActivity(myIntent);
    }
}