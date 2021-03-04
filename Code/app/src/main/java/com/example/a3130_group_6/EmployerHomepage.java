package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

public class EmployerHomepage extends AppCompatActivity {

    Employer employer;
    DatabaseReference employerRef = null;
    int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        setEmployeeList();
    }

    protected void setEmployeeList(){
        String[] employees = new String[] { "Noback Endintegration", "Potter Weasley", "Henry Harry", "Jim Jones", "Granger Jones Jr.", "asdasdasdasd", "asdasdasdas", "asdasdasdasdasdas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeList = (ListView) findViewById(R.id.listingHistoryView);
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
        Intent switchIntent = new Intent(this, ListingHistory.class);
        startActivity(switchIntent);
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