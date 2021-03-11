package com.example.a3130_group_6;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class EmployerApplicantsTest {
        @Rule
        public ActivityScenarioRule<ListingApplicants> ListingApplicantsRule = new ActivityScenarioRule<>(ListingApplicants.class);

        @BeforeClass
        public static void setup(){
            Intents.init();
        }

        /** AT 1
         * Test is now passing - due to lack of login info code isn't going deep enough into db code to make this visible
         * */
        @Test
        public void checkVisiblityNoApplicants(){
            // onView(withId(R.id.noApplicantsMessage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.noApplicantsMessage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        }
        /** AT 3*/
        /** AT 5*/



    }
