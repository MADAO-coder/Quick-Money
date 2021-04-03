package com.CSCI.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class EmployeeRatingEmployer extends AppCompatActivity {
    String empUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_rating_employer);

        Intent intent = getIntent();
        empUserName = intent.getStringExtra("userName");
        createToast("Retrieved username " + empUserName);
    }

    /**
     * Function: Method to create a Toast
     * Parameters:
     * Returns: boolean
     *
     */
    private void createToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}