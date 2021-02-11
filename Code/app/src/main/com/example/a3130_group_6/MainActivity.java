package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent switchIntent = new Intent(MainActivity.this, EmployeeHomepage.class);
        startActivity(switchIntent);
    }
}
