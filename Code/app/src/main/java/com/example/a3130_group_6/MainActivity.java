package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.button);
        registerButton.setOnClickListener((View.OnClickListener) this);
    }

    protected void switch2WelcomeWindow() {
        //your business logic goes here!
        Intent switchIntent = new Intent(this, add_listing.class);
        startActivity(switchIntent);
        setContentView(R.layout.activity_add_listing);
    }

    public void onClick(View view) {
        switch2WelcomeWindow();
    }
}