package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeHomepage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        setEmployeeList();


        Button employeeProfileButton = findViewById(R.id.employeeProfileButton); // CREATED JUST TO VIEWING PURPOSES, CAN DELETE AFTER INTEGRATION OF NAV BAR
        employeeProfileButton.setOnClickListener((View.OnClickListener) this); // CREATED JUST TO VIEWING PURPOSES, CAN DELETE AFTER INTEGRATION OF NAV BAR
    }

    protected void setEmployeeList(){
        String[] employees = new String[] { "Noback Endintegration", "Potter Weasley", "Henry Harry", "Jim Jones", "Granger Jones Jr.", "asdasdasdasd", "asdasdasdas", "asdasdasdasdasdas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, employees);
        ListView employeeList = (ListView) findViewById(R.id.TaskList);
        employeeList.setAdapter(adapter);
    }
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, EmployeeHomepage.class);
        startActivity(switchIntent);
    }




    /**
     * Created button just for testing/viewing purposes. Can/will delete after integration of navigation bar
     */
    public void employeeProfileSwitch() {
        Intent switchIntent = new Intent(this, EmployeeProfile.class);
        startActivity(switchIntent);
        setContentView(R.layout.activity_edit_employee_profile);
    }

    /**
     * Created button just for testing/viewing purposes. Can/will delete after integration of navigation bar
     */
    public void onClick(View v) {
        employeeProfileSwitch();
    }

}
