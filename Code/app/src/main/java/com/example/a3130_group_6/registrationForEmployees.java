package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrationForEmployees extends AppCompatActivity implements View.OnClickListener {
    EditText name,username,password,vpassword,phone,email;
    Button homeBt,addPayment,submitBt, employeeBt;
    TextView registrationStatus;
    DatabaseReference employeeRef = null;
    DatabaseReference passWordRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        vpassword = findViewById(R.id.vpassword);
        phone= findViewById(R.id.phone);
        email = findViewById(R.id.email);
        homeBt = findViewById(R.id.Employee);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit);

        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);


    }

    protected void switchToEmployer(){
        Intent employer = new Intent(this, registrationForEmployers.class);
        startActivity(employer);
    }

    protected void switchToHome(){
        Intent back = new Intent(this, loginPage.class);
        startActivity(back);
    }
    protected boolean isUserNameEmpty(){
        return getInputUserName().equals("");
    }

    protected boolean isNameEmpty(){
        return getName().equals("");
    }

    protected boolean isPasswordEmpty(){
        return getInputPassword().equals("");
    }

    protected boolean isPhoneEmpty(){ return getPhoneNumber().equals(""); }

    protected boolean isValidEmail(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected boolean validRegistrationInformation() {
        return !isUserNameEmpty() && !isPasswordEmpty() && !isNameEmpty() && !isPhoneEmpty()
                && isValidEmail(getInputEmailAddress());
    }
    protected void saveEmployeeToDataBase(Object Employees) {
        //save object user to database to Firebase
        employeeRef = FirebaseDatabase.getInstance().getReference();
        employeeRef.child(String.valueOf(System.currentTimeMillis())).setValue(Employees);
    }

    protected String getInputUserName(){
        return username.getText().toString().trim();
    }

    protected String getName() { return name.getText().toString().trim(); }

    protected String getInputPassword(){
        return password.getText().toString().trim();
    }

    protected String getInputEmailAddress() {
        return email.getText().toString().trim();
    }

    protected String getPhoneNumber() {
        return phone.getText().toString().trim();
    }


    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.emp):
                switchToEmployer();
                break;
            case (R.id.Employee):
                switchToHome();
                break;
            case (R.id.Submit):
                if(validRegistrationInformation()) {
                    Employee employees = new Employee();
                    employees.setUserName(getInputUserName());
                    employees.setPassword(getInputPassword());
                    employees.setEmailAddress(getInputEmailAddress());
                    employees.setPhone(getPhoneNumber());
                    employees.setName(getName());
                    saveEmployeeToDataBase(employees);
                    switchToHome();//Once all registration info correct, switch to loginPage
                    break;
                }
                else {
                    Toast toast = Toast.makeText(this,"Empty or invalid registration information",Toast.LENGTH_LONG);
                    toast.show();
                }

        }
    }
}
