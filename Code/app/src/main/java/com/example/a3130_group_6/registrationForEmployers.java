package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class registrationForEmployers extends AppCompatActivity implements View.OnClickListener{
    ArrayList employeeList = new ArrayList<>();
    ArrayList employerList = new ArrayList<>();
    EditText name,username,password,vpassword,phone,email, business;
    Button homeBt,addPayment,submitBt, employeeBt;
    TextView error;
    static registrationForEmployers emp = new registrationForEmployers();
    DatabaseReference employerRef = null;
    DatabaseReference employer = null;
    DatabaseReference employee = null;

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
        username.setOnClickListener(this);
        error = findViewById(R.id.error);

        // call the database
        // validate username
        initializeDatabase();
        saveUserNamesInLists();
        validateUsername();
    }

    // method to initialize the reference for
    // employer and employee
    private void initializeDatabase() {
        employer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        employee = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
    }

    // method to save the usenames of employer
    // and employee in lists
    private void saveUserNamesInLists() {
        employeeList = getUserNameList(employer, employeeList);
        employerList = getUserNameList(employee, employerList);
    }

    // method to retrieve the list of usernames
    // from the database
    private ArrayList getUserNameList(DatabaseReference db, ArrayList list){
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.

                while (employerItr.hasNext()) {
                    Employer employer = employerItr.next().getValue(Employer.class);
                    String employerUserName = employer.getUserName();
                    list.add(employerUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return list;
    }

    private boolean checkUserNameLength() {
        if (getInputUserName().length() < 3){
            TextView error = (TextView) findViewById(R.id.error);
            error.setText("Username too short");
            return false;
        }
        return true;
    }

    // method to check if the username exists in any of the lists
    // from the database and print an error in realtime
    private void validateUsername() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // set a constraint on username - for example at least it has to be three characters - once it has that then validate it
                if (checkUserNameLength()) {
                    checkUserNameInDatabase(employerList, employeeList, String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // method to check if the username exists in the database
    // and set the error text in the statusLabel
    private void checkUserNameInDatabase(ArrayList employeeList, ArrayList employerList, String userName){
        TextView error = (TextView) findViewById(R.id.error);
        if(employeeList.contains(userName) || employerList.contains(userName)){
            error.setText("Username already taken. Please enter a different username.");
        }
        else error.setText("Username valid");
    }

    // method to check if the user changed the username or not
    private boolean checkUserNameError() {
        String text = String.valueOf(error.getText());
        String error1 = "Username already taken. Please enter a different username.";
        String error2 = "Username too short";
        System.out.println(text.equals(error1));
        System.out.println(text.equals(error2));
        if (text.equals(error1) || text.equals(error2)) {
            return true;
        }
        return false;
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

    protected String getBusinessName() {
        return business.getText().toString().trim();
    }

    protected String getName() {
        return name.getText().toString().trim();
    }

    // method to create a Toast
    private void createToast(String message){
        Toast toast = Toast.makeText(this, message,Toast.LENGTH_LONG);
        toast.show();
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
                    createToast("Empty or invalid registration information");
                }
                else if(!isPasswordMatched()){
                    createToast("password is not matched");
                }
                else if(checkUserNameError()){
                    createToast("Please change the username");
                }
                else {
                    employers.setUserName(getInputUserName());
                    employers.setPassword(getInputPassword());
                    employers.setEmailAddress(getInputEmailAddress());
                    employers.setPhone(getPhoneNumber());
                    employers.setName(getName());
                    employers.setBuisnessName(getBusinessName());
                    saveEmployerToDataBase(employers);
                    switchToHome();//Once all registration info correct, switch to loginPage
                    break;
                }
        }
    }
}
