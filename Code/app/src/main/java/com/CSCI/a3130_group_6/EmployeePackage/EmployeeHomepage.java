package com.CSCI.a3130_group_6.EmployeePackage;

import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Button;
import android.widget.ListView;

import com.CSCI.a3130_group_6.Listings.Listing;
import com.CSCI.a3130_group_6.Listings.ListingDetails;
import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.HelperClases.SortHelper;
import com.CSCI.a3130_group_6.HelperClases.UserLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.CSCI.a3130_group_6.Registration.LoginPage.validEmployee;

public class EmployeeHomepage extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference employerRef;
    FirebaseDatabase db;
    DataSnapshot listingData;
    ListView taskList;
    Iterator<DataSnapshot> listingItr;
    ArrayList<Listing> listings;
    ArrayList<String> keys;
    ArrayList<String> employers;
    EditText search;
    ArrayAdapter<String> adapter;
    String[] listingsString;
    String [] details;
    List<String> employerName;

    Button employeeProfileButton, sortButton;
    private EmployeeProfile employeeProfile;
    private UserLocation user;
    ArrayList<Listing> locationListing = new ArrayList<>();
    DatabaseReference employeeRef;
    SortHelper sort = new SortHelper();

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
        db = FirebaseDatabase.getInstance();
        employeeRef = db.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        employerRef = db.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        employeeProfile = new EmployeeProfile();

        employeeProfileButton = findViewById(R.id.employeeProfileButton); // CREATED JUST TO VIEWING PURPOSES, CAN DELETE AFTER INTEGRATION OF NAV BAR
        employeeProfileButton.setOnClickListener(this); // CREATED JUST TO VIEWING PURPOSES, CAN DELETE AFTER INTEGRATION OF NAV BAR
        sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(this);

        dbReadEmployees(employerRef, listings);

        this.showDropDownMenu();

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
    }

    /**
     * Function: This method converts all listings into one string array that can be understood by my list view
     * Parameters: none
     * Returns: void
     *
     */
    public void setTaskList(ArrayList<Listing> list){
        String[] listingsString = new String[list.size()];
        for(int i=0; i<listingsString.length; i++){

            //ToDo: check if a task is open or not - do not show the closed tasks - Bryson
//            listingsString[i] = list.get(i).getTaskTitle() + "\tStatus:" + list.get(i).getStatus() + "\tDate:" + list.get(i).getUrgency();
            listingsString[i] = list.get(i).getTaskTitle();
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
                dbReadEmployeeLocation(employeeRef);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     *  Method to show the drop down menu
     */
    public void showDropDownMenu() {
        Spinner sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
        List<String> dropDownList = new ArrayList<String>();
        dropDownList.add("sort by urgency");
        dropDownList.add("sort by date");
        dropDownList.add("sort by location");
        @SuppressLint("ResourceType") ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        itemListAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(itemListAdapter);
    }

    /**
     * Function: This method sorts the listing by date
     * Parameters:
     * Return: void
     */
    private void sortByDate(){
        locationListing = sort.sortDatesDescending(locationListing);
        setTaskList(locationListing);
    }

    /**
     * Function: This method sorts the listing by urgency
     * Parameters:
     * Return: void
     */
    private void sortByUrgency(){
        Collections.sort(locationListing, new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                return l2.getUrgency().compareTo(l1.getUrgency());
            }
        });
        setTaskList(locationListing);
    }

    public void editListing(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("details", details);
        Intent intent = new Intent(this, ListingDetails.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Function: This method sorts the listing by location
     * Parameters: UserLocation
     * Return: void
     */
    private void sortByLocation(UserLocation user) {
        HashMap<Listing, Double> taskDistance = new HashMap<Listing, Double>();
        for (int i = 0; i < listings.size(); i++) {
            double latitude = listings.get(i).getLocation().getLatitude();
            double longitude = listings.get(i).getLocation().getLongitude();
            double diff = user.calculateDistanceInKilometer(latitude, longitude);

            if (diff < Double.parseDouble(user.getRadius())){
                // adding listing which is in the user radius to the hashmap
                taskDistance.put(listings.get(i), diff);
            }
        }

        // sorting the hashmap based on values
        taskDistance = sort.sortByValue(taskDistance);
        System.out.println(taskDistance);

        // adding all the keys to an arraylist
        locationListing.clear();
        for ( Listing listKey : taskDistance.keySet() ) {
            locationListing.add(listKey);
        }

        // showing the filtered listings in a textview
        setTaskList(locationListing);
    }

    /**
     * Function: Method to read the employee's location from the database
     * Parameters: DatabaseReferences
     * Return: void
     */
    public void dbReadEmployeeLocation(DatabaseReference db1){

        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employeeItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employeeItr.hasNext()) {
                    //assume there will always be at least one employer
                    Employee employee = employeeItr.next().getValue(Employee.class);
                    //need to check against correct value to retrieve the correct location
                    if (employee.getUserName().equals(validEmployee[0])){
                        user = new UserLocation();
                        user = dataSnapshot.child(validEmployee[0]).child("Location").getValue(UserLocation.class);
                        sortByLocation(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /**
     * Function: This method switches intent to the employee homepage
     * Parameters: View view
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
     * Method to get the text from the dropdown menu for sort
     * @return
     * The following method has been used from Assignment 4
     */
    protected String getSelectedItem() {
        Spinner itemList = (Spinner) findViewById(R.id.sortSpinner);
        return itemList.getSelectedItem().toString();
    }


    public void onClick(View v) {
        if(v.getId() == R.id.employeeProfileButton){
         employeeProfileSwitch();
        }
        else if (v.getId() == R.id.sortButton) {
            String selectedItem = getSelectedItem();
            if (selectedItem.equals("sort by urgency")) {
                sortByUrgency();
            } else if (selectedItem.equals("sort by date")) {
                sortByDate();
            } else if (selectedItem.equals("sort by location")) {
                dbReadEmployeeLocation(employeeRef);
            }
        }
    }
}
