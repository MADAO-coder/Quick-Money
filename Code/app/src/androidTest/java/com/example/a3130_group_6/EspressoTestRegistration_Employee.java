package com.example.a3130_group_6;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTestRegistration_Employee {

    @Rule
    public ActivityScenarioRule<registrationForEmployees> RuleRegistration = new ActivityScenarioRule<>(registrationForEmployees.class);

    @Test
    public void checkIfRegistrationEmployeeShows() {
        onView(withId(R.id.AddPayment)).check(matches(withText("Add Paypal")));
        onView(withId(R.id.Submit)).check(matches(withText("Submit")));
        onView(withId(R.id.Name)).check(matches(withText("Name")));
        onView(withId(R.id.Username)).check(matches(withText("Username")));
        onView(withId(R.id.Password)).check(matches(withText("Password")));
        onView(withId(R.id.VPassword)).check(matches(withText("Verify Password")));
        onView(withId(R.id.Email)).check(matches(withText("Email")));
    }

    @Test
    public void checkIfUserNameShort() {
        onView(withId(R.id.username)).perform(typeText("a"));
        onView(withId(R.id.employeeUserError)).check(matches(withText("Username too short")));
    }

    @Test
    public void checkIfDuplicateUserName() {
        onView(withId(R.id.username)).perform(typeText("333"));
        closeSoftKeyboard();
        onView(withId(R.id.employeeUserError)).check(matches(withText("Username already taken. Please enter a different username.")));
    }

    @Test
    public void checkIfValidUserName() {
        onView(withId(R.id.username)).perform(typeText("new_worker"));
        closeSoftKeyboard();
        onView(withId(R.id.employeeUserError)).check(matches(withText("Username valid")));
    }
}

