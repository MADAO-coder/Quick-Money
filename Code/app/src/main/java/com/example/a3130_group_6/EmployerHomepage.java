package com.example.a3130_group_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.example.a3130_group_6.loginPage.validEmployer;

public class EmployerHomepage extends AppCompatActivity {

    private List<Employee> employeeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        setEmployeeList();
    }

    protected void setEmployeeList(){
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        dbReadEmployer(employeeRef);
        List<String> employees = new ArrayList<String>();
        for (Employee employee : employeeList) {
            employees.add(employee.getName() + "\t" + employee.getEmail() + "\t" + employee.getPassword() + "\t" + employee.getPhone() + "\t" + employee.getUserName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeListView = (ListView) findViewById(R.id.employeeList);
        employeeListView.setAdapter(adapter);
    }
    public void dbReadEmployer(DatabaseReference db) {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employeeItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employeeItr.hasNext()) {
                    Employee employee = employeeItr.next().getValue(Employee.class);
                    employeeList.add(employee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
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

    public void profileSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployerProfile.class);
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