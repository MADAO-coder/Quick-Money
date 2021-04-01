package com.CSCI.a3130_group_6.EmployerPackage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.CSCI.a3130_group_6.EmployeePackage.Employee;
import com.CSCI.a3130_group_6.EmployeePackage.EmployeeHomepage;
import com.CSCI.a3130_group_6.HelperClases.EmployerChatList;
import com.CSCI.a3130_group_6.Listings.AddListing;
import com.CSCI.a3130_group_6.Listings.ListingHistory;
import com.CSCI.a3130_group_6.R;
import com.CSCI.a3130_group_6.Registration.LoginPage;

import com.CSCI.a3130_group_6.HelperClases.ShowApplication;
import com.google.android.material.tabs.TabLayout;

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

    Button addTask;
    TabLayout tab;

    String taskTitle;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_homepage);

        employeeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        setEmployeeList();
        addTask= findViewById(R.id.addTaskButton);
        addTask.setOnClickListener(this::addTaskSwitch);
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

        // database reference to the Listing child of the employer who is currently logged in
        notificationRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer").child(LoginPage.validEmployer[0]).child("Listing");

        // listening to any change in data in the database
        notificationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override


            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // getting the parent of the snapshot
                DatabaseReference parent = snapshot.getRef().getParent();

                System.out.println(previousChildName);

                // retrieving the task title to show in the notification
                taskTitle = (String) snapshot.child("taskTitle").getValue();

                // checking if the parent of the listing matches the current employer who is logged in
                if (parent.getParent().getKey().equals(LoginPage.validEmployer[0])) {
                    notification();
                }
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
                .setContentText("An employee applied to your listing: " + taskTitle)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(100+count, builder.build());
        count++;
    }

    public Intent applicationIntent(){
        Intent intentToApplication = new Intent(this, ListingHistory.class);
        return intentToApplication;
    }

    public void setEmployeeList(){
        // connect to db, retrieve employees
        employees = new ArrayList<>();


        String[] employeesString = new String[employees.size()];
        employees.toArray(employeesString);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employeesString);
        ListView employeeList = (ListView) findViewById(R.id.employeeList);
        employeeList.setAdapter(adapter);
    }

    public boolean searchFunctioning(String search){ return !search.isEmpty(); }

    public boolean checkEmployeeList(String[] employees){
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
     * Function: This is a method to switch to Add listing page
     * Parameters: none
     * Returns: void
     *
     */
    public void addTaskSwitch(View v) {
        Intent switchIntent = new Intent(this, AddListing.class);
        startActivity(switchIntent);
    }
    /**
     * Function: This is a method to switch to Employer profile class
     * Parameters: none
     * Returns: void
     *
    */

    public void profileSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), EmployerProfile.class);
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
        ListingHistory.employerRef=null;
        LoginPage.validEmployee = null;
        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
        Intent switchIntent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(switchIntent);
    }
    public void chatSwitch() {
        Intent switchIntent = new Intent(getApplicationContext(), EmployerChatList.class);
        startActivity(switchIntent);
    }
}