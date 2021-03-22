package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListingDetails extends AppCompatActivity {
    DatabaseReference employerRef;
    FirebaseDatabase database;
    DatabaseReference listingRef = null;

    Button home, logout, back, apply;
    String [] listing =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);

        Bundle extras = getIntent().getExtras();
        listing = extras.getStringArray("details");
        home=findViewById(R.id.employeeHome);
        logout = findViewById(R.id.Logout);
        home.setOnClickListener(this::onClick);
        logout.setOnClickListener(this::onClick);
        back =findViewById(R.id.backEmployeeHome);
        back.setOnClickListener(this::onClick);
        database = FirebaseDatabase.getInstance();
        listingRef = database.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");

        setTextBox();
    }

    protected void setTitleDisplay(String title){
        EditText editText = findViewById(R.id.titleInput);
        editText.setText(title);
    }
    protected void setDescriptionDisplay(String title){
        EditText editText = findViewById(R.id.descriptionInput);
        editText.setText(title);
    }
    protected void setUrgencyDisplay(String title){
        EditText editText = findViewById(R.id.urgencyInput);
        editText.setText(title);
    }
    protected void setDateDisplay(String title){
        EditText editText = findViewById(R.id.dateInput);
        editText.setText(title);
    }
    protected void setPayDisplay(String title){
        EditText editText = findViewById(R.id.payInput);
        editText.setText(title);
    }
    protected void setStatusDisplay(String title){
        EditText editText = findViewById(R.id.statusInput);
        editText.setText(title);
    }
    protected void setTextBox() {
        setTitleDisplay(listing[0]);
        setDescriptionDisplay(listing[1]);
        setUrgencyDisplay(listing[2]);
        setDateDisplay(listing[3]);
        setPayDisplay(listing[4]);
        setStatusDisplay(listing[5]);
    }

    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployeeHomepage.class);
        startActivity(switchIntent);
    }

    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.backEmployeeHome:
            case R.id.employeeHome:
                startActivity(new Intent(this, EmployeeHomepage.class));
                break;
            case R.id.applyToListing:
                startActivity(new Intent(this, AddListing.class));
                break;
            case R.id.Logout:
                //database.
                break;
        }

    }
}
