package com.example.a3130_group_6;

import android.app.DirectAction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a3130_group_6.LoginPage.validEmployer;
import static com.example.a3130_group_6.LoginPage.validEmployee;

public class ListingDetails extends AppCompatActivity {
    DatabaseReference employerRef;
    DatabaseReference listingRef = null;
    FirebaseDatabase database;

    Button home, logout, back, apply;
    String [] listing = null;

    ArrayList<String> keys;
    ArrayList<String> employers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        listing = extras.getStringArray("details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);

        home = findViewById(R.id.employeeHome);
        home.setOnClickListener(this::onClick);

        logout = findViewById(R.id.Logout);
        logout.setOnClickListener(this::onClick);

        back = findViewById(R.id.backEmployeeHome);
        back.setOnClickListener(this::onClick);

        apply = findViewById(R.id.applyToListing);
        apply.setOnClickListener(this::onClick);

        database = FirebaseDatabase.getInstance();
        listingRef = database.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");

        setTextBox();
    }

    protected void setTitleDisplay(String title){
        TextView textView = findViewById(R.id.titleInput);
        textView.setText(title);
    }
    protected void setDescriptionDisplay(String description){
        TextView textView = findViewById(R.id.descriptionInput);
        textView.setText(description);
    }
    protected void setUrgencyDisplay(String urgency){
        TextView textView = findViewById(R.id.urgencyInput);
        textView.setText(urgency);
    }
    protected void setDateDisplay(String date){
        TextView textView = findViewById(R.id.dateInput);
        textView.setText(date);
    }
    protected void setPayDisplay(String pay){
        TextView textView = findViewById(R.id.payInput);
        textView.setText(pay);
    }

    protected void setStatusDisplay(String status){
        TextView textView = findViewById(R.id.statusInput);
        textView.setText(status);
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

    /**
     * Function: This method applies an employee to a listing
     * Parameters: none
     * Returns: void
     *
     */
    protected void applyToListing(int position){
        // save current employee under listing in database
        //save object user to database to Firebase
        employerRef.child(employers.get(position)).child("Listing").child(keys.get(position)).child("Applicants").child(validEmployee[0]).setValue("Applying");
    }

    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.backEmployeeHome:
            case R.id.employeeHome:
                startActivity(new Intent(this, EmployeeHomepage.class));
                break;
            case R.id.applyToListing:
                applyToListing(1);
                break;
            case R.id.Logout:
                //database.
                break;
        }

    }

}
