package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmployerHomepage extends AppCompatActivity {
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        setSearchView();
    }

    protected void setSearchView(){
        searchView = (SearchView) findViewById(R.id.searchBar);
    }

    protected boolean searchFunctioning(){
        //empty searchview without data list to follow
        return searchView==null;
    }
    protected String searchQuery(){
        return searchView.getQuery().toString().trim();
    }

}