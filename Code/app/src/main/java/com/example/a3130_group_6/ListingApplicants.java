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

import static com.example.a3130_group_6.loginPage.validEmployer;

public class ListingApplicants extends AppCompatActivity {

    TextView applicantStatus;
    ListView applicantList;
    String key, employer, title, date, pay, status, description, urgency, listingLocation, employeeName;
    DatabaseReference listingRef;
    FirebaseDatabase db;
    Iterator<DataSnapshot> applicantItr;
    ArrayList<String> applicantNames;
    ArrayList<String> applicantValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_applicants);
        // retrieve extras
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        employer = intent.getStringExtra("employer");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        pay = intent.getStringExtra("pay");
        status = intent.getStringExtra("status");
        description = intent.getStringExtra("description");
        urgency = intent.getStringExtra("urgency");
        applicantNames = new ArrayList<>();
        applicantValues = new ArrayList<>();
        applicantStatus = findViewById(R.id.noApplicantsMessage);
        applicantList = findViewById(R.id.applicantList);

        // db stuff
        db = FirebaseDatabase.getInstance();
        listingLocation = "https://group-6-a830d-default-rtdb.firebaseio.com/Employer/" + employer + "/Listing/" + key;
        listingRef = db.getReferenceFromUrl(listingLocation);
        // access specific listing to retrieve applicants - if they exist
        checkForApplicants(listingRef);

        // display applicants in scrolling list view
        //AT 3


        // AT 5
        // on click send to employee details page
    }

    public void checkForApplicants(DatabaseReference db){
        // connect to db, check listing at extras
        //AT 1
        // if no exist - display error label
        final DataSnapshot[] lHold = new DataSnapshot[1];
        final DataSnapshot[] applicant = new DataSnapshot[1];
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.

                lHold[0] = dataItr.next();
                if(lHold[0].getKey().equals("Applicants")){
                    // if employer location has Listings
                    // do something
                    applicantItr = lHold[0].getChildren().iterator();
                    applicant[0] = applicantItr.next();
                    if (applicant[0]!=null) {
                        if(applicantItr.hasNext()){
                            while(applicantItr.hasNext()){
                                applicantNames.add(applicant[0].getKey());
                                applicantValues.add(applicant[0].getValue().toString());
                            }
                        }else{
                            applicantNames.add(applicant[0].getKey());
                            applicantValues.add(applicant[0].getValue().toString());
                        }
                        updateApplicants();
                    }
                }
                else{
                    applicantStatus.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
    /**
     * Function: This method updates converts ArrayList of applicants into a String array to be understood by the List View
     * Parameters: none
     * Returns: void
     *
     */
    public void updateApplicants(){
        if(applicantNames.size()>0){
            String[] applicantsString = new String[applicantNames.size()];
            for(int i=0; i<applicantsString.length; i++){
                applicantsString[i] = applicantNames.get(i) + "\tStatus:" + applicantValues.get(i);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, applicantsString);
            applicantList.setAdapter(adapter);
            applicantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }else{
            applicantStatus.setVisibility(View.VISIBLE);
        }
    }
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployerHomepage.class);
        startActivity(switchIntent);
    }
}
