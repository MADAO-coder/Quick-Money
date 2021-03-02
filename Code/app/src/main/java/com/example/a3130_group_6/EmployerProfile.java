package com.example.a3130_group_6;

import android.os.Bundle;
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
    String biography;
    EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameView = new EditText(this, findViewById(R.id.editName));
        setContentView(R.layout.activity_employer_profile);
        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef);//Get data from database
    }

    //code from loginPage
    //Read data from dataBase and make employers' userName and password into ArrayList.
    public void dbReadEmployer(DatabaseReference db){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();
                //Read data from data base.
                while (employerItr.hasNext()) {
                    Employer employer = employerItr.next().getValue(Employer.class);
                        //need to check against correct value to retrieve the correct location
                        if (employer.getUserName().equals(validEmployer[0])){
                            String username = employer.getUserName();
                            String password = employer.getPassword();
                            String phone = employer.getPassword();
                            String email = employer.getPassword();
                            String name = employer.getPassword();
                            nameView.setText(name);
                            String business = employer.getPassword();
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
