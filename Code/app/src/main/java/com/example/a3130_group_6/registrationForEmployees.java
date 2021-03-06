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
    Button homeBt,addPayment,submitBt, employeeBt, addLocationButton;//creating buttons and display variables
    TextView employeeUsernameError;
    TextView statusLabel;
    DatabaseReference employeeRef = null;
    Employee employees = new Employee();
    checkExistingUserName user = new checkExistingUserName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        vpassword = findViewById(R.id.vpassword);
        phone= findViewById(R.id.phone);        //assigning the variables to its associated variable on th view
        email = findViewById(R.id.email);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit1);
        employeeBt = findViewById(R.id.Employer);
        homeBt =  findViewById(R.id.home2);
        statusLabel = findViewById(R.id.statusLabel);
        addLocationButton = findViewById(R.id.addLocationButton);
        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);
        addLocationButton.setOnClickListener(this);
        employeeUsernameError = findViewById(R.id.employeeUserError);

        user.validateUsername(username, employeeUsernameError);

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

    protected String getInputVpassword(){
        return vpassword.getText().toString().trim();
    }

    protected boolean isPasswordMatched(String password, String vPassword){
        return (password.equals(vPassword));
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

    protected void switchToEmployeeMap(){
        Intent map = new Intent(this, EmployeeMapActivity.class);
        startActivity(map);
    }

    // method to create a Toast
    private void createToast(String message){
        Toast toast = Toast.makeText(this, message,Toast.LENGTH_LONG);
        toast.show();
    }

    protected boolean isEmptyLocation(String location){
        return true;
    }

    public void onClick(View v) {
        if (R.id.Submit1 ==v.getId()){//when the submit button is clicked, add employee
            if(!validRegistrationInformation()){
                createToast("Empty or invalid registration information");
            }
            else if(!isPasswordMatched(getInputPassword(), getInputVpassword())){
                createToast("password is not matched");
            }
            else if(user.checkUserNameError(employeeUsernameError)){
                createToast("Please change the username");
            }
            else if(!isPasswordMatched(getInputPassword(), getInputVpassword())){//when the password and verification password are not matched
                statusLabel.setText("password is not matched");
            }
            else {
                employees.setUserName(getInputUserName());
                employees.setPassword(getInputPassword());
                employees.setEmailAddress(getInputEmailAddress());
                employees.setPhone(getPhoneNumber());
                employees.setName(getName());
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
        else if(R.id.addLocationButton == v.getId()){
            switchToEmployeeMap();
        }
    }
}
