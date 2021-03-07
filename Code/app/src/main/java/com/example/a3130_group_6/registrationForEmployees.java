package com.example.a3130_group_6;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.util.PatternsCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class registrationForEmployees extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    EditText name, username, password, vpassword, phone, email;
    Button homeBt, addPayment, submitBt, employeeBt, addLocationButton;//creating buttons and display variables
    TextView employeeUsernameError;
    TextView statusLabel;
    TextView currentLocationView;
    DatabaseReference employeeRef = null;
    Employee employees = new Employee();

    checkExistingUserName user;
    AddListingMap location;
    LatLng currentLocation;
    Context context;
    Activity activity;
    LocationManager manager;
    GetExactAddress exactAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        vpassword = findViewById(R.id.vpassword);
        phone = findViewById(R.id.phone);        //assigning the variables to its associated variable on th view
        email = findViewById(R.id.email);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit1);
        employeeBt = findViewById(R.id.Employer);
        homeBt = findViewById(R.id.home2);
        statusLabel = findViewById(R.id.statusLabel);
        addLocationButton = findViewById(R.id.addLocationButton);
        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);
        addLocationButton.setOnClickListener(this);
        employeeUsernameError = findViewById(R.id.employeeUserError);
        context = registrationForEmployees.this;
        activity = registrationForEmployees.this;
        currentLocationView = findViewById(R.id.currentLocationView);

        user = new checkExistingUserName();
        location = new AddListingMap();

        user.validateUsername(username, employeeUsernameError);
    }

    protected boolean isUserNameEmpty() {
        return getInputUserName().equals("");
    }

    protected boolean isNameEmpty() {
        return getName().equals("");
    }

    protected boolean isPasswordEmpty() {
        return getInputPassword().equals("");
    }

    protected boolean isVerifyPasswordEmpty() {
        return vpassword.getText().toString().trim().equals("");
    }

    protected boolean isPhoneEmpty() {
        return getPhoneNumber().equals("");
    }

    protected boolean isValidEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected String getInputVpassword() {
        return vpassword.getText().toString().trim();
    }

    protected boolean isPasswordMatched(String password, String vPassword) {
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
        employeeRef = FirebaseDatabase.getInstance().getReference();
        employeeRef.child("Employee").child(employees.getUserName()).setValue(Employee);
    }


    protected String getInputUserName() {
        return username.getText().toString().trim();
    }

    protected String getInputPassword() {
        return password.getText().toString().trim();
    }

    protected String getInputEmailAddress() {
        return email.getText().toString().trim();
    }

    protected String getPhoneNumber() {
        return phone.getText().toString().trim();
    }

    public String getName() {
        return name.getText().toString().trim();
    }

    /*
    Changing pages to see employer registration
     */
    protected void switchToEmployer() {
        Intent employer = new Intent(this, registrationForEmployers.class);
        startActivity(employer);
    }

    /*
    Switch to login page
     */
    protected void switchToHome() {
        Intent back = new Intent(this, loginPage.class);
        startActivity(back);
    }

    protected void switchToEmployeeMap() {
        Intent map = new Intent(this, AddListingMap.class);
        startActivity(map);
    }

    // method to create a Toast
    private void createToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    protected boolean isEmptyLocation(String location) {
        return location.isEmpty();
    }

    // Map Code Start
    protected void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            checkLocationPermission(activity, context, location.LOCATION_PERMISSION, location.LOCATION_PREF);
        } else {
        }
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void checkLocationPermission(final Activity activity, final Context context, final String Permission, final String prefName) {

        PermissionUtil.checkPermission(activity, context, Permission, prefName,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onPermissionAsk() {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Permission},
                                location.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining is permission denied previously , but app require it and then request permission

                        showToast("Permission previously Denied.");

                        ActivityCompat.requestPermissions(activity,
                                new String[]{Permission},
                                location.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionDisabled() {
                        // permission check box checked and permission denied previously .
                        askUserToAllowPermissionFromSetting();
                    }

                    @Override
                    public void onPermissionGranted() {
                        showToast("Permission Granted.");
                    }
                });
    }

    private void askUserToAllowPermissionFromSetting() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Permission Required:");

        // set dialog message
        alertDialogBuilder
                .setMessage("Kindly allow Permission from App Setting, without this permission app could not show maps.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, location.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        showToast("Permission forever Disabled.");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(@NonNull Location locate) {
            currentLocation = new LatLng(locate.getLatitude(), locate.getLongitude());
            String message =  "Current location " + currentLocation.latitude + "," + currentLocation.latitude;
            try {
                getAddressFromLocation(currentLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void getAddressFromLocation(LatLng currentLocation) throws IOException {
        exactAddress = new GetExactAddress(currentLocation, activity);
        exactAddress.createAddress();
        currentLocationView.setText(exactAddress.getAddress());
    }

    protected void getCurrentLocation() {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                5, listener);
    }

    // Map Code end


    public void onClick(View v) {

        if (R.id.Submit1 ==v.getId()){//when the submit button is clicked, add employee
            if(!validRegistrationInformation()){
                createToast("Empty or invalid registration information");
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
            // ask for permissions
            checkPermissions();

            // add method to get the current lat long
            getCurrentLocation();


            // show the address in the textview
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
