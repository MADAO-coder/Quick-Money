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

    @Test
    public void testProfile(){

    }
    /** Test Profile Elements below **/
    @Test
    public void testProfileDetails(){
    //biography
        onView(withId(R.id.editBiography)).check(matches(withText("")));

    //business name

    //email

    //name

    //phone

    //pass

    }
}
