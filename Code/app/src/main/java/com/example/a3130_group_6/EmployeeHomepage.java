package com.example.a3130_group_6;

import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class EmployeeHomepage extends AppCompatActivity {
    DatabaseReference employee = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDatabase();
        setContentView(R.layout.activity_employee_homepage);
        setEmployeeList();

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(this::logout);
    }

    protected void setEmployeeList(){
        String[] employees = new String[] { "Noback Endintegration", "Potter Weasley", "Henry Harry", "Jim Jones", "Granger Jones Jr.", "asdasdasdasd", "asdasdasdas", "asdasdasdasdasdas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeList = (ListView) findViewById(R.id.TaskList);
        employeeList.setAdapter(adapter);
    }
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployeeHomepage.class);
        startActivity(switchIntent);
    }

    protected void initializeDatabase() {
        employee = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
    }


    public void logout(View view){
    String username =employee.
        if (!employee.equals(null)){
            employee.getDatabase().getReference().
        }


    }

}
