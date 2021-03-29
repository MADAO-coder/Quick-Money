package com.CSCI.a3130_group_6.HelperClases;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.CSCI.a3130_group_6.EmployeePackage.EmployeeHomepage;
import com.CSCI.a3130_group_6.EmployerChatList;
import com.CSCI.a3130_group_6.Listings.ListingHistory;
import com.CSCI.a3130_group_6.Registration.LoginPage;

public class SwitchEmployeeMethods extends AppCompatActivity {
    Context context;
    public void profileSwitch() {
        Intent switchIntent = new Intent(context, com.CSCI.a3130_group_6.EmployeePackage.EmployeeProfile.class);
        startActivity(switchIntent);
    }
    public void homepageSwitch() {
        Intent switchIntent = new Intent(context, EmployeeHomepage.class);
        startActivity(switchIntent);
    }

    public void switchListingHistory() {
        Intent switchIntent = new Intent(context, ListingHistory.class);
        startActivity(switchIntent);
    }
    public void LogoutSwitch() {

        LoginPage.validEmployee = null;
        Intent switchIntent = new Intent(context, LoginPage.class);
        startActivity(switchIntent);
    }
    public void chatSwitch() {
        Intent switchIntent = new Intent(context, EmployerChatList.class);
        startActivity(switchIntent);
    }
}
