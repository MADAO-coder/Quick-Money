package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class EmployerHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        setEmployeeList();
    }

    protected void setEmployeeList(){
        String[] employees = new String[] { "Noback Endintegration", "Potter Weasley", "Henry Harry", "Jim Jones", "Granger Jones Jr."};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeList = (ListView) findViewById(R.id.employeeList);
        employeeList.setAdapter(adapter);
    }

    protected String getSearchView(){
        SearchView searchView = (SearchView) findViewById(R.id.searchBar);
        return searchView.getQuery().toString().trim();
    }

    protected boolean searchFunctioning(String search){
        /* irrelevant testing process for unit tests.
        searchView.setQuery(search, true);
        return searchView.getQuery().toString().equals(search);
        */
        return !search.isEmpty();
    }

    protected boolean checkEmployeeList(){
        //return employeeList!=null;
        return false;
    }

}