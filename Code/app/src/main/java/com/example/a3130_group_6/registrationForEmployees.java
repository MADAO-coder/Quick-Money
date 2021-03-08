package com.example.a3130_group_6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class registrationForEmployees extends AppCompatActivity implements View.OnClickListener {
    EditText name,username,password,vpassword,phone,email;
    TextInputEditText selfDef;
    private Employee employee;
    Button homeBt,addPayment,submitBt, employeeBt, resumeBt;//creating buttons and display variables
    TextView registrationStatus;
    DatabaseReference employerRef = null;
    DatabaseReference employeeRef = null;
    Employee employees = new Employee();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        selfDef = findViewById(R.id.SelfDescription);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        vpassword = findViewById(R.id.vpassword);
        phone= findViewById(R.id.phone);        //assigning the variables to its associated variable on th view
        email = findViewById(R.id.email);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit);
        employeeBt = findViewById(R.id.Employer);
        homeBt =  findViewById(R.id.home2);
        resumeBt = findViewById(R.id.Resume);

        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);
        resumeBt.setOnClickListener(this);

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

    protected boolean isVerifyPasswordEmpty(){ return vpassword.getText().toString().trim().equals(""); }

    protected boolean isPhoneEmpty(){ return getPhoneNumber().equals(""); }

    protected boolean isValidEmail(String email){
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }
    protected boolean isSelfDescriptionEmpty(){
        return selfDef.getText().toString().trim().equals("");
    }

    protected String getInputVpassword(){
        return vpassword.getText().toString().trim();
    }

    protected boolean isPasswordMatched(){
        return (getInputPassword().equals(getInputVpassword()));
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
    protected void saveEmployeeToDataBase(Object Employee) {
        //save object user to database to Firebase
        employeeRef  = FirebaseDatabase.getInstance().getReference();
        employeeRef.child("Employee").child(employees.getUserName()).setValue(Employee);
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

    public String getSelfDescription(){ return selfDef.getText().toString().trim(); }
    /*
    Changing pages to see employer registration
     */
    protected void switchToEmployer(){
        Intent employer = new Intent(this, registrationForEmployers.class);
        startActivity(employer);
    }
    /*
    Switch to login page
     */
    protected void switchToHome(){
        Intent back = new Intent(this, loginPage.class);
        startActivity(back);
    }

    public void onClick(View v) {
        if (R.id.Submit==v.getId()){//when the submit button is clicked, add employee
            if(!validRegistrationInformation()){
                Toast toast = Toast.makeText(this,"Empty or invalid registration information",Toast.LENGTH_LONG);
                toast.show();
            }
            else if(!isPasswordMatched()){
                Toast toast = Toast.makeText(this,"password is not matched",Toast.LENGTH_LONG);
                toast.show();
            }

            else {

                employees.setUserName(getInputUserName());
                employees.setPassword(getInputPassword());
                employees.setEmailAddress(getInputEmailAddress());
                employees.setPhone(getPhoneNumber());
                employees.setName(getName());
                employees.setSelfDescription(getSelfDescription());
                saveEmployeeToDataBase(employees);
                switchToHome();
            }
        }
        else if(R.id.home2 == v.getId()){
            switchToHome();
        }
        else if(R.id.Employer == v.getId()){
            switchToEmployer();
        }
        if (R.id.imageButton == v.getId()){
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

        }
    }
    public static final int GET_FROM_GALLERY = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
