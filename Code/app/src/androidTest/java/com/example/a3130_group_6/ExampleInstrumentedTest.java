package com.example.a3130_group_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.text.IsEmptyString;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<EmployerHomepage>employerRule = new ActivityScenarioRule<>(EmployerHomepage.class);

    /*** AT-I**/
    @Test
    public void checkIfSearchBarIsShown() {
        ActivityScenario<EmployerHomepage> scenario = employerRule.getScenario();
        onView(withId(R.id.searchBar)).check(matches(withText(R.string.EMPTY_STRING)));
    }
    @Test
    public void checkIfSearchTextIsCaught(){
        ActivityScenario<EmployerHomepage> scenario = employerRule.getScenario();
        onView(withId(R.id.searchBar)).perform(typeText("Jim Jones"));
        onView(withId(R.id.searchBar)).check(matches(withText(R.string.TEST_STRING)));
    }
}