package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        setEmployeeList();
    }

    protected void setEmployeeList(){
        String[] employees = new String[] { "Noback Endintegration", "Potter Weasley", "Henry Harry", "Jim Jones", "Granger Jones Jr.", "asdasdasdasd", "asdasdasdas", "asdasdasdasdasdas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeList = (ListView) findViewById(R.id.employeeList);
        employeeList.setAdapter(adapter);
    }

}
