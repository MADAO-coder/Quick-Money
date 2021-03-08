package com.example.a3130_group_6;

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
    List<Listing> listings = null;
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
        listView = findViewById(R.id.listingHistoryView);
        listings = new ArrayList<>();
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
                    doSomething(position);
                }
            });
        }else{
            NoListing.setVisibility(View.VISIBLE);
        }
    }
    public void doSomething(int position){
        // sendExtras() listing properties to listing detail page
        Listing temp = listings.get(position);
        // 6 == num properties of a listing
        String[] details = new String[6];
        details[0] = temp.getTaskTitle();
        details[1] = temp.getTaskDescription();
        details[2] = temp.getUrgency();
        details[3] = temp.getDate();
        details[4] = temp.getPay();
        details[5] = temp.getStatus();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, details);
        listView.setAdapter(adapter);
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
                            employerRef= database.getReferenceFromUrl(fireRef);
                            listingData = employer.child("Listing");
                            listingItr = listingData.getChildren().iterator();
                            while (listingItr.hasNext()) {
                                listings.add(listingItr.next().getValue(Listing.class));
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
