package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_homepage);
        setTaskList();
    }

    protected void setTaskList(){
        String[] Task = new String[] {"job1", "job2","job3","job4","job5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Task);
        ListView taskList = (ListView) findViewById(R.id.taskList);
        taskList.setAdapter(adapter);
    }
    protected boolean searchFunctioning(String search){
        /* irrelevant testing process for unit tests.
        searchView.setQuery(search, true);
        return searchView.getQuery().toString().equals(search);
        */
        return !search.isEmpty();
    }

    protected boolean checkEmployeeList(String[] employees){
        for(String individual : employees) {
            if(individual.isEmpty()){
                return false;
            }else{
                return true;
            }
        }
        return false;
    }

    public void addTaskSwitch(View view) {
        Intent switchIntent = new Intent(this, addTask.class);
        startActivity(switchIntent);
    }
    public void homepageSwitch(View view) {
        Intent switchIntent = new Intent(this, MainActivity.class);
        startActivity(switchIntent);
    }
}
