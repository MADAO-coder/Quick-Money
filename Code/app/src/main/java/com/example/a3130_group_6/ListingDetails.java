package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.a3130_group_6.LoginPage.validEmployee;

public class ListingDetails extends AppCompatActivity {
    DatabaseReference listingRef = null;
    FirebaseDatabase database;

    Button home, logout, back, apply;
    String [] listing = null;
    EditText applicationMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        listing = extras.getStringArray("details");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);

        applicationMessage = findViewById(R.id.applicationMessage);

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

    protected void setLatitudeDisplay(String lat){
        TextView textView = findViewById(R.id.latitudeInput);
        textView.setText(lat);
    }

    protected void setLongitudeDisplay(String longitude){
        TextView textView = findViewById(R.id.longitudeInput);
        textView.setText(longitude);
    }

    protected void setTextBox() {
        setTitleDisplay(listing[0]);
        setDescriptionDisplay(listing[1]);
        setUrgencyDisplay(listing[2]);
        setDateDisplay(listing[3]);
        setPayDisplay(listing[4]);
        setStatusDisplay(listing[5]);
        setLongitudeDisplay(listing[8]);
        setLatitudeDisplay(listing[9]);
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
    protected void applyToListing(){
        // save current employee under listing in database
        // save object user to database to Firebase
        listingRef.child(listing[7]).child("Listing").child(listing[6]).child("Applicants").child("Applied").child(validEmployee[0]).child("Message").setValue(applicationMessage.getText().toString());

        applicationMessage.setText(null);
    }

    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.backEmployeeHome:
            case R.id.employeeHome:
                startActivity(new Intent(this, EmployeeHomepage.class));
                break;
            case R.id.applyToListing:
                applyToListing();
                Toast.makeText(ListingDetails.this, "You have successfully applied to this listing with your new message", Toast.LENGTH_LONG).show();
                break;
            case R.id.Logout:
                //database.
                break;
        }

    }

}
