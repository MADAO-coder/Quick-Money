package com.CSCI.a3130_group_6.EmployeePackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.HelperClases.UserLocation;
import com.CSCI.a3130_group_6.HelperClases.Config;
import com.CSCI.a3130_group_6.PayPal.PayActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeView extends AppCompatActivity {
    DatabaseReference employeeRef;
    String description, username, password, phone, email, employeeName, radius, clientID, amount, key, employerName;
    EditText descriptionBox, nameView, emailView, phoneView, passView, radiusView, clientIDView;
    TextView usernameView, statusView;
    ImageView imageView;
    Button submitButton, refreshButton, imageButton, uploadResume, selectResume, pay;
    UserLocation user;
    String wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        // create based off employee profile? hide buttons + change editable
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        key = intent.getStringExtra("key");
        employerName = intent.getStringExtra("employerName");
        employeeName = intent.getStringExtra("employeeName");
        pay = (Button) findViewById(R.id.payBtn);
        pay.setVisibility(View.VISIBLE);
        pay.setEnabled(true);
        // set database
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/"+employeeName);
        // set all views
        setViews();

        // disable all buttons
        disableButtons();
        // set fields to uneditable
        disableFields();

        //set onclick for pay button
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPayment(v);
            }
        });

        // get data
        getEmployeeDetails(employeeRef);

    }

    public void sendPayment(View view){
        Config.setID(clientID);
        Intent switchIntent = new Intent(this, PayActivity.class);
        switchIntent.putExtra("name", username);
        switchIntent.putExtra("amount", amount);
        switchIntent.putExtra("key", key);
        switchIntent.putExtra("employerName", employerName);
        switchIntent.putExtra("wallet", wallet);
        startActivity(switchIntent);
    }

    /**
     * Function: This method disables all fields on the employee profile
     * Parameters: None
     * Returns: void
     *
     */
    public void disableFields(){
        nameView.setClickable(false);
        nameView.setEnabled(false);

        descriptionBox.setClickable(false);
        descriptionBox.setEnabled(false);

        usernameView.setClickable(false);
        usernameView.setEnabled(false);

        passView.setClickable(false);
        passView.setEnabled(false);

        phoneView.setClickable(false);
        phoneView.setEnabled(false);

        emailView.setClickable(false);
        emailView.setEnabled(false);

        radiusView.setClickable(false);
        radiusView.setEnabled(false);

        clientIDView.setClickable(false);
        clientIDView.setEnabled(false);
    }
    /**
     * Function: This method disables all buttons on the employee profile
     * Parameters: None
     * Returns: void
     *
     */
    public void disableButtons(){
        submitButton.setEnabled(false);
        refreshButton.setEnabled(false);
        imageButton.setEnabled(false);
        uploadResume.setEnabled(false);
        selectResume.setEnabled(false);
    }
    /**
     * Function: This method sets the views
     * Parameters: none
     * Returns: void
     *
     */
    public void setViews(){
        nameView = findViewById(R.id.applicantName);
        imageView = findViewById(R.id.profilePicture);
        statusView = findViewById(R.id.employeeStatusLabel);
        usernameView = findViewById(R.id.applicantUserName);
        passView = findViewById(R.id.applicantMessage);
        phoneView = findViewById(R.id.applicantPhoneNum);
        emailView = findViewById(R.id.applicantEmail);
        radiusView = findViewById(R.id.applicantRadius);

        submitButton = (Button) findViewById(R.id.accept);
        clientIDView = findViewById(R.id.ClientIDInput);
        refreshButton = (Button) findViewById(R.id.employerHome);
        imageButton = findViewById(R.id.profileImageButton);

        uploadResume = findViewById(R.id.uploadResume);
        selectResume = findViewById(R.id.seeResume);
    }
    /**
     * Function: This method loads all variables into the views
     * Parameters: DatabaseReference - db
     * Returns: void
     *
     */
    public void loadProfile(){
        nameView.setText(employeeName);
        descriptionBox.setText(description);
        usernameView.setText(username);
        //passView.setText(password);
        phoneView.setText(phone);
        emailView.setText(email);
        radiusView.setText(radius);
        clientIDView.setText(clientID);

    }
    /**
     * Function: This method loads all the variables from the employee database entry
     * Parameters: DatabaseReference - db
     * Returns: void
     *
     */
    public void getEmployeeDetails(DatabaseReference db){
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Read data from data base.
                //assume we are on employer
                Employee employee = dataSnapshot.getValue(Employee.class);
                //need to check against correct value to retrieve the correct location
                if(employee!=null){
                    user = new UserLocation();
                    user = dataSnapshot.child(employeeName).child("Location").getValue(UserLocation.class);
                    if (user != null) {
                        radius = user.getRadius();
                    }
                    username = employee.getUserName();
                    //password = employee.getPassword();
                    phone = employee.getPhone();
                    email = employee.getEmail();
                    employeeName = employee.getName();
                    description = employee.getDescription();
                    clientID = employee.getClientID();
                    wallet = employee.getWallet();
                    loadProfile();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }
}
