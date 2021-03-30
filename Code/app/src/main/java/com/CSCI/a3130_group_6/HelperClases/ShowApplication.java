package com.CSCI.a3130_group_6.HelperClases;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.CSCI.a3130_group_6.EmployeePackage.Employee;
import com.CSCI.a3130_group_6.EmployerPackage.EmployerHomepage;
import com.CSCI.a3130_group_6.Listings.ListingHistory;
import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.Registration.LoginPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowApplication extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference employeeRef, applicantRef;
    String description, username, phone, email, name, radius, employeeName, listingKey, message;
    TextView descriptionBox, applicantName, employeeUsername, applicantEmail,
            applicantPhoneNum, applicantRadius, applicantMessage;
    Button homeButton, accept, reject, seeResume;
    String resumeLink;
    UserLocation user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_application);
        homeButton = findViewById(R.id.employerHome);

        accept = findViewById(R.id.accept);
        accept.setOnClickListener(this);
        reject = findViewById(R.id.reject);
        reject.setOnClickListener(this);

        seeResume = findViewById(R.id.seeResume);
        seeResume.setOnClickListener(this);

        applicantMessage = findViewById(R.id.applicantMessage);
        applicantName = findViewById(R.id.applicantName);
        employeeUsername = findViewById(R.id.applicantUserName);
        applicantEmail = findViewById(R.id.applicantEmail);
        applicantPhoneNum = findViewById(R.id.applicantPhoneNum);
        applicantRadius = findViewById(R.id.applicantRadius);
        descriptionBox = findViewById(R.id.applicantDescription);

        homeButton.setOnClickListener(this);

        Intent intent = getIntent();
        employeeName = intent.getStringExtra("name");
        listingKey = ListingHistory.listingKey;

        applicantRef =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer/"
                + LoginPage.validEmployer[0] + "/Listing/" + listingKey + "/Applicants/Applied/"+employeeName);
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/"+employeeName);

        getEmployeeDetails(employeeRef);
    }

    public void switchToEmployerHome(){
        Intent home = new Intent(this, EmployerHomepage.class);
        startActivity(home);
    }

    public void showResume(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(resumeLink));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.employerHome){
            switchToEmployerHome();
        }
        else if(v.getId() == R.id.seeResume) {
            showResume();
        }
        else if(v.getId() == R.id.accept){
        }

        else if(v.getId() == R.id.reject){

        }
    }

    public void getMessage(DatabaseReference db){
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                message = dataSnapshot.child("Message").getValue(String.class);
                applicantMessage.setText(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
                    radius = dataSnapshot.child("Location").child("radius").getValue(String.class);
                    applicantRadius.setText(radius);
                    username = employee.getUserName();
                    resumeLink = employee.getResumeUrl();
                    phone = employee.getPhone();
                    email = employee.getEmail();
                    name = employee.getName();
                    description = employee.getDescription();
                    loadProfile();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * Function: This method loads all variables into the views
     * Parameters: DatabaseReference - db
     * Returns: void
     *
     */
    public void loadProfile(){
        applicantName.setText(name);
        descriptionBox.setText(description);
        employeeUsername.setText(username);
        applicantPhoneNum.setText(phone);
        applicantEmail.setText(email);
        getMessage(applicantRef);
    }

}
