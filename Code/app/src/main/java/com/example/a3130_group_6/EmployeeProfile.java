package com.example.a3130_group_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.view.View;


import static com.example.a3130_group_6.loginPage.validEmployee;

public class EmployeeProfile extends AppCompatActivity {

    DatabaseReference employeeRef = null;

    String description, username, password, phone, email, name;
    EditText descriptionBox, nameView, usernameView, emailView, phoneView, passView;
    TextView statusView;
    Button submitButton, refreshButton;
    // use upload profile button to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        // get data from database
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        dbReadEmployer(employeeRef);
        // set all views
        setViews();

        // set button to update to database on click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update fields
                // define new employer object and set fields
                Employee employee = new Employee();
                employee.setName(nameView.getText().toString());
                //employers.setBiography(biography);
                employee.setUserName(usernameView.getText().toString());
                employee.setPassword(passView.getText().toString());
                employee.setPhone(phoneView.getText().toString());
                employee.setEmailAddress(emailView.getText().toString());
                // updates to db, but deletes associated listings
                updateToDatabase(employee);
            }
        });
        // set button to refresh profile fields on click
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                refreshPage();
            }
        });

    }
    public void setViews(){
        //statusView = findViewById(R.id.statusView);
        nameView = findViewById(R.id.employeeNameInput);
        descriptionBox = findViewById(R.id.descriptionBox);
        usernameView = findViewById(R.id.employeeUsernameInput);
        passView = findViewById(R.id.employeePassInput);
        phoneView = findViewById(R.id.employeePhoneNumInput);
        emailView = findViewById(R.id.employeeEmailInput);
        submitButton = (Button) findViewById(R.id.saveProfileUpdate);
        refreshButton = (Button) findViewById(R.id.refreshButton);
    }

    public void updateToDatabase(Employee employee){
        // save object user to database to Firebase
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/" + username);
        // 95 to 102 attempts to update without overwriting
        Map<String, Object> updates = new HashMap<>();
        updates.put(username, employee.getUserName());
        updates.put("password", employee.getPassword());
        updates.put("emailAddress", employee.getEmail());
        updates.put("name", employee.getName());
        updates.put("phone", employee.getPhone());
        employeeRef.updateChildren(updates);
        // below sets entirely new employee object
        //employeeRef.setValue(employer);
        //statusView.setText("Profile updated to database!");
    }
    public void refreshPage(){
        // save object user to database to Firebase
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employeeRef);
        //statusView.setText("Profile changes refreshed");
    }

    public void loadProfile(){
        nameView.setText(name);
        descriptionBox.setText(description);
        usernameView.setText(username);
        passView.setText(password);
        phoneView.setText(phone);
        emailView.setText(email);
    }

    //code from loginPage
    //Read data from dataBase and retrieve employer information
    public void dbReadEmployer(DatabaseReference db){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employerItr.hasNext()) {
                    //assume there will always be at least one employer
                    Employer employer = employerItr.next().getValue(Employer.class);
                    //need to check against correct value to retrieve the correct location
                    if (employer.getUserName().equals(validEmployee[0])){
                        username = employer.getUserName();
                        password = employer.getPassword();
                        phone = employer.getPhone();
                        email = employer.getEmailAddress();
                        name = employer.getName();
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
