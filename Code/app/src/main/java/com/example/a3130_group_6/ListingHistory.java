package com.example.a3130_group_6;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import static com.example.a3130_group_6.loginPage.validEmployer;

public class ListingHistory extends AppCompatActivity {
    DatabaseReference employerRef = null;
    DatabaseReference ListingRef = null;
    String fireRef = "https://group-6-a830d-default-rtdb.firebaseio.com/Employer";
    TextView NoListing = null;
    int check;
    Employer employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_history);
        NoListing = findViewById(R.id.noListingMessage);
        check = getIntent().getExtras().getInt("listing");
        if(check==0){
            //have no listings
            //apparently NoListing still null - likely issue of synchronicity
            NoListing.setVisibility(View.VISIBLE);
        }else if(check==1){
            //have listings
            //NoListing = findViewById(R.id.noListingMessage);
            employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl(fireRef);
            dbReadEmployer(employerRef);
            // below MAY return null
            employerRef = FirebaseDatabase.getInstance().getReferenceFromUrl(fireRef);
            dbReadEmployer(employerRef);
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
                    if(dataItr.next().hasChild("Listing")){
                        employer = dataItr.next().getValue(Employer.class);
                        // if above matches, set employer location to check for Listings
                        if(employer.getUserName().equals(validEmployer[0])) {
                            //checks if next employer has Listing
                            fireRef = fireRef + "/" + employer.getUserName() + "/Listing";
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
}
