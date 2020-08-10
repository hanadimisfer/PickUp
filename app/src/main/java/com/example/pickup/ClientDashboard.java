package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientDashboard extends AppCompatActivity {
Button Account,Payment,Lougout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        Account = findViewById(R.id.Accountbutton);
        Payment = findViewById(R.id.Ordersbutton);
        Lougout = findViewById(R.id.Logoutbutton);


        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent intent = new Intent(ClientDashboard.this,#.class);
             //   startActivity(intent);
                //   finish();
            }});

      //  Payment.setOnClickListener(new View.OnClickListener() {
        //    @Override
       //     public void onClick(View view) {
          //      Intent intent = new Intent(ClientDashboard.this,Payment.class);
         //       startActivity(intent);
         //       finish();
        //    }});

        Lougout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientDashboard.this,Homepage.class);
                startActivity(intent);
                finish();
            }});

    }
}
