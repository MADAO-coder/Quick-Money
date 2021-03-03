package com.example.a3130_group_6;

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
import java.util.Iterator;
import java.util.List;

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

        // set button to update to database on click
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                updateToDatabase();
            }
        });
        // set button to refresh profile fields on click
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                refreshPage();
            }
        });

        //attach listeners to all views for when they change
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

    public void updateToDatabase(){
        // define new employer object and set fields
        Employer employers = new Employer();
        employers.setBuisnessName(name);
        //employers.setBiography(biography);
        employers.setUserName(username);
        employers.setPassword(password);
        employers.setPhone(phone);
        employers.setEmailAddress(email);
        employers.setBuisnessName(business);
        // save object user to database to Firebase
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer/" + name);
        employerRef.setValue(employers);
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
