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

public class EmployeeHomepage extends AppCompatActivity {
    DatabaseReference employerRef;
    DataSnapshot listingData;
    Iterator<DataSnapshot> listingItr;
    ArrayList<Listing> listings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        employerRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        listings = new ArrayList<>();
        dbReadEmployees(employerRef, listings);
        ListView listView = findViewById(R.id.employeeList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                applyToListing();
            }
        });
    }

    /**
     * Function: This method applies an employee to a listing, and saves their reference under the Listing
     * Parameters: none
     * Returns: void
     *
     */
    protected void applyToListing(){
        // save to database

        //notify employer
        
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
        ListView taskList = (ListView) findViewById(R.id.TaskList);
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
                            listings.add(listingItr.next().getValue(Listing.class));
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
