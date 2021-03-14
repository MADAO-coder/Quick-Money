package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditEmployerListing extends AppCompatActivity {
    Button save,Back,home,switchPage,addTask,logout;
    String [] listing =null;
    DataSnapshot empListing;

    DatabaseReference listingRef = null;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        listing = extras.getStringArray("details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employer_listing);
        home=findViewById(R.id.Home);
        switchPage = findViewById(R.id.ListingHistory);
        addTask= findViewById(R.id.addTaskButton);
        logout = findViewById(R.id.Logout);
        home.setOnClickListener(this::onClick);
        switchPage.setOnClickListener(this::onClick);
        logout.setOnClickListener(this::onClick);
        addTask.setOnClickListener(this::onClick);
        save=findViewById(R.id.submitTask);
        save.setOnClickListener(this::onClick);
        Back =findViewById(R.id.Back);
        Back.setOnClickListener(this::onClick);
        database = FirebaseDatabase.getInstance();
        listingRef = database.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");

        setTextBox();
    }

    //}

    protected boolean isEmptyTaskTitle(String task) {
        return task.isEmpty();
    }

    protected boolean isEmptyTaskDescription(String description) {
        return description.isEmpty();
    }

    protected boolean isEmptyUrgency(String urgency) {
        return urgency.isEmpty();
    }

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

    protected void setTitleDisplay(String title){
        EditText editText = findViewById(R.id.EditTask);
        editText.setText(title);
    }
    protected void setDescriptionDisplay(String title){
        EditText editText = findViewById(R.id.EditTaskDescription);
        editText.setText(title);
    }
    protected void setUrgencyDisplay(String title){
        EditText editText = findViewById(R.id.editUrgency);
        editText.setText(title);
    }
    protected void setDateDisplay(String title){
        EditText editText = findViewById(R.id.editDate);
        editText.setText(title);
    }
    protected void setPayDisplay(String title){
        EditText editText = findViewById(R.id.EditPay);
        editText.setText(title);
    }
    protected void setStatusDisplay(String title){
        EditText editText = findViewById(R.id.EditStatus);
        editText.setText(title);
    }
    protected void setTextBox() {
        setTitleDisplay(listing[0]);
        setDescriptionDisplay(listing[1]);
        setUrgencyDisplay(listing[2]);
        setDateDisplay(listing[3]);
        setPayDisplay(listing[4]);
        setStatusDisplay(listing[5]);
    }


    public void switchListingHistory(View view) {
        Intent switchIntent = new Intent(this, ListingHistory.class);
        startActivity(switchIntent);
    }

    public void addTaskSwitch(View view) {
        Intent switchIntent = new Intent(this, AddListing.class);
        startActivity(switchIntent);
    }
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployerHomepage.class);
        startActivity(switchIntent);
    }



    public void onClick(View v) {
        EditText EditTask=findViewById(R.id.EditTask);
        EditText EditTaskDescription=findViewById(R.id.EditTaskDescription);
        EditText EditUrgency = findViewById(R.id.editUrgency);
        EditText EditPay = findViewById(R.id.EditPay);
        EditText EditDate = findViewById(R.id.editDate);
        EditText EditStatus = findViewById(R.id.EditStatus);
        switch ((v.getId())) {
            case R.id.submitTask:
                checkUrgencyRange(EditUrgency.toString().trim());
                if (isEmptyDate(EditDate.toString()) || isEmptyTaskTitle(EditTask.toString()) || isEmptyTaskDescription(EditTaskDescription.toString().trim()) || isEmptyUrgency(EditUrgency.toString().trim()) || isEmptyPay(EditPay.toString().trim())) {
                    Toast toast = Toast.makeText(this, "Error: Please ensure all fields are filled.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Listing post = new Listing(EditTask.getText().toString(), EditTaskDescription.getText().toString(),EditUrgency.getText().toString(), EditDate.getText().toString(),EditPay.getText().toString(),EditStatus.getText().toString(),listing[6]);
                    Map<String, Object> postValues = post.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(listing[7]+"/Listing/"+ listing[6], postValues);
                    listingRef.updateChildren(childUpdates);

                }
                break;
            case R.id.Back:
            case R.id.ListingHistory:
                startActivity(new Intent(this, ListingHistory.class));
                break;
            case R.id.addTaskButton:
                startActivity(new Intent(this, AddListing.class));
                break;
            case R.id.Logout:
                //database.
                break;
            case R.id.Home:
                startActivity(new Intent(this, EmployerHomepage.class));
                break;
        }

    }
}