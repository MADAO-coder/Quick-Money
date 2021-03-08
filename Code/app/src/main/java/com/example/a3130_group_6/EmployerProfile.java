package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.a3130_group_6.loginPage.validEmployer;

public class EmployerProfile extends AppCompatActivity {
    DatabaseReference employerRef = null;
    String biography, username, password, phone, email, name, business;
    EditText nameView, biographyView, usernameView, passwordView, phoneView, emailView, businessView;
    TextView statusView;
    Button submitButton, refreshButton;
    // use upload profile button to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        // get data from database
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef);
        // set all views
        setViews();

        // set button to update to database on click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update fields
                // define new employer object and set fields
                Employer employer = new Employer();
                employer.setName(nameView.getText().toString());
                //employers.setBiography(biography);
                employer.setUserName(usernameView.getText().toString());
                employer.setPassword(passwordView.getText().toString());
                employer.setPhone(phoneView.getText().toString());
                employer.setEmailAddress(emailView.getText().toString());
                employer.setBuisnessName(businessView.getText().toString());
                // updates to db, but deletes associated listings
                updateToDatabase(employer);
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
        // asynchronous task function enables front end thread to wait for backend thread
        // need to setup so UI thread waits for backend thread
        // google resources + ask vikash
    }

    public void switchListingHistory(View view) {
        Intent switchIntent = new Intent(this, ListingHistory.class);
        startActivity(switchIntent);
    }

    public void setViews(){
        statusView = findViewById(R.id.statusView);
        nameView = findViewById(R.id.editName);
        biographyView = findViewById(R.id.editBiography);
        usernameView = findViewById(R.id.editUsername);
        passwordView = findViewById(R.id.editPassword);
        phoneView = findViewById(R.id.editPhone);
        emailView = findViewById(R.id.editEmail);
        businessView = findViewById(R.id.editBusiness);
        submitButton = (Button) findViewById(R.id.submitBtn);
        refreshButton = (Button) findViewById(R.id.refreshBtn);
    }

    public void updateToDatabase(Employer employer){
        // save object user to database to Firebase
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer/" + username);

        /* Map<String, Object> updates = new HashMap<>();
         * updates.put(username, employer.getUserName());
         * updates.put("password", employer.getPassword());
         * updates.put("emailAddress", employer.getEmailAddress());
         * updates.put("name", employer.getName());
         * updates.put("businessName", employer.getBuisnessName());
         * updates.put("phone", employer.getPhone());
         * employerRef.updateChildren(updates);
         */
        // below sets entirely new employer object - overwriting any listings
        employerRef.setValue(employer);
        statusView.setText("Profile updated to database!");
    }
    public void refreshPage(){
        // save object user to database to Firebase
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef);
        statusView.setText("Profile changes refreshed");
    }

    public void loadProfile(){
        nameView.setText(name);
        biographyView.setText(biography);
        usernameView.setText(username);
        passwordView.setText(password);
        phoneView.setText(phone);
        emailView.setText(email);
        businessView.setText(business);
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
                        if (employer.getUserName().equals(validEmployer[0])){
                            username = employer.getUserName();
                            password = employer.getPassword();
                            phone = employer.getPhone();
                            email = employer.getEmailAddress();
                            name = employer.getName();
                            business = employer.getBuisnessName();
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
