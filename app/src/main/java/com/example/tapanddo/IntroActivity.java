package com.example.tapanddo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startLoading();
    } // onCreate()

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent indent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(indent); // load MainActivity
                finish(); // finish current activity
            }
        }, 3000); // display Logo for 3sec
    } // startLoading Method
} // MainActivity Class