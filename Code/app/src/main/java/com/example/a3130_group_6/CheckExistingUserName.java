package com.example.a3130_group_6;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckExistingUserName {
    ArrayList employeeList = new ArrayList<>();
    ArrayList employerList = new ArrayList<>();
    DatabaseReference employer = null;
    DatabaseReference employee = null;

    /**
     * Function: Constructor for the class
     * Parameters:
     * Returns:
     */
    public CheckExistingUserName() {
        initializeDatabase();
        saveUserNamesInLists();
    }

    /**
     * Function: method to initialize the reference for employer and employee
     * Parameters:
     * Returns: void
     *
     */
    protected void initializeDatabase() {
        employer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        employee = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
    }

    /**
     * Function: method to save the usernames of employer and employee in lists
     * Parameters:
     * Returns: void
     *
     */
    protected void saveUserNamesInLists() {
        employeeList = getUserNameList(employer, employeeList);
        employerList = getUserNameList(employee, employerList);
    }

    /**
     * Function: method to retrieve the list of usernames from the database
     * Parameters: DatabaseReference, ArrayList
     * Returns: Arraylist of usernames
     *
     */
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

    /**
     * Function: Method to check the length of the username and then show an error if
     *           the length of the username is less than 3 characters
     * Parameters: TextView, String
     * Returns: boolean
     *
     */
    private boolean checkUserNameLength(TextView error, String inputUserName) {
        if (inputUserName.length() < 3){
            error.setText("Username too short");
            return false;
        }
        return true;
    }

    /**
     * Function: method to check if the username exists in any of the lists
     *           from the database and print an error in realtime
     * Parameters: EditText, TextView
     * Returns:
     *
     */
    protected void validateUsername(EditText username, TextView error) {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkUserNameLength((TextView) error, String.valueOf(s))) {
                    checkUserNameInDatabase(employerList, employeeList, String.valueOf(s), error);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Function: method to check if the username exists in the database
     *           and set the error text in the statusLabel
     * Parameters: ArrayList, ArrayList, String, TextView
     * Returns:
     *
     */
    private void checkUserNameInDatabase(ArrayList employeeList, ArrayList employerList, String userName, TextView error){
        if(employeeList.contains(userName) || employerList.contains(userName)){
            error.setText("Username already taken. Please enter a different username.");
        }
        else error.setText("Username valid");
    }

    /**
     * Function: method to check if the user changed the username or not
     * Parameters: TextView
     * Returns: boolean
     *
     */
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
