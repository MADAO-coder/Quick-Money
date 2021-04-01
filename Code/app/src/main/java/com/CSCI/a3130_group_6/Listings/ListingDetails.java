package com.CSCI.a3130_group_6.Listings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.CSCI.a3130_group_6.EmployeePackage.EmployeeHomepage;
import com.CSCI.a3130_group_6.HelperClases.EmployerChatList;
import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.Registration.LoginPage;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.CSCI.a3130_group_6.Registration.LoginPage.validEmployee;

public class ListingDetails extends AppCompatActivity {
    DatabaseReference listingRef = null;
    FirebaseDatabase database;
    Button  apply;
    String [] listing = null;
    EditText applicationMessage;
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        listing = extras.getStringArray("details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);
        applicationMessage = findViewById(R.id.applicationMessage);
        apply = findViewById(R.id.applyToListing);
        apply.setOnClickListener(this::onClick);
        database = FirebaseDatabase.getInstance();
        listingRef = database.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        setTextBox();
        tab =findViewById(R.id.tabs);
        TabLayout.Tab activeTab = tab.getTabAt(3);
        activeTab.select();
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getText().toString()) {
                    case "Listing":
                        switchListingHistory();
                        break;
                    case "Profile":
                        profileSwitch();
                        break;
                    case "Logout":
                        LogoutSwitch();
                        break;
                    case "Home":
                        homepageSwitch();
                        break;
                    case "Chat":
                        chatSwitch();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
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
     */
    protected void applyToListing(){
        // save current employee under listing in database
        // save object user to database to Firebase
        listingRef.child(listing[7]).child("Listing").child(listing[6]).child("Applicants").child("Applied").child(validEmployee[0]).child("Message").setValue(applicationMessage.getText().toString());
        applicationMessage.setText(null);
    }
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.applyToListing:
                applyToListing();
                Toast.makeText(ListingDetails.this, "You have successfully applied to this listing with your new message", Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void profileSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), com.CSCI.a3130_group_6.EmployeePackage.EmployeeProfile.class);
        startActivity(switchIntent);
    }
    public void homepageSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), EmployeeHomepage.class);
        startActivity(switchIntent);
    }

    public void switchListingHistory() {
        Intent switchIntent = new Intent(getApplicationContext(), ListingHistory.class);
        startActivity(switchIntent);
    }
    public void LogoutSwitch() {
        LoginPage.validEmployee = null;
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent switchIntent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(switchIntent);
    }
    public void chatSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), EmployerChatList.class);
        startActivity(switchIntent);
    }
}
