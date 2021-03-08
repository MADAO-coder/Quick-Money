package com.example.a3130_group_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.view.View;


import static com.example.a3130_group_6.loginPage.validEmployee;

public class EmployeeProfile extends AppCompatActivity {

    DatabaseReference employeeRef = null;

    String description, username, password, phone, email, name;
    EditText descriptionBox, nameView, emailView, phoneView, passView;
    TextView usernameView, statusView;
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
                // define new employee object and set fields
                Employee employee = new Employee();
                setStatusMessage(true, "");
                if (isNameEmpty(nameView.getText().toString())) {
                    setStatusMessage(false, "Error: Please fill in name");
                }
                else if (isEmailEmpty(emailView.getText().toString().trim())) {
                    setStatusMessage(false, "Error: Please fill in email");
                }
                else if (isPhoneNumEmpty(phoneView.getText().toString().trim())) {
                    setStatusMessage(false, "Error: Please fill in phone number");
                }
                else if (isPasswordEmpty(passView.getText().toString().trim())) {
                    setStatusMessage(false,"Error: Please fill in password");
                }
                else {
                    employee.setName(nameView.getText().toString());
                    //employees.setDescription(description);
                    employee.setUserName(usernameView.getText().toString());
                    employee.setPassword(passView.getText().toString());
                    employee.setPhone(phoneView.getText().toString());
                    employee.setEmailAddress(emailView.getText().toString());
                    // updates to db
                    updateToDatabase(employee);
                }

            }
        });
        // set button to refresh profile fields on click
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                refreshPage();
                setStatusMessage(true, "");
            }
        });

    }
    public void setViews(){
        statusView = findViewById(R.id.employeeStatusLabel);
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
        Map<String, Object> updates = new HashMap<>();
        updates.put("userName", employee.getUserName());
        updates.put("password", employee.getPassword());
        updates.put("email", employee.getEmail());
        updates.put("name", employee.getName());
        updates.put("phone", employee.getPhone());
        employeeRef.updateChildren(updates);
        // below sets entirely new employee object
        employeeRef.setValue(employee);
        setStatusMessage(true, "Profile updated to database!");
    }
    public void refreshPage(){
        // save object user to database to Firebase
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
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

    protected boolean isNameEmpty (String name) {
        return name.isEmpty();
    }

    protected boolean isEmailEmpty (String email) {
        return email.isEmpty();
    }

    protected boolean isPhoneNumEmpty (String phoneNum) {
        return phoneNum.isEmpty();
    }

    protected boolean isPasswordEmpty (String password) {
        return password.isEmpty();
    }

    protected void setStatusMessage(Boolean success, String message) {
        TextView statusLabel = findViewById(R.id.employeeStatusLabel);
        if (success) {
            statusLabel.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            statusLabel.setTextColor(Color.RED);
        }
        statusLabel.setText(message);
    }


}
