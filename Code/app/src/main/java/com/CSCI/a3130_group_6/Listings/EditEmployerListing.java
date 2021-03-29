package com.CSCI.a3130_group_6.Listings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.CSCI.a3130_group_6.HelperClases.EmployerChatList;
import com.CSCI.a3130_group_6.EmployerPackage.EmployerHomepage;
import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.Registration.LoginPage;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditEmployerListing extends AppCompatActivity {
    Button save;
    String [] listing =null;
    DataSnapshot empListing;

    DatabaseReference listingRef = null;
    FirebaseDatabase database;
    TabLayout tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        listing = extras.getStringArray("details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employer_listing);

        save=findViewById(R.id.submitTask);
        save.setOnClickListener(this::onClick);

        database = FirebaseDatabase.getInstance();
        listingRef = database.getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        tab =findViewById(R.id.tabs);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getText().toString()) {
                    case "Listing":
                        switchListingHistory();
                        break;
                    case "Profile":
                        profileSwitch();
                        break;
                    case "Logout":
                        LogoutSwitch();
                        break;
                    case "Home":
                        homepageSwitch();
                        break;
                    case "Chat":
                        chatSwitch();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}


        });
        setTextBox();
    }

    //}

    public boolean isEmptyTaskTitle(String task) {
        return task.isEmpty();
    }

    public boolean isEmptyTaskDescription(String description) {
        return description.isEmpty();
    }

    public boolean isEmptyUrgency(String urgency) {
        return urgency.isEmpty();
    }

    public boolean checkUrgencyRange(String urgency) {
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

    public boolean isEmptyDate(String date) {
        return date.isEmpty();
    }

    public boolean isEmptyPay(String pay) {
        return pay.isEmpty();
    }

    public void setTitleDisplay(String title){
        EditText editText = findViewById(R.id.titleInput);
        editText.setText(title);
    }
    public void setDescriptionDisplay(String title){
        EditText editText = findViewById(R.id.descriptionInput);
        editText.setText(title);
    }
    public void setUrgencyDisplay(String title){
        EditText editText = findViewById(R.id.urgencyInput);
        editText.setText(title);
    }
    public void setDateDisplay(String title){
        EditText editText = findViewById(R.id.dateInput);
        editText.setText(title);
    }
    public void setPayDisplay(String title){
        EditText editText = findViewById(R.id.payInput);
        editText.setText(title);
    }
    public void setStatusDisplay(String title){
        EditText editText = findViewById(R.id.statusInput);
        editText.setText(title);
    }
    public void setTextBox() {
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
        EditText EditTask=findViewById(R.id.titleInput);
        EditText EditTaskDescription=findViewById(R.id.descriptionInput);
        EditText EditUrgency = findViewById(R.id.urgencyInput);
        EditText EditPay = findViewById(R.id.payInput);
        EditText EditDate = findViewById(R.id.dateInput);
        EditText EditStatus = findViewById(R.id.statusInput);
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



        }

    }
    public void profileSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), com.CSCI.a3130_group_6.EmployerPackage.EmployerProfile.class);
        startActivity(switchIntent);
    }


    public void homepageSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), EmployerHomepage.class);
        startActivity(switchIntent);
    }

    public void switchListingHistory() {
        Intent switchIntent = new Intent(getApplicationContext(), ListingHistory.class);
        startActivity(switchIntent);
    }
    public void LogoutSwitch() {
        LoginPage.validEmployer= null;
        Intent switchIntent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(switchIntent);
    }
    public void chatSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), EmployerChatList.class);
        startActivity(switchIntent);
    }


}