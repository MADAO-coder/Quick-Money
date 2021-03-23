package com.example.a3130_group_6;

import android.annotation.SuppressLint;
import android.location.Location;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.a3130_group_6.LoginPage.validEmployee;
import static com.example.a3130_group_6.LoginPage.validEmployer;

public class EmployeeHomepage extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference employerRef;
    FirebaseDatabase db;
    DataSnapshot listingData;
    ListView taskList;
    Iterator<DataSnapshot> listingItr;
    Button employeeProfileButton, sortButton;
    private EmployeeProfile employeeProfile;
    private ArrayList<Listing> listings = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> employers = new ArrayList<>();
    private UserLocation user;
    ArrayList<Listing> locationListing = new ArrayList<>();
    DatabaseReference employeeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);

        taskList = (findViewById(R.id.TaskList));
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
    protected void setTaskList(ArrayList<Listing> list){
        String[] listingsString = new String[list.size()];
        for(int i=0; i<listingsString.length; i++){
            listingsString[i] = list.get(i).getTaskTitle() + "\tStatus:" + list.get(i).getStatus();
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
                dbReadEmployeeLocation(employeeRef);
                // setTaskList(listings);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     *  Method to show the drop down menu
     */
    protected void showDropDownMenu() {
        Spinner sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
        List<String> dropDownList = new ArrayList<String>();
        dropDownList.add("sort by urgency");
        dropDownList.add("sort by date");
        dropDownList.add("sort by location");
        @SuppressLint("ResourceType") ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        itemListAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(itemListAdapter);
    }


    private void sortByUrgency(){
        Collections.sort(locationListing, new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                return l1.getUrgency().compareTo(l2.getUrgency());
            }
        });
        setTaskList(locationListing);
    }
    
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
        taskDistance = sortByValue(taskDistance);

        System.out.println(taskDistance);

        // adding all the keys to an arraylist
        for ( Listing listKey : taskDistance.keySet() ) {
            locationListing.add(listKey);
        }

        // showing the filtered listings in a textview
        setTaskList(locationListing);
    }


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

    // Citation: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
    // function to sort hashmap by values
    public static HashMap<Listing, Double> sortByValue(HashMap<Listing, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Listing, Double> > list =
                new LinkedList<Map.Entry<Listing, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Listing, Double> >() {
            public int compare(Map.Entry<Listing, Double> o1,
                               Map.Entry<Listing, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Listing, Double> temp = new LinkedHashMap<Listing, Double>();
        for (Map.Entry<Listing, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
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
     * Method to get the text from the dropdown menu for sort
     * @return
     * The following method has been used from Assignment 4
     */
    protected String getSelectedItem() {
        Spinner itemList = (Spinner) findViewById(R.id.sortSpinner);
        return itemList.getSelectedItem().toString();
    }

    /**
     * Created button just for testing/viewing purposes. Can/will delete after integration of navigation bar
     */
    public void onClick(View v) {

        if(v.getId() == R.id.employeeProfileButton){
         employeeProfileSwitch();
        }
        else if (v.getId() == R.id.sortButton) {
            String selectedItem = getSelectedItem();
            if (selectedItem.equals("sort by urgency")) {
                sortByUrgency();
            } else if (selectedItem.equals("sort by date")) {

            } else if (selectedItem.equals("sort by location")) {
                dbReadEmployeeLocation(employeeRef);
            }
        }
    }

    // method to create a Toast
    private void createToast(String message){
        Toast toast = Toast.makeText(this, message,Toast.LENGTH_LONG);
        toast.show();
    }

}
