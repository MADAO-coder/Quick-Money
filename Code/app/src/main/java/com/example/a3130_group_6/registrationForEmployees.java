package com.example.a3130_group_6;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.core.content.FileProvider;
import androidx.core.util.PatternsCompat;
import java.security.Permission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;


public class registrationForEmployees extends AppCompatActivity implements View.OnClickListener {
    EditText name, username, password, vpassword, phone, email;
    TextInputEditText selfDef;
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
        imageButton.setOnClickListener(this);
        selfDef = findViewById(R.id.SelfDescription);


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
        phone = findViewById(R.id.phone);        //assigning the variables to its associated variable on th view
        email = findViewById(R.id.email);
        addPayment = findViewById(R.id.AddPayment);
        submitBt = findViewById(R.id.Submit);
        employeeBt = findViewById(R.id.Employer);
        homeBt = findViewById(R.id.home2);
        imageBtn = findViewById(R.id.Image);

        employeeBt.setOnClickListener(this);
        homeBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);
        imageBtn.setOnClickListener(this);
        imageToUpload.setOnClickListener(this);
        uploadResume.setOnClickListener(this);
        selectResume.setOnClickListener(this);


        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        // get data from database
        employeeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee");

        //bUploadImage.setOnClickListener(this);

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
        return selfDef.getText().toString().trim().equals("");
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

    public String getSelfDescription() {
        return selfDef.getText().toString().trim();
    }

    /*
    Changing pages to see employer registration
     */
    protected void switchToEmployer() {
        Intent employer = new Intent(this, registrationForEmployers.class);
        startActivity(employer);
    }

    protected void switchtoImage() {
        Intent Image = new Intent(this, imageCapture.class);
        startActivity(Image);
    }

    /*
    Switch to login page
     */
    protected void switchToHome() {
        Intent back = new Intent(this, loginPage.class);
        startActivity(back);
    }

    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
        if (R.id.Submit == v.getId()) {//when the submit button is clicked, add employee
            if (!validRegistrationInformation()) {
                Toast toast = Toast.makeText(this, "Empty or invalid registration information", Toast.LENGTH_LONG);
                toast.show();
            } else if (!isPasswordMatched()) {
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
        } else if (R.id.home2 == v.getId()) {
            switchToHome();
        } else if (R.id.Employer == v.getId()) {
            switchToEmployer();
        } else if (R.id.Image == v.getId()) {
            switchtoImage();

        }
        if (R.id.imageButton == v.getId()) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

        }

        switch (v.getId()) {
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.bUploadImage:

                break;

            case R.id.bDownloadedImage:

                break;
        }

        if (ContextCompat.checkSelfPermission(registrationForEmployees.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            selectPDF();
        } else {
            ActivityCompat.requestPermissions(registrationForEmployees.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
        }

        if (pdf != null) {
            uploadFile(pdf);
        } else {
            Toast.makeText(registrationForEmployees.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 101;
    public static final int GET_FROM_GALLERY = 1;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;


//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
//            imageToUpload.setImageURI(selectedImage);
//
//        }
//    }
//    String currentPhotoPath;
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

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
                                        Toast.makeText(registrationForEmployees.this, "File successfully uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(registrationForEmployees.this, "File failed to upload", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(registrationForEmployees.this, "Please grant permission", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageView.setImageURI(image_uri);
        }
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdf = data.getData();
        }
        else {
            Toast.makeText(registrationForEmployees.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }






}
