package com.example.a3130_group_6;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class EmployerProfileTest {
    @Rule
    public ActivityScenarioRule<EmployerProfile> employerProfileRule = new ActivityScenarioRule<>(EmployerProfile.class);
    /** Test Profile Elements below **/
    @Test
    public void testRefresh(){
        onView(withId(R.id.editBiography)).perform(typeText("Biography words, please do not be there"));
        onView(withId(R.id.refreshBtn)).perform(click());
        onView(withId(R.id.editBiography)).check(matches(withText("")));
        onView(withId(R.id.statusView)).check(matches(withText("")));
    }

    /** Test Profile Elements below **/
    @Test
    public void testBiography(){
        onView(withId(R.id.editBiography)).check(matches(withText("")));
    }
    @Test
    public void testName(){
        onView(withId(R.id.editName)).check(matches(withText("")));
    }
    @Test
    public void testUsername(){
        onView(withId(R.id.editUsername)).check(matches(withText("")));
    }
    @Test
    public void testPassword(){
        onView(withId(R.id.editPassword)).check(matches(withText("")));
    }
    @Test
    public void testPhone(){
        onView(withId(R.id.editPhone)).check(matches(withText("")));
    }
    @Test
    public void testEmail(){
        onView(withId(R.id.editEmail)).check(matches(withText("")));
    }
    @Test
    public void testBusiness(){
        onView(withId(R.id.editBusiness)).check(matches(withText("")));
    }
}
