package com.example.a3130_group_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.view.View;
import android.widget.Toast;

import java.security.Permission;

import static com.example.a3130_group_6.loginPage.validEmployee;

public class EmployeeProfile extends AppCompatActivity {

    DatabaseReference employeeRef = null;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    String description, username, password, phone, email, name, radius;
    EditText descriptionBox, nameView, emailView, phoneView, passView, radiusView;
    TextView usernameView, statusView;
    Button submitButton, refreshButton, imageButton;
    ImageView imageView;
    Uri image_uri;

    // use upload profile button to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        // get data from database
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        dbReadEmployee(employeeRef);
        // set all views
        setViews();

        imageView = findViewById(R.id.profilePicture);
        imageButton = findViewById(R.id.profileImageButton);

        // set button to update to database on click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update fields
                // define new employee object and set fields
                Employee employee = new Employee();
                setStatusMessage(true, "");
                if (isNameEmpty(nameView.getText().toString())) {
                    setStatusMessage(false, "Error: Please fill in name");
                }
                else if (isEmailEmpty(emailView.getText().toString().trim())) {
                    setStatusMessage(false, "Error: Please fill in email");
                }
                else if (isPhoneNumEmpty(phoneView.getText().toString().trim())) {
                    setStatusMessage(false, "Error: Please fill in phone number");
                }
                else if (isPasswordEmpty(passView.getText().toString().trim())) {
                    setStatusMessage(false,"Error: Please fill in password");
                }
                else {
                    employee.setName(nameView.getText().toString());
                    employee.setDescription(descriptionBox.getText().toString());
                    employee.setUserName(usernameView.getText().toString());
                    employee.setPassword(passView.getText().toString());
                    employee.setPhone(phoneView.getText().toString());
                    employee.setEmailAddress(emailView.getText().toString());
                    // updates to db
                    updateToDatabase(employee);
                }

            }
        });
        // set button to refresh profile fields on click
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                refreshPage();
                setStatusMessage(true, "");
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED ){
                        //request permission
                        String [] permission  = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        //permission granted
                        openCamera();


                    }
                }
                else{
                    openCamera();
                }
            }
        });

    }
    public void setViews(){
        statusView = findViewById(R.id.employeeStatusLabel);
        nameView = findViewById(R.id.employeeNameInput);
        descriptionBox = findViewById(R.id.descriptionBox);
        usernameView = findViewById(R.id.employeeUsernameInput);
        passView = findViewById(R.id.employeePassInput);
        phoneView = findViewById(R.id.employeePhoneNumInput);
        emailView = findViewById(R.id.employeeEmailInput);
        radiusView = findViewById(R.id.radiusInput);

        submitButton = (Button) findViewById(R.id.saveProfileUpdate);
        refreshButton = (Button) findViewById(R.id.refreshButton);
    }

    public void updateToDatabase(Employee employee){
        // save object user to database to Firebase
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/" + username);
        Map<String, Object> updates = new HashMap<>();
        updates.put("userName", employee.getUserName());
        updates.put("password", employee.getPassword());
        updates.put("email", employee.getEmail());
        updates.put("name", employee.getName());
        updates.put("phone", employee.getPhone());
        updates.put("description", employee.getDescription());
        employeeRef.updateChildren(updates);
        // below sets entirely new employee object
        employeeRef.setValue(employee);
        setStatusMessage(true, "Profile updated to database!");
    }
    public void refreshPage(){
        // save object user to database to Firebase
        employeeRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
        dbReadEmployee(employeeRef);
        //statusView.setText("Profile changes refreshed");
    }

    public void loadProfile(){
        nameView.setText(name);
        descriptionBox.setText(description);
        usernameView.setText(username);
        passView.setText(password);
        phoneView.setText(phone);
        emailView.setText(email);
        radiusView.setText(radius);
    }

    //code from loginPage
    //Read data from dataBase and retrieve employee information
    public void dbReadEmployee(DatabaseReference db){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employeeItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employeeItr.hasNext()) {
                    //assume there will always be at least one employer
                    Employee employee = employeeItr.next().getValue(Employee.class);
                    //need to check against correct value to retrieve the correct location
                    if (employee.getUserName().equals(validEmployee[0])){
                        radius = dataSnapshot.child("Radius").getValue(String.class);
                        username = employee.getUserName();
                        password = employee.getPassword();
                        phone = employee.getPhone();
                        email = employee.getEmail();
                        name = employee.getName();
                        description = employee.getDescription();
                        break;
                    }
                }
                loadProfile();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //granted to use camera
                    openCamera();
                }
                else{
                    //permission was denied
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void TakePicture(View view){
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageView.setImageURI(image_uri);
        }
    }



    protected static boolean isNameEmpty(String name) {
        return name.isEmpty();
    }

    protected static boolean isEmailEmpty (String email) {
        return email.isEmpty();
    }

    protected static boolean isPhoneNumEmpty (String phoneNum) {
        return phoneNum.isEmpty();
    }

    protected static boolean isPasswordEmpty (String password) {
        return password.isEmpty();
    }

    protected void setStatusMessage(Boolean success, String message) {
        TextView statusLabel = findViewById(R.id.employeeStatusLabel);
        if (success) {
            statusLabel.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            statusLabel.setTextColor(Color.RED);
        }
        statusLabel.setText(message);
    }


}
