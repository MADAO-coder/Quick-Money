package com.example.a3130_group_6;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class EspressoTestEmployeeProfile {

    @Rule
    public ActivityScenarioRule<EmployeeProfile> employeeRule = new ActivityScenarioRule<>(EmployeeProfile.class);

    @Test
    public void checkIfNameIsEmpty() {
        onView(withId(R.id.descriptionBox)).perform(typeText("This is my description"));
        onView(withId(R.id.employeeNameInput)).perform(typeText(""));
        onView(withId(R.id.employeeEmailInput)).perform(typeText("employee@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.employeePhoneNumInput)).perform(typeText("1234567"));
        closeSoftKeyboard();
        onView(withId(R.id.employeePassInput)).perform(typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.saveProfileUpdate)).perform(click());
        onView(withId(R.id.employeeStatusLabel)).check(matches(withText("Error: Please fill in name")));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.descriptionBox)).perform(typeText("This is my description"));
        onView(withId(R.id.employeeNameInput)).perform(typeText("Employee Name"));
        onView(withId(R.id.employeeEmailInput)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.employeePhoneNumInput)).perform(typeText("1234567"));
        closeSoftKeyboard();
        onView(withId(R.id.employeePassInput)).perform(typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.saveProfileUpdate)).perform(click());
        onView(withId(R.id.employeeStatusLabel)).check(matches(withText("Error: Please fill in email")));
    }

    @Test
    public void checkIfPhoneNumIsEmpty() {
        onView(withId(R.id.descriptionBox)).perform(typeText("This is my description"));
        onView(withId(R.id.employeeNameInput)).perform(typeText("Employee Name"));
        onView(withId(R.id.employeeEmailInput)).perform(typeText("employee@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.employeePhoneNumInput)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.employeePassInput)).perform(typeText("test"));
        closeSoftKeyboard();
        onView(withId(R.id.saveProfileUpdate)).perform(click());
        onView(withId(R.id.employeeStatusLabel)).check(matches(withText("Error: Please fill in phone number")));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.descriptionBox)).perform(typeText("This is my description"));
        onView(withId(R.id.employeeNameInput)).perform(typeText("Employee Name"));
        onView(withId(R.id.employeeEmailInput)).perform(typeText("employee@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.employeePhoneNumInput)).perform(typeText("1234567"));
        closeSoftKeyboard();
        onView(withId(R.id.employeePassInput)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.saveProfileUpdate)).perform(click());
        onView(withId(R.id.employeeStatusLabel)).check(matches(withText("Error: Please fill in password")));
    }

}

