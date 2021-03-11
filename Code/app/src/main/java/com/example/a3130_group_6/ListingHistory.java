package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.List;

import static com.example.a3130_group_6.loginPage.validEmployer;

public class ListingHistory extends AppCompatActivity {
    DatabaseReference employerRef = null;
    FirebaseDatabase database;
    String fireRef;
    TextView NoListing = null;
    List<Listing> listings;
    List<String> keys;
    DataSnapshot employer;
    ListView listView=null;
    String[] listingsString;
    DataSnapshot listingData;
    Iterator<DataSnapshot> listingItr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_history);
        NoListing = findViewById(R.id.noListingMessage);
        listView = findViewById(R.id.employeeList);
        listings = new ArrayList<>();
        keys = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        fireRef =  "https://group-6-a830d-default-rtdb.firebaseio.com/Employer";
        employerRef= database.getReferenceFromUrl(fireRef);
        dbReadEmployer(employerRef);
        /* To-Do
        * change size of list view to fill more of screen
        * add listings label so know what loaded element is
        * add open/close status to a listing
        * add routing to listing history page on each element
        * */
    }

    /**
     * Function: This method updates converts ArrayList of Listings into String array to be understood by the List View
     * Parameters: View - view
     * Returns: void
     *
     */
    public void updateListing(){
        if(listings.size()>0){
            listingsString = new String[listings.size()];
            for(int i=0; i<listingsString.length; i++){
                listingsString[i] = "Task: " + listings.get(i).getTaskTitle() + "\tStatus: " + listings.get(i).getStatus();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listingsString);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sendToEdit(position);
                }
            });
        }else{
            NoListing.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Function: This method fills a list view with data, but Ty's code will implement an edit/listing details page
     * Parameters: Integer - position
     * Returns: void
     *
     */
    public void sendToEdit(int position){
        Intent switchIntent = new Intent(this, ListingApplicants.class);
        Listing temp = listings.get(position);
        // 6 == num properties of a listing
        // putExtra() listing properties to listing detail page -- currently sends to my listing applicants page
        switchIntent.putExtra("key", keys.get(position));
        switchIntent.putExtra("title", temp.getTaskTitle());
        switchIntent.putExtra("date", temp.getDate());
        switchIntent.putExtra("pay", temp.getPay());
        switchIntent.putExtra("status", temp.getStatus());
        switchIntent.putExtra("description", temp.getTaskDescription());
        switchIntent.putExtra("urgency", temp.getUrgency());
        startActivity(switchIntent);

    }

    /**
     * Function: This method reads the database and retrieves all of the employers listings
     * Parameters: DatabaseReference - db
     * Returns: void
     *
     */
    public void dbReadEmployer(DatabaseReference db){
        final DataSnapshot[] listing = new DataSnapshot[1];
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (dataItr.hasNext()) {
                    employer = dataItr.next();
                    if(employer.hasChild("Listing")){
                        // if employer location has Listings
                        if(employer.getValue(Employer.class).getUserName().equals(validEmployer[0])) {
                            fireRef = fireRef + "/" + employer.getValue(Employer.class).getUserName() + "/Listing";
                            NoListing.setVisibility(View.INVISIBLE);
                            employerRef= database.getReferenceFromUrl(fireRef);
                            listingData = employer.child("Listing");
                            listingItr = listingData.getChildren().iterator();
                            while (listingItr.hasNext()) {
                                listing[0] = listingItr.next();
                                keys.add(listing[0].getKey());
                                listings.add(listing[0].getValue(Listing.class));
                            }
                        }
                    }
                }
                updateListing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
}
