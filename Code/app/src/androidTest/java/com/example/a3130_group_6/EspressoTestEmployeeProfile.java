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
    public ActivityScenarioRule<EmployeeProfile> employerRule = new ActivityScenarioRule<>(EmployeeProfile.class);

    @Test
    public void checkIfDescriptionIsEmpty() {
//        onView(withId(R.id.descriptionBox)).perform(typeText(""));
//        onView(withId(R.id.employeeNameInput)).perform(typeText("Employee Name"));
//        onView(withId(R.id.employeeUsernameInput)).perform(typeText("employeeUserName"));
//        onView(withId(R.id.employeeEmailInput)).perform(typeText("2employee@dal.ca"));
//        onView(withId(R.id.employeePhoneNumInput)).perform(typeText("1234567"));
//        closeSoftKeyboard();
//        onView(withId(R.id.updateEmployeeProfile)).perform(click());
//        onView(withId(R.id.statusLabel)).check(matches(withText("Error: Empty Description Box")));
    }

}

