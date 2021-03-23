package com.example.a3130_group_6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ShowApplication extends AppCompatActivity implements View.OnClickListener {
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_application);
        homeButton = findViewById(R.id.employerHome);
        homeButton.setOnClickListener(this);
    }

    protected void switchToEmployerHome(){
        Intent home = new Intent(this, EmployerHomepage.class);
        startActivity(home);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.employerHome){
            switchToEmployerHome();
        }
    }

}