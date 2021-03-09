package com.example.a3130_group_6;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class checkExistingUserName {
    ArrayList employeeList = new ArrayList<>();
    ArrayList employerList = new ArrayList<>();
    DatabaseReference employer = null;
    DatabaseReference employee = null;


    public checkExistingUserName() {
        initializeDatabase();
        saveUserNamesInLists();
    }

    // method to initialize the reference for
    // employer and employee
    protected void initializeDatabase() {
        employer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        employee = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
    }

    // method to save the usenames of employer
    // and employee in lists
    protected void saveUserNamesInLists() {
        employeeList = getUserNameList(employer, employeeList);
        employerList = getUserNameList(employee, employerList);
    }

    // method to retrieve the list of usernames
    // from the database
    private ArrayList getUserNameList(DatabaseReference db, ArrayList list){
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.

                while (employerItr.hasNext()) {
                    Employer employer = employerItr.next().getValue(Employer.class);
                    String employerUserName = employer.getUserName();
                    list.add(employerUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return list;
    }

    // method to check the length of the username and then show an error if
    // the length of the username is less than 3 characters
    private boolean checkUserNameLength(TextView error, String inputUserName) {
        if (inputUserName.length() < 3){
            error.setText("Username too short");
            return false;
        }
        return true;
    }

    // method to check if the username exists in any of the lists
    // from the database and print an error in realtime
    protected void validateUsername(EditText username, TextView error) {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // set a constraint on username - for example at least it has to be three characters - once it has that then validate it
                if (checkUserNameLength((TextView) error, String.valueOf(s))) {
                    checkUserNameInDatabase(employerList, employeeList, String.valueOf(s), error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // method to check if the username exists in the database
    // and set the error text in the statusLabel
    private void checkUserNameInDatabase(ArrayList employeeList, ArrayList employerList, String userName, TextView error){
        if(employeeList.contains(userName) || employerList.contains(userName)){
            error.setText("Username already taken. Please enter a different username.");
        }
        else error.setText("Username valid");
    }

    // method to check if the user changed the username or not
    protected boolean checkUserNameError(TextView error) {
        String text = String.valueOf(error.getText());
        String error1 = "Username already taken. Please enter a different username.";
        String error2 = "Username too short";

        if (text.equals(error1) || text.equals(error2)) {
            return true;
        }
        return false;
    }

}
