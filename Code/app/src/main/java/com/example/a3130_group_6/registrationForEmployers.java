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

    EditText name,username,password,vpassword,phone,email, business;
    Button homeBt,addPayment,submitBt, employeeBt;
    private Employer employees;
    static registrationForEmployers emp = new registrationForEmployers();
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
        username.setOnClickListener(this);
        // call the database
        // validate username
        validateUsername();
    }

    private void validateUsername() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // fetch usernames on create instead of doing multiple times.
                // set a constraint on username - for example at least it has to be three characters - once it has that then validate it
/*                DatabaseReference employer = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
                DatabaseReference employee = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
                checkUserNameInDatabase(employer, employee, String.valueOf(s));*/
                System.out.println(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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

    public ArrayList getUserNameList(DatabaseReference db, ArrayList list){
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
    public void checkUserNameInDatabase(DatabaseReference db1, DatabaseReference db2, String userName){
        ArrayList employeeList = new ArrayList<>();
        ArrayList employerList = new ArrayList<>();
        TextView error = (TextView) findViewById(R.id.error);

        employeeList = getUserNameList(db1, employeeList);
        employerList = getUserNameList(db2, employerList);

        if(employeeList.contains(userName) || employerList.contains(userName)){
            error.setText("Username already taken. Please enter a different username");
        }
        else error.setText("Username valid");
    }

    protected String getBusinessName() {
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
                    employers.setBuisnessName(getBusinessName());
                    saveEmployerToDataBase(employers);
                    switchToHome();//Once all registration info correct, switch to loginPage
                    break;
                }
        }
    }
}
