package com.example.a3130_group_6;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

import static com.example.a3130_group_6.loginPage.validEmployee;

public class EmployeeHomepage extends AppCompatActivity {
    DatabaseReference employerRef;
    FirebaseDatabase db;
    DataSnapshot listingData;
    ListView taskList;
    Iterator<DataSnapshot> listingItr;
    ArrayList<Listing> listings;
    ArrayList<String> keys;
    ArrayList<String> employers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        taskList = (findViewById(R.id.TaskList));
        listings = new ArrayList<>();
        keys = new ArrayList<>();
        employers = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        employerRef = db.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployees(employerRef, listings);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                applyToListing(position);
            }
        });

    }

    /**
     * Function: This method applies an employee to a listing
     * Parameters: none
     * Returns: void
     *
     */
    protected void applyToListing(int position){
        // TODO: get below to update to database properly
        // save current employee under listing in database
        //save object user to database to Firebase
        employerRef.child(employers.get(position)).child("Listing").child(keys.get(position)).child("Applicants").child(validEmployee[0]).setValue("Applying");
    }


    /**
     * Function: This method converts all listings into one string array that can be understood by my list view
     * Parameters: none
     * Returns: void
     *
     */
    protected void setTaskList(){
        String[] listingsString = new String[listings.size()];
        for(int i=0; i<listingsString.length; i++){
            listingsString[i] = listings.get(i).getTaskTitle() + "\tStatus:" + listings.get(i).getStatus();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listingsString);
        taskList.setAdapter(adapter);
    }

    /**
     * Function: This method reads the database and retrieves an ArrayList of Listings
     * Parameters: DatabaseReference - db, ArrayList<Listing> - listings
     * Returns: void
     *
     */
    public void dbReadEmployees(DatabaseReference db, ArrayList<Listing> listings){
        final DataSnapshot[] employer = new DataSnapshot[1];
        final DataSnapshot[] listing = new DataSnapshot[1];
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employerItr.hasNext()) {
                    employer[0] = employerItr.next();
                    if(employer[0].hasChild("Listing")){
                        // if employer location has Listings, add to the list
                        listingData = employer[0].child("Listing");
                        listingItr = listingData.getChildren().iterator();
                        while (listingItr.hasNext()) {
                            listing[0] = listingItr.next();
                            // add key + employer + listing to separate lists
                            keys.add(listing[0].getKey());
                            employers.add(employer[0].getKey());
                            listings.add(listing[0].getValue(Listing.class));
                        }
                    }
                }
                setTaskList();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
    /**
     * Function: This method switches intent to the employee homepage
     * Parameters: View - view
     * Returns: void
     *
     */
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployeeHomepage.class);
        startActivity(switchIntent);
    }

}
