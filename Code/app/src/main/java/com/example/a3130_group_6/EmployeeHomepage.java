package com.example.a3130_group_6;

<<<<<<< HEAD
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
=======
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
>>>>>>> 9b5b74e2ba1f67aa33f70bdc7670979876953b7c
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.TextView;
=======
>>>>>>> 9b5b74e2ba1f67aa33f70bdc7670979876953b7c

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.a3130_group_6.LoginPage.validEmployee;
import static com.example.a3130_group_6.LoginPage.validEmployer;

public class EmployeeHomepage extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference employerRef;
    FirebaseDatabase db;
    DataSnapshot listingData;
    ListView taskList;
    Iterator<DataSnapshot> listingItr;
    ArrayList<Listing> listings;
    ArrayList<String> keys;
    ArrayList<String> employers;
<<<<<<< HEAD
    EditText search;
    ArrayAdapter<String> adapter;
    String[] listingsString;
=======
    String [] details;
    List<String> employerName;

>>>>>>> 9b5b74e2ba1f67aa33f70bdc7670979876953b7c


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        taskList = (findViewById(R.id.TaskList));
        listings = new ArrayList<>();
        keys = new ArrayList<>();
        employers = new ArrayList<>();
        employerName = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        search = findViewById(R.id.newSearchBar);
        TextView searchStatus = findViewById(R.id.searchStatus);

        Button employeeProfileButton = findViewById(R.id.employeeProfileButton); // CREATED JUST TO VIEWING PURPOSES, CAN DELETE AFTER INTEGRATION OF NAV BAR
        employeeProfileButton.setOnClickListener(this); // CREATED JUST TO VIEWING PURPOSES, CAN DELETE AFTER INTEGRATION OF NAV BAR

        employerRef = db.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployees(employerRef, listings);
<<<<<<< HEAD

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                applyToListing(position);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EmployeeHomepage.this.adapter.getFilter().filter(s);

                adapter.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int count) {
                        if (count == 0){
                            searchStatus.setText("No tasks available based on search");
                        } else {
                            searchStatus.setText("");
                        }
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
=======
>>>>>>> 9b5b74e2ba1f67aa33f70bdc7670979876953b7c
    }

    /**
     * Function: This method converts all listings into one string array that can be understood by my list view
     * Parameters: none
     * Returns: void
     *
     */
    protected void setTaskList(){
        listingsString = new String[listings.size()];
        for(int i=0; i<listingsString.length; i++){
            listingsString[i] = listings.get(i).getTaskTitle() + "\tStatus:" + listings.get(i).getStatus();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listingsString);
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Listing temp = listings.get(position);
                    UserLocation location = temp.getLocation();
                    details = new String[10];
                    details[0] = temp.getTaskTitle();
                    details[1] = temp.getTaskDescription();
                    details[2] = temp.getUrgency();
                    details[3] = temp.getDate();
                    details[4] = temp.getPay();
                    details[5] = temp.getStatus();
                    details[6] = keys.get(position);
                    details[7] = employers.get(position);
                    details[8] = location.getLongitude().toString();
                    details[9] = location.getLatitude().toString();
                    editListing(view);
            }
        });

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


    public void editListing(View view){
        Bundle bundle = new Bundle();
        bundle.putStringArray("details", details);
        Intent intent = new Intent(this, ListingDetails.class);
        intent.putExtras(bundle);
        startActivity(intent);
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


    /**
     * Created button just for testing/viewing purposes. Can/will delete after integration of navigation bar
     */
    public void employeeProfileSwitch() {
        Intent switchIntent = new Intent(this, EmployeeProfile.class);
        startActivity(switchIntent);
    }

    /**
     * Created "Test Button" just for Employee Profile testing/viewing purposes. Can/will delete after integration of navigation bar
     */
    public void onClick(View v) {
        employeeProfileSwitch();
    }

}
