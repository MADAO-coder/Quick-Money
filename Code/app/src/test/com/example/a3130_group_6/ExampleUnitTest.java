package com.example.a3130_group_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.a3130_group_6.EmployeeHomepage;

import org.hamcrest.text.IsEmptyString;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<EmployeeHomepage>employerRule = new ActivityScenarioRule<>(EmployeeHomepage.class);

    /*** AT-I**/
    @Test
    public void checkIfSearchBarIsShown() {
        //ActivityScenario<EmployerHomepage> scenario = employerRule.getScenario();
        onView(withId(R.id.searchBar)).check(matches(isDisplayed()));
    }
    @Test
    public void checkIfSearchTextIsCaught(){
        //ActivityScenario<EmployerHomepage> scenario = employerRule.getScenario();
        onView(withId(R.id.searchBar)).perform(click());
        onView(withId(R.id.searchBar)).perform(typeText("jim"));
        //check correct element being interacted with. retrieving query unobtainable at this time
        onView(withId(R.id.searchBar)).check(matches(withId(2131231018)));
    }
    /** AT-3*/
    @Test
    public void checkHeader(){
        onView(withId(R.id.employeeHeader)).check(matches(isDisplayed()));
    }
    /** AT-5**/
    @Test
    public void checkEmployeeList(){
        //in debug its clear to see items are updating on page as well :)
        onView(withId(R.id.employeeList)).check(matches(isDisplayed()));
    }
    @Test
    public void checkEmployeeListScroll(){
        onView(withId(R.id.employeeList)).perform(swipeUp());
        onView(withId(R.id.employeeList)).perform(swipeDown());
    }
}
