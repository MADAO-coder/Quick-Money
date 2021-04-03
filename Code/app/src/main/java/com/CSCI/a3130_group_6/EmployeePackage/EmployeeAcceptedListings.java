package com.CSCI.a3130_group_6.EmployeePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.CSCI.a3130_group_6.EmployerPackage.Employer;
import com.CSCI.a3130_group_6.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.CSCI.a3130_group_6.Registration.LoginPage.validEmployee;

public class EmployeeAcceptedListings extends AppCompatActivity {

    DatabaseReference employerRef = null;
    private List<Employer> employer_list = new ArrayList<>();
    Set<Employer> employerSet = new LinkedHashSet<Employer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_accepted_listings);

        employerRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer");
        dbReadEmployer(employerRef);
    }

    //Read data from dataBase and make employers' userName and password into ArrayList.
    public void dbReadEmployer(DatabaseReference db){

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // iterable for employers
                Iterator<DataSnapshot> employerItr = dataSnapshot.getChildren().iterator();

                // running loop over employers
                while (employerItr.hasNext()) {
                    DataSnapshot employerSnap = employerItr.next();
                    Employer employer = employerSnap.getValue(Employer.class);

                    // Iterable for listings under each employer
                    Iterator<DataSnapshot> listsItr = employerSnap.child("Listing").getChildren().iterator();

                    // running loop over listings
                    while (listsItr.hasNext()) {

                        // Iterable for applicants who are accepted under each listing
                        Iterator<DataSnapshot> acceptedEmp = listsItr.next().child("Applicants").
                                child("Accepted").getChildren().iterator();

                        // running loop over all the accepted employees
                        while (acceptedEmp.hasNext()) {

                            // get key of the accepted employer
                            String userName = acceptedEmp.next().getKey();

                            // compare key with the current employee who is logged in
                            if (userName.equals(validEmployee[0])){
                                employerSet.add(employer);
                                break;
                            }
                        }
                    }
                }
                System.out.println(employerSet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}