package com.example.a3130_group_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.a3130_group_6.loginPage.validEmployee;


public class EmployeeProfile extends AppCompatActivity {

    DatabaseReference employeeRef = null;
    private List<String> employee_userName_list = new ArrayList<>();//List to store password getting from db for Employee object
    private List<String> employee_password_list = new ArrayList<>();//List to store password getting from db for Employee object
    String description, username, password, phone, email, name;

    EditText nameView;

    // use upload profile button to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        dbReadEmployer(employeeRef);//Get data from database
        nameView = findViewById(R.id.employeeNameInput);
        nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // business logic for changing the db profile data
                // dont call db, will update every time smth changes
                // use button function to call db one time when all changes in form are desired to be submitted
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // asynchronous task function enables front end thread to wait for backend thread
        // need to setup so UI thread waits for backend thread
        // google resources + ask vikash

    }

    public void loadProfile(){
        nameView.setText(name);
    }

    //code from loginPage
    //Read data from dataBase and retrieve employer information
    public void dbReadEmployer(DatabaseReference db){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employeeItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employeeItr.hasNext()) {
                    //assume there will always be at least one employer
                    Employee employee = employeeItr.next().getValue(Employee.class);
                    //need to check against correct value to retrieve the correct location
                    if (employee.getUserName().equals(validEmployee[0])){
                        username = employee.getUserName();
                        password = employee.getPassword();
                        phone = employee.getPhone();
                        email = employee.getEmail();
                        name = employee.getName();
                        break;
                    }
                }
                loadProfile();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

}
