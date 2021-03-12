package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeView extends AppCompatActivity {
    DatabaseReference employeeRef;
    String employeeName;
    String description, username, password, phone, email, name, radius;
    EditText descriptionBox, nameView, emailView, phoneView, passView, radiusView;
    TextView usernameView, statusView;
    ImageView imageView;
    Button submitButton, refreshButton, imageButton, uploadResume, selectResume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        // create based off employee profile? hide buttons + change editable
        Intent intent = getIntent();
        employeeName = intent.getStringExtra("name");
        nameView = findViewById(R.id.employeeNameInput);

        imageView = findViewById(R.id.profilePicture);
        imageButton = findViewById(R.id.profileImageButton);

        uploadResume = findViewById(R.id.uploadResume);
        selectResume = findViewById(R.id.selectResume);
        // get data from database
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        getEmployeeDetails(employeeRef);
        // set all views
        setViews();

        // disable all buttons
        disableButtons();

        // set fields to uneditable
        disableFields();

    }
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
    public void disableButtons(){
        submitButton.setEnabled(false);
        refreshButton.setEnabled(false);
        imageButton.setEnabled(false);
        uploadResume.setEnabled(false);
        selectResume.setEnabled(false);
    }
    public void setViews(){
        statusView = findViewById(R.id.employeeStatusLabel);
        nameView = findViewById(R.id.employeeNameInput);
        descriptionBox = findViewById(R.id.descriptionBox);
        usernameView = findViewById(R.id.employeeUsernameInput);
        passView = findViewById(R.id.employeePassInput);
        phoneView = findViewById(R.id.employeePhoneNumInput);
        emailView = findViewById(R.id.employeeEmailInput);
        radiusView = findViewById(R.id.radiusInput);

        submitButton = (Button) findViewById(R.id.saveProfileUpdate);
        refreshButton = (Button) findViewById(R.id.refreshButton);
    }
    public void getEmployeeDetails(DatabaseReference db){

    }
}
