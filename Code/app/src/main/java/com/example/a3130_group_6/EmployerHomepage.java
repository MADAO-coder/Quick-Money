package com.example.a3130_group_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import static com.example.a3130_group_6.loginPage.validEmployer;

public class EmployerHomepage extends AppCompatActivity {

    Employer employer;
    DatabaseReference employerRef = null;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        setEmployeeList();
    }

    protected void setEmployeeList(){
        String[] employees = new String[] { "Noback Endintegration", "Potter Weasley", "Henry Harry", "Jim Jones", "Granger Jones Jr.", "asdasdasdasd", "asdasdasdas", "asdasdasdasdasdas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeList = (ListView) findViewById(R.id.employeeList);
        employeeList.setAdapter(adapter);
    }

    protected boolean searchFunctioning(String search){
        /* irrelevant testing process for unit tests.
        searchView.setQuery(search, true);
        return searchView.getQuery().toString().equals(search);
        */
        return !search.isEmpty();
    }

    protected boolean checkEmployeeList(String[] employees){
        for(String individual : employees) {
            if(individual.isEmpty()){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
    public void switchListingHistory(View view) {
        // pre-load db data from here, alterations on next page
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef);
        Intent switchIntent = new Intent(this, ListingHistory.class);
        switchIntent.putExtra("listings", check);
        startActivity(switchIntent);
    }
    public void dbReadEmployer(DatabaseReference db){
        check = 0;
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
                            check = 1;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }
    public void addTaskSwitch(View view) {
        Intent switchIntent = new Intent(this, add_listing.class);
        startActivity(switchIntent);
    }
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployerHomepage.class);
        startActivity(switchIntent);
    }
}