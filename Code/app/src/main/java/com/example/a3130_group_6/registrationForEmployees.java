package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class registrationForEmployees extends AppCompatActivity {
    EditText name,username,password,vpassword,phone,email;
    Button homeBt,addPayment,submitBt, employeeBt;
    TextView registrationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employee);
        Button home =  findViewById(R.id.home2);

    }


    protected String getInputUserName(){
        return username.getText().toString().trim();
    }

    protected String getInputPassword(){
        return password.getText().toString().trim();
    }

    protected String getInputEmailAddress() {
        return email.getText().toString().trim();
    }

    protected String getPhoneNumber() {
        return phone.getText().toString().trim();
    }

    public String getName() { return name.getText().toString().trim(); }

    protected void switchToEmployer(){
        Intent employer = new Intent(this, registrationForEmployers.class);
        startActivity(employer);
    }

    protected void switchToHome(){
        Intent back = new Intent(this, loginPage.class);
        startActivity(back);
    }

    public void onClick(View v) {
        String statusMsg = "";
        if(R.id.Submit == v.getId())
            registrationStatus = (TextView) findViewById(R.id.registrationStatus);

        switch (v.getId()){
            case (R.id.Employer):
                switchToEmployer();
                break;
            case (R.id.home2):
                switchToHome();
                break;
        }
    }
}
