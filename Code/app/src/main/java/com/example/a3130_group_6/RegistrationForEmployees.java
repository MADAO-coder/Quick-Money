package com.example.a3130_group_6;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.PatternsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class RegistrationForEmployees extends AppCompatActivity implements View.OnClickListener {
    EditText name, username, password, vpassword, phone, email;
    TextInputLayout selfDef;
    private Employee employee;
    Button homeBt, addPayment, submitBt, employeeBt, imageBtn, uploadResume, selectResume;
    //creating buttons and display variables
    TextView registrationStatus;
    DatabaseReference employerRef = null;
    DatabaseReference employeeRef = null;
    Employee employees = new Employee();
    ImageView imageToUpload;
    Button bUploadImage;
    EditText uploadImage;
    private static final int RESULT_LOAD_IMAGE = 1;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progress;
    private Uri image_uri;
    private Uri pdf;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        ImageButton imageButton = findViewById(R.id.imageButton);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        ImageView downloadedImage = (ImageView) findViewById(R.id.downloadedImage);

        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        Button bDownloadedImage = (Button) findViewById(R.id.bDownloadedImage);

        uploadImage = (EditText) findViewById(R.id.etloadImageName);
        EditText downloadImageName = (EditText) findViewById(R.id.etDownloadName);

        uploadResume = findViewById(R.id.uploadResume);
        selectResume = findViewById(R.id.selectResume);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        vpassword = findViewById(R.id.vpassword);
        phone = findViewById(R.id.phone);        //assigning the variables to its associated variable on the view
        email = findViewById(R.id.email);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit);
        employeeBt = findViewById(R.id.Employer);
        homeBt = findViewById(R.id.home2);
        imageBtn = findViewById(R.id.Image);
        selfDef = findViewById(R.id.SelfDescription);

        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);
        imageBtn.setOnClickListener(this);
        uploadResume.setOnClickListener(this);
        selectResume.setOnClickListener(this);
        imageButton.setOnClickListener(this);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        // get data from database
        employeeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");
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

    protected boolean isSelfDescriptionEmpty() {
        return selfDef.toString().trim().equals("");
    }

    protected String getInputVpassword() {
        return vpassword.getText().toString().trim();
    }

    protected boolean isPasswordMatched() {
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
        employeeRef = FirebaseDatabase.getInstance().getReference();
        employeeRef.child("Employee").child(employees.getUserName()).setValue(Employee);
    }
    protected String getInputUserName() { return username.getText().toString().trim(); }

    protected String getInputPassword() { return password.getText().toString().trim(); }

    protected String getInputEmailAddress() {
        return email.getText().toString().trim();
    }

    protected String getPhoneNumber() {
        return phone.getText().toString().trim();
    }

    public String getName() { return name.getText().toString().trim(); }

    protected String getSelfDescription() { return selfDef.toString(); }

    /*
    Changing pages to see employer registration
     */
    protected void switchToEmployer() {
        Intent employer = new Intent(this, RegistrationForEmployers.class);
        startActivity(employer);
    }
    protected void switchtoImage() {
        Intent Image = new Intent(this, ImageCapture.class);
        startActivity(Image);
    }
    /*
    Switch to login page
     */
    protected void switchToHome() {
        Intent back = new Intent(this, LoginPage.class);
        startActivity(back);
    }
    //check whether user inputted information or not
    public void onClick(View v) {
        if(R.id.Submit == v.getId()){ //when the submit button is clicked, add employee
                if (!validRegistrationInformation()) {
                    Toast toast = Toast.makeText(this, "Empty or invalid registration information", Toast.LENGTH_LONG);
                    toast.show();
                }
                //verify that the passwords match
                if (!isPasswordMatched()) {
                    Toast toast = Toast.makeText(this, "password is not matched", Toast.LENGTH_LONG);
                    toast.show();
                } else {

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
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 0);
        switch (v.getId()) {
            case R.id.home2:
                switchToHome();
                break;

            case R.id.Employer:
                switchToEmployer();
                break;

            case R.id.Image:
                switchtoImage();
                break;

            case R.id.imageButton:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
        }
        switch (v.getId()) {
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                if (RESULT_LOAD_IMAGE == RESULT_OK) {
                    imageToUpload.setImageURI(image_uri);
                };
                break;

            case R.id.bUploadImage:

                break;

            case R.id.bDownloadedImage:

                break;
        }
        //select resume from files
        selectResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(RegistrationForEmployees.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPDF();
                } else {
                    ActivityCompat.requestPermissions(RegistrationForEmployees.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }

            }
        });
        //upload resume to database
        uploadResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make sure information is filled out before user can upload resume
                if (pdf != null && name != null && username != null && email != null && phone != null) {
                    uploadFile(pdf);
                } else {
                    Toast.makeText(RegistrationForEmployees.this, "Please fill out the information before uploading Resume.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    static final int REQUEST_IMAGE_CAPTURE = 101;
    public static final int GET_FROM_GALLERY = 1;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }
    //upload pdf file
    private void uploadFile(Uri pdf) {
        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setTitle("Uploading file...");
        progress.setProgress(0);
        progress.show();

        String fileName = System.currentTimeMillis() + "";
        StorageReference storageReference = storage.getReference();

        storageReference.child("Uploads").child(fileName).putFile(pdf).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata().getReference() != null) {
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            DatabaseReference reference = database.getReference();

                            reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationForEmployees.this, "File successfully uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(RegistrationForEmployees.this, "File failed to upload", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currentProgress = (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progress.setProgress(currentProgress);
            }
        });
    }
    //once permission is granted, give user access to camera
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
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        }
        else {
            Toast.makeText(RegistrationForEmployees.this, "Please grant permission", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            image_uri = data.getData();
            try {
                imageToUpload.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(RegistrationForEmployees.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }







}
