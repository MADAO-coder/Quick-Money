package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrationForEmployers extends AppCompatActivity implements View.OnClickListener{

    EditText name,username,password,vpassword,phone,email, business;
    Button homeBt,addPayment,submitBt, employeeBt;
    private Employer employees;
    DatabaseReference employerRef = null;
    DatabaseReference passWordRef = null;

    Employer employers = new Employer();

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
        homeBt = findViewById(R.id.Employer);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit);
        employeeBt = findViewById(R.id.emp);
        business = findViewById(R.id.business);

        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);
    }



    /*
    Switch to login page
     */
    protected void switchToHome(){
        Intent back = new Intent(this, loginPage.class);
        startActivity(back);
    }
    /*
    Changing pages to see employer registration
     */
    protected void switchToEmployee(){
        Intent employee = new Intent(this, registrationForEmployees.class);
        startActivity(employee);
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

    protected boolean isVerifyPasswordEmpty(){
        return vpassword.getText().toString().trim().equals("");
    }

    protected boolean isPhoneEmpty(){ return getPhoneNumber().equals(""); }

    protected boolean isValidEmail(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }
    /*
    Checking registration information
     */
    protected boolean validRegistrationInformation() {
       return !isUserNameEmpty() && !isPasswordEmpty() && !isNameEmpty() && !isPhoneEmpty()
               && isValidEmail(getInputEmailAddress());
    }
    /*
    Saving employee information to the database
     */
    protected void saveEmployerToDataBase(Object Employers) {
        //save object user to database to Firebase
        employerRef = FirebaseDatabase.getInstance().getReference();
        employerRef.child("Employer").child(String.valueOf(employers.getUserName())).setValue(Employers);
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

    protected String getInputVpassword(){
        return vpassword.getText().toString().trim();
    }

    protected boolean isPasswordMatched(){
        return (getInputPassword().equals(getInputVpassword()));
    }

    protected String getBuissnessName() {
        return business.getText().toString().trim();
    }

    public String getName() {
        return name.getText().toString().trim();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.emp):
                switchToEmployee();
                break;
            case (R.id.Employer):
                switchToHome();
                break;
            case (R.id.Submit):
                if(!validRegistrationInformation()) {
                    Toast toast = Toast.makeText(this,"Empty or invalid registration information",Toast.LENGTH_LONG);
                    toast.show();
                }
                else if(!isPasswordMatched()){
                    Toast toast = Toast.makeText(this,"password is not matched",Toast.LENGTH_LONG);
                    toast.show();
                }

                else {
                    employers.setUserName(getInputUserName());
                    employers.setPassword(getInputPassword());
                    employers.setEmailAddress(getInputEmailAddress());
                    employers.setPhone(getPhoneNumber());
                    employers.setName(getName());
                    employers.setBuisnessName(getBuissnessName());
                    saveEmployerToDataBase(employers);
                    switchToHome();//Once all registration info correct, switch to loginPage
                    break;
                }

        }
    }
}