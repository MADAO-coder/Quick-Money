package com.example.a3130_group_6;

import android.os.Bundle;
import android.view.View;
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
    String fireRef;
    TextView NoListing = null;
    List<Listing> listings;
    DataSnapshot employer;
    ListView listView=null;
    String[] listingsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_history);
        NoListing = findViewById(R.id.noListingMessage);
        listView = findViewById(R.id.listingHistoryView);
        listings = new ArrayList<>();
        fireRef =  "https://group-6-a830d-default-rtdb.firebaseio.com/Employer";
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl(fireRef);
        dbReadEmployer(employerRef);
        retrieveListings(employerRef);
    }
    public void updateListing(){
        if(listings!=null){
            listingsString = new String[listings.size()];
            for(int i=0; i<listingsString.length; i++){
                listingsString[i] = listings.get(i).getTaskTitle();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listingsString);
            listView.setAdapter(adapter);
        }
    }

    // function is asynchronous, causing issues
    //Read data from dataBase and make employers' userName and password into ArrayList.
    public void dbReadEmployer(DatabaseReference db){
        // not iterating through the onDataChange or anything for some reason
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
                            employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl(fireRef);
                            break;
                        }
                    }else{
                        //do something
                        NoListing.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
    public void retrieveListings(DatabaseReference db){
        // not iterating through the onDataChange or anything for some reason
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // below line retrieves all employer data
                Iterator<DataSnapshot> dataItr = dataSnapshot.getChildren().iterator();
                //Read listings, add to list
                while (dataItr.hasNext()) {
                    // need to get each listing, but their key value is a stupid unintelligible string
                    listings.add(dataItr.next().child("Listing").child("-MUzCFONR80M1C9TRVxk").getValue(Listing.class));
                    // shouldnt break, should properly retrieve each listing (based on some undefined criteria), instead of just one
                    break;
                }
                updateListing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
}
