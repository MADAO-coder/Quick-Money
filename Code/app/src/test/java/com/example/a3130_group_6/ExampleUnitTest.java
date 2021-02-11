package com.example.a3130_group_6;

import android.widget.SearchView;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * Acceptance Tests
 * AT 1: Given that I want to use the program, I expect to see a search bar and button where I can search for available tasks or available employees to do the task
 * AT 2: Given that I click the search button, as an employer I expect to see a list of employees who can do the task and as an employee I expect to see available tasks
 * AT 3: Given that I am on the homepage, I expect to see the app’s banner/logo on the top
 * AT 4: Given that I am on the homepage, I expect to see a home button which refreshes the page
 * AT 5: Given that I am an employee/employer I expect to see available tasks/available taskers in my local
 */
public class ExampleUnitTest {
    static EmployerHomepage employerHomepage;
    @BeforeClass
    public static void setup() {
        employerHomepage = new EmployerHomepage();
    }

    // search bar test check
    @Test
    public void searchBar(){
        assertFalse((employerHomepage.searchFunctioning("")));
    }
    // search bar typed test check
    @Test
    public void searchBarType(){
        assertTrue(employerHomepage.searchFunctioning("Jim"));
    }
    //employee details elements
    @Test
    public void employeeDetails(){
        assertTrue(employerHomepage.checkEmployeeList());
    }
    // add task button test
    @Test
    public void addTask(){

    }
    // available workers heading
    @Test
    public void headerWorkers(){

    }

}