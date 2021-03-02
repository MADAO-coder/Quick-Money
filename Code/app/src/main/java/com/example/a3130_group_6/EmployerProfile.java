package com.example.a3130_group_6;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.a3130_group_6.loginPage.validEmployer;

public class EmployerProfile extends AppCompatActivity {
    DatabaseReference employerRef = null;
    private List<String> employer_userName_list = new ArrayList<>();//List to store password getting from db for Employee object
    private List<String> employer_password_list = new ArrayList<>();//List to store password getting from db for Employee object
    String biography, username, password, phone, email, name;

    String business;
    EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef);//Get data from database
        nameView = new EditText(this, findViewById(R.id.editName));
        nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                loadProfile();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);
    }

    public void loadProfile(){
        EditText tran = new EditText(this.nameView.getContext());
        tran.setText(name);
        nameView = tran;
        //nameView.setText(name);
        //
    }

    //code from loginPage
    //Read data from dataBase and retrieve employer information
    public void dbReadEmployer(DatabaseReference db){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employerItr.hasNext()) {
                    //assume there will always be at least one employer
                    Employer employer = employerItr.next().getValue(Employer.class);
                        //need to check against correct value to retrieve the correct location
                        if (employer.getUserName().equals(validEmployer[0])){
                            String username = employer.getUserName();
                            String password = employer.getPassword();
                            String phone = employer.getPhone();
                            String email = employer.getEmailAddress();
                            String name = employer.getName();
                            String business = employer.getBuisnessName();
                            loadProfile();
                            break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
}
