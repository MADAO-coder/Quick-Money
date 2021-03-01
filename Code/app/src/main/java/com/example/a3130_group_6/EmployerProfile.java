package com.example.a3130_group_6;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployerProfile extends AppCompatActivity {
    DatabaseReference employerRef = null;
    private List<String> employer_userName_list = new ArrayList<>();//List to store password getting from db for Employee object
    private List<String> employer_password_list = new ArrayList<>();//List to store password getting from db for Employee object
    String biography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);
    }

    public void onCLick(View v){
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef, biography);//Get data from database
    }

    //code from loginPage
    //Read data from dataBase and make employers' userName and password into ArrayList.
    public void dbReadEmployer(DatabaseReference db, String statusMessage){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.

                while (employerItr.hasNext()) {
                    Employer employer = employerItr.next().getValue(Employer.class);
                    String employerUserName = employer.getUserName();
                    String employerPassword = employer.getPassword();
                    //if there is a tracked session, perhaps retrieve from there
                    employer_userName_list.add(employerUserName);
                    employer_password_list.add(employerPassword);
                }
                for(int i=0; i<employer_password_list.size(); i++){
                    //need to check against correct value to retrieve the correct location
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }
}
