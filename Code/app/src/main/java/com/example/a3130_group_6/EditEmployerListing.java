package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditEmployerListing extends AppCompatActivity {
    Button save;
   // EditText EditTask,EditTaskDescription,EditUrgency,EditPay,EditDate,EditStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employer_listing);
        save.setOnClickListener(this::onClick);

    }
    //protected String getTaskTitle(){

    //}

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
    public void onClick(View v) {
        EditText EditTask=findViewById(R.id.EditTask);
        EditText EditTaskDescription=findViewById(R.id.EditTaskDescription);
        EditText EditUrgency = findViewById(R.id.editUrgency);
        EditText EditPay = findViewById(R.id.EditPay);
        EditText EditDate = findViewById(R.id.editDate);
        EditText EditStatus = findViewById(R.id.EditStatus);
        switch ((v.getId())){
            case R.id.submitTask:
                checkUrgencyRange(EditUrgency.toString().trim());
                if (isEmptyDate(EditDate.toString())|| isEmptyTaskTitle(EditTask.toString())||isEmptyTaskDescription(EditTaskDescription.toString().trim())||isEmptyUrgency(EditUrgency.toString().trim())||isEmptyPay(EditPay.toString().trim())){
                    setStatusMessage("Error: Please ensure all fields are filled.");
                }
                else{

                }
        }
    }

}