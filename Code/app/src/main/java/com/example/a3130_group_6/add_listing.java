package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_listing extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        Button submitButton = findViewById(R.id.submitTask);
        submitButton.setOnClickListener(this);

        initializeDatabase();
    }

    private void initializeDatabase() {
    }

    protected boolean isEmptyTaskTitle(String task) {
        return task.isEmpty();
    }

    protected boolean isEmptyTaskDescription(String description) {
        return description.isEmpty();
    }

    protected boolean isEmptyUrgency(String urgency) {
        return urgency.isEmpty();
    }

    protected boolean checkUrgencyRange(String urgency) {
        try {
            int num = Integer.parseInt(urgency);
            for (int i = 1; i < 6; i++){
                if (num == i) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Urgency not in range");
        }
        return false;
    }

    protected boolean isEmptyDate(String date) {
        return date.isEmpty();
    }

    protected boolean isEmptyPay(String pay) {
        return pay.isEmpty();
    }

    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message);
    }

    @Override
    public void onClick(View view) {
        EditText taskTitle = findViewById(R.id.inputTaskTitle);
        EditText taskDescription = findViewById(R.id.inputTaskDescription);
        EditText urgency = findViewById(R.id.inputUrgency);
        EditText date = findViewById(R.id.enterDate);
        EditText pay = findViewById(R.id.inputPay);

        if (isEmptyTaskTitle(taskTitle.getText().toString())) {
            setStatusMessage("Error: Empty Task Title");
        }
        else if (isEmptyTaskDescription(taskDescription.getText().toString().trim())) {
            setStatusMessage("Error: Empty Task Description");
        }
        else if (isEmptyUrgency(urgency.getText().toString().trim())) {
            setStatusMessage("Error: Please fill in Urgency");
        }
        else if (isEmptyDate(date.getText().toString().trim())) {
            setStatusMessage("Error: Please fill in Date");
        }
        else if (isEmptyPay(pay.getText().toString().trim())) {
            setStatusMessage("Error: Please fill in Pay");
        }

    }
}