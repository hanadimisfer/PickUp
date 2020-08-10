package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverDashboard extends AppCompatActivity {
    Button Account,Order,Lougout,Car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        Account = findViewById(R.id.Accountbutton);
        Order = findViewById(R.id.Ordersumbutton);
        Lougout = findViewById(R.id.Logoutbutton);
        Car = findViewById(R.id.Carbutton);

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Intent intent = new Intent(DriverDashboard.this,#.class);
                //   startActivity(intent);
                //   finish();
            }});

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     Intent intent = new Intent(DriverDashboard.this,Payment.class);
             //   startActivity(intent);
               // finish();
            }});

        Lougout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(DriverDashboard.this,Homepage.class);
                startActivity(intent);
                finish();
            }});

        Car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(DriverDashboard.this,Homepage.class);
                // startActivity(intent);
                // finish();
            }});
    }
    }

