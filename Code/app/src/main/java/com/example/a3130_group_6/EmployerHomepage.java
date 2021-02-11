package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import java.io.Console;

public class EmployerHomepage extends AppCompatActivity {
    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        setSearchBar();
    }
    protected void setSearchBar(){
        searchBar =  findViewById(R.id.searchBar);
    }
    protected boolean searchFunctioning(){
        return searchBar!=null;
    }

}