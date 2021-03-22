package com.example.a3130_group_6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;


public class AddListing extends AppCompatActivity implements View.OnClickListener {
    Listing list;
    AddListingMap map;
    EditText taskTitle, taskDescription, urgency, date, pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        map = new AddListingMap();

        if (savedInstanceState != null) {
            taskTitle.setText(savedInstanceState.getString("taskTitle"));
            taskTitle.setText(savedInstanceState.getString("taskDescription"));
            taskTitle.setText(savedInstanceState.getString("urgency"));
            taskTitle.setText(savedInstanceState.getString("date"));
            taskTitle.setText(savedInstanceState.getString("pay"));
        }

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);

        Button submitButton = findViewById(R.id.submitTask);
        submitButton.setOnClickListener(this);

        Button addLocationBt = findViewById(R.id.add_locationBt);
        addLocationBt.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("taskTitle", taskTitle.getText().toString());
        outState.putString("taskDescription", taskDescription.getText().toString());
        outState.putString("urgency", urgency.getText().toString());
        outState.putString("date", date.getText().toString());
        outState.putString("pay", pay.getText().toString());
    }

    protected boolean isEmptyTaskTitle(String task) {
        return task.isEmpty();
    }

    protected boolean isEmptyTaskDescription(String description) {
        return description.isEmpty();
    }

    protected boolean isEmptyUrgency(String urgency) {
        return urgency.isEmpty();
    }

    /**
     * Function: Method to check if urgency ranges from 1 to 5
     * Parameters: String
     * Returns: boolean
     *
     */
    protected boolean checkUrgencyRange(String urgency) {
        try {
            int num = Integer.parseInt(urgency);
            for (int i = 1; i < 6; i++){
                if (num == i) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Urgency not in range");
        }
        return false;
    }

    protected boolean isEmptyDate(String date) {
        return date.isEmpty();
    }

    protected boolean isEmptyPay(String pay) {
        return pay.isEmpty();
    }

    protected boolean isEmptyLocation(String location){
        return location.isEmpty();
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message);
    }

    public void employeeMapSwitch() {
        Intent EmployeeMapIntent = new Intent(this, AddListingMap.class);
        startActivity(EmployeeMapIntent);
    }

    protected boolean checkIfLocationEmpty(UserLocation current){
        return current == null;
    }

    @Override
    public void onClick(View view) {

        EditText status = findViewById(R.id.EditStatus);
        taskTitle = findViewById(R.id.EditTask);
        taskDescription = findViewById(R.id.EditTaskDescription);
        urgency = findViewById(R.id.editUrgency);
        date = findViewById(R.id.editDate);
        pay = findViewById(R.id.EditPay);
        TextView currentLocation = findViewById(R.id.currentLocationView);
        Button addLocationBt = findViewById(R.id.add_locationBt);

        switch (view.getId()) {
            case R.id.submitTask:
                if (isEmptyTaskTitle(taskTitle.getText().toString())) {
                    setStatusMessage("Error: Empty Task Title");
                }
                else if (isEmptyTaskDescription(taskDescription.getText().toString().trim())) {
                    setStatusMessage("Error: Empty Task Description");
                }
                else if (isEmptyUrgency(urgency.getText().toString().trim())) {
                    setStatusMessage("Error: Please fill in Urgency");
                }
                else if (isEmptyDate(date.getText().toString().trim())) {
                    setStatusMessage("Error: Please fill in Date");
                }
                else if (isEmptyPay(pay.getText().toString().trim())) {
                    setStatusMessage("Error: Please fill in Pay");
                }
                else if (checkIfLocationEmpty(AddListingMap.presentLocation)){
                    setStatusMessage("Error: Please choose a location");
                } else {
                    // creating reference to the Employer object in the database
                    DatabaseReference listing = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");

                    // creating a list object with relevant entries which have to be added to the database
                    list = new Listing(taskTitle.getText().toString(), taskDescription.getText().toString(), urgency.getText().toString(),
                            date.getText().toString(), pay.getText().toString(), status.getText().toString(),AddListingMap.presentLocation);

                    // pushing entries to the database
                    listing.child(String.valueOf(LoginPage.validEmployer[0])).child("Listing").push().setValue(list);
                }
                break;
            case R.id.add_locationBt:
                employeeMapSwitch();
                break;
            case R.id.imageButton:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
        }
    }


    public static final int GET_FROM_GALLERY = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}