package com.example.a3130_group_6;

import androidx.test.espresso.intent.Intents;

import org.junit.BeforeClass;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class EspressoTestListingHistory {
    @BeforeClass
    public static void setup(){
        Intents.init();
    }

    /** AT-3**/
    @Test
    public void checkBackButton(){
        onView(withId(R.id.backButton)).perform(click());
        // intended route to EmployerProfile - not yet complete -Bryson :(
        intended(hasComponent(EmployerHomepage.class.getName()));
    }
}
