package com.example.pickup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        final Button Pickbutton = findViewById(R.id.pickButton);
        final Button Sendbutton = findViewById(R.id.SendButton);
        ImageView img = findViewById(R.id.imageView3);


        Sendbutton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                     Intent intent = new Intent(Homepage.this,SendSignUp.class);
                 startActivity(intent);
                    finish();
            }});

        //move to Lama's interface
            Pickbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent = new Intent(Homepage.this,PickSignUp.class);
                    startActivity(intent);
                    finish();
                }});






    }}