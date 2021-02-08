package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class add_listing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
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

    protected boolean isPayEmpty(String pay) {
        return pay.isEmpty();
    }
}