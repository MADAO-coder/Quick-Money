package com.CSCI.a3130_group_6.HelperClases;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.CSCI.a3130_group_6.EmployeePackage.Employee;
import com.CSCI.a3130_group_6.EmployerPackage.EmployerHomepage;
import com.CSCI.a3130_group_6.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowApplication extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference employeeRef;
    String description, username, phone, email, name, radius, employeeName;
    TextView navBar, descriptionBox, applicantName, employeeUsername, applicantEmail,
            applicantPhoneNum, applicantPassword, applicantRadius, applicantMessage;
    Button homeButton, accept, reject, selectResume;
    UserLocation user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_application);
        homeButton = findViewById(R.id.employerHome);
        homeButton.setOnClickListener(this);

        Intent intent = getIntent();
        employeeName = intent.getStringExtra("name");

        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/"+employeeName);

        getEmployeeDetails(employeeRef);
    }

    public void switchToEmployerHome(){
        Intent home = new Intent(this, EmployerHomepage.class);
        startActivity(home);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.employerHome){
            switchToEmployerHome();
        }
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
                    // applicantMessage = employ
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
        //passView.setText(password);
        applicantPhoneNum.setText(phone);
        applicantEmail.setText(email);
        applicantRadius.setText(radius);
    }
}
