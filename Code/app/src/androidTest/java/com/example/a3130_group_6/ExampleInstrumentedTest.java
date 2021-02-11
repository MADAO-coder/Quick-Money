package com.example.a3130_group_6;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.a3130_group_6", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);
    public IntentsTestRule<MainActivity> myIntentRule=new IntentsTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setup(){
        Intents.init();
    }

    @Test
    public void checkIfTaskTitleEmpty() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputTaskTitle)).perform(typeText(""));
        onView(withId(R.id.inputTaskDescription)).perform(typeText("Here is a description"));
        onView(withId(R.id.inputUrgency)).perform(typeText("1"));
        onView(withId(R.id.enterDate)).perform(typeText("20/10/2021"));
        onView(withId(R.id.inputPay)).perform(typeText("20"));
        closeSoftKeyboard();
        onView(withId(R.id.submitTask)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("Error: Empty Task Title")));
    }

    @Test
    public void checkIfTaskDescriptionEmpty() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputTaskTitle)).perform(typeText("Awesome Task Title"));
        onView(withId(R.id.inputTaskDescription)).perform(typeText(""));
        onView(withId(R.id.inputUrgency)).perform(typeText("1"));
        onView(withId(R.id.enterDate)).perform(typeText("20/10/2021"));
        onView(withId(R.id.inputPay)).perform(typeText("20"));
        closeSoftKeyboard();
        onView(withId(R.id.submitTask)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("Error: Empty Task Description")));
    }

    @Test
    public void checkIfUrgencyEmpty() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputTaskTitle)).perform(typeText("Awesome Task Title"));
        onView(withId(R.id.inputTaskDescription)).perform(typeText("Here is a description"));
        onView(withId(R.id.inputUrgency)).perform(typeText(""));
        onView(withId(R.id.enterDate)).perform(typeText("20/10/2021"));
        onView(withId(R.id.inputPay)).perform(typeText("20"));
        closeSoftKeyboard();
        onView(withId(R.id.submitTask)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("Error: Please fill in Urgency")));
    }

    @Test
    public void checkIfDateIsEmpty() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputTaskTitle)).perform(typeText("Awesome Task Title"));
        onView(withId(R.id.inputTaskDescription)).perform(typeText("Here is a description"));
        onView(withId(R.id.inputUrgency)).perform(typeText("1"));
        onView(withId(R.id.enterDate)).perform(typeText(""));
        onView(withId(R.id.inputPay)).perform(typeText("20"));
        closeSoftKeyboard();
        onView(withId(R.id.submitTask)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("Error: Please fill in Date")));
    }

    @Test
    public void checkIfPayIsEmpty() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.inputTaskTitle)).perform(typeText("Awesome Task Title"));
        onView(withId(R.id.inputTaskDescription)).perform(typeText("Here is a description"));
        onView(withId(R.id.inputUrgency)).perform(typeText("1"));
        onView(withId(R.id.enterDate)).perform(typeText("20/10/2021"));
        onView(withId(R.id.inputPay)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.submitTask)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText("Error: Please fill in Pay")));
    }

}