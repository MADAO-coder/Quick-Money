package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

public class EmployerHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
    }

    protected void setEmployeeList(){
        ListView employeeList = (ListView) findViewById(R.id.searchBar);
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