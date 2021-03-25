package com.CSCI.a3130_group_6.EmployeePackage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.HelperClases.UserLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeView extends AppCompatActivity {
    DatabaseReference employeeRef;
    String employeeName;
    String description, username, password, phone, email, name, radius;
    EditText descriptionBox, nameView, emailView, phoneView, passView, radiusView;
    TextView usernameView, statusView;
    ImageView imageView;
    Button submitButton, refreshButton, imageButton, uploadResume, selectResume;
    UserLocation user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        // create based off employee profile? hide buttons + change editable
        Intent intent = getIntent();
        employeeName = intent.getStringExtra("name");
        // set database
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/"+employeeName);
        // set all views
        setViews();
        // disable all buttons
        disableButtons();
        // set fields to uneditable
        disableFields();
        // get data
        getEmployeeDetails(employeeRef);

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
        nameView = findViewById(R.id.employeeNameInput);
        imageView = findViewById(R.id.profilePicture);
        statusView = findViewById(R.id.employeeStatusLabel);
        descriptionBox = findViewById(R.id.descriptionBox);
        usernameView = findViewById(R.id.employeeUsernameInput);
        passView = findViewById(R.id.employeePassInput);
        phoneView = findViewById(R.id.employeePhoneNumInput);
        emailView = findViewById(R.id.employeeEmailInput);
        radiusView = findViewById(R.id.radiusInput);

        submitButton = (Button) findViewById(R.id.saveProfileUpdate);
        refreshButton = (Button) findViewById(R.id.employerHome);
        imageButton = findViewById(R.id.profileImageButton);

        uploadResume = findViewById(R.id.uploadResume);
        selectResume = findViewById(R.id.selectResume);
    }
    /**
     * Function: This method loads all variables into the views
     * Parameters: DatabaseReference - db
     * Returns: void
     *
     */
    public void loadProfile(){
        nameView.setText(name);
        descriptionBox.setText(description);
        usernameView.setText(username);
        //passView.setText(password);
        phoneView.setText(phone);
        emailView.setText(email);
        radiusView.setText(radius);
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
}
