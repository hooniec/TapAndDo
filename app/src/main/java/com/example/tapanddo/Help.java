package com.example.tapanddo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class Help extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        toolbar = (Toolbar)findViewById(R.id.app_bar); // reference app_bar
        setSupportActionBar(toolbar); // set action bar as the toolbar
        getSupportActionBar().setTitle("Tap N Do - Help"); // set title on action bar

        ab = getSupportActionBar(); // instance of the ActionBar
        ab.setDisplayHomeAsUpEnabled(true); // create back button
    } // onCreate()
}