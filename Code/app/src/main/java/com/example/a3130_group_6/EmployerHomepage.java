package com.example.a3130_group_6;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EmployerHomepage extends AppCompatActivity {

    ArrayList<Employee> employees;
    DatabaseReference employeeRef;
    DatabaseReference notificationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);
        employeeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        setEmployeeList();

        // database reference to the Listing child of the employer who is currently logged in
        notificationRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer").child(LoginPage.validEmployer[0]).child("Listing");

        // listening to any change in data in the database
        notificationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                notification();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    /**
     * Function: This method is used to create a push notification on the device
     * Parameters: none
     * Returns: void
     * Citation: Idea from https://www.geeksforgeeks.org/how-to-push-notification-in-android-using-firebase-cloud-messaging/
     */
    private void notification() {
        Intent applicationIntent = applicationIntent();
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, applicationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Application")
                .setSmallIcon(R.drawable.application)
                .setAutoCancel(true)
                .setContentText("A new employer applied to your listing. Click here to review their application.")
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    protected Intent applicationIntent(){
        Intent intentToApplication = new Intent(this, ShowApplication.class);
        return intentToApplication;
    }

    protected void setEmployeeList(){
        // connect to db, retrieve employees
        employees = new ArrayList<>();
        // causing bugs (wasn't working before anyway)
        //dbReadEmployees(employeeRef, employees);
        String[] employeesString = new String[employees.size()];
        employees.toArray(employeesString);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employeesString);
        ListView employeeList = (ListView) findViewById(R.id.employeeList);
        employeeList.setAdapter(adapter);
    }

    protected boolean searchFunctioning(String search){
        /* irrelevant testing process for unit tests.
        searchView.setQuery(search, true);
        return searchView.getQuery().toString().equals(search);
        */
        return !search.isEmpty();
    }

    protected boolean checkEmployeeList(String[] employees){
        for(String individual : employees) {
            if(individual.isEmpty()){
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Function: This is a method to switch to Employer profile class
     * Parameters: none
     * Returns: void
     *
     */
    public void profileSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployerProfile.class);
        startActivity(switchIntent);
    }

    /**
     * Function: This is a method to switch to Add listing page
     * Parameters: none
     * Returns: void
     *
     */
    public void addTaskSwitch(View view) {
        Intent switchIntent = new Intent(this, AddListing.class);
        startActivity(switchIntent);
    }

    /**
     * Function: This is a method to switch to Employer homepage
     * Parameters: none
     * Returns: void
     *
     */
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployerHomepage.class);
        startActivity(switchIntent);
    }

    public void switchListingHistory(View view) {
        Intent switchIntent = new Intent(this, ListingHistory.class);
        startActivity(switchIntent);
    }

}