package com.example.a3130_group_6;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<EmployerHomepage>employerRule = new ActivityScenarioRule<>(EmployerHomepage.class);
    public ActivityScenarioRule<loginPage> loginRule = new ActivityScenarioRule<>(loginPage.class);

    @BeforeClass
    public static void setup(){
        Intents.init();
    }

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
        onView(withId(R.id.searchBar)).check(matches(withId(2131231023)));
    }

    /** AT-3**/
    @Test
    public void checkBanner(){
        onView(withId(R.id.employeeHeader)).check(matches(isDisplayed()));
    }

    /** AT-4**/
    @Test
    public void checkHomeButton(){
        onView(withId(R.id.homeButton)).perform(click());
        intended(hasComponent(EmployerHomepage.class.getName()));
    }
    /** AT-5**/
    @Test
    public void checkHeader(){
        onView(withId(R.id.employeeHeader)).check(matches(isDisplayed()));
    }
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
    /** Add task intent check**/
    @Test
    public void checkAddTask() {
        onView(withId(R.id.addTaskButton)).perform(click());
        intended(hasComponent(addTask.class.getName()));
    }
    @Test
    public void checkIfLoginPageIsShown() {
        onView(withId(R.id.userNameEt)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.passwordEt)).check(matches(withText(R.string.EMPTY_STRING)));
        onView(withId(R.id.loginBt)).check(matches(withText("login")));
        onView(withId(R.id.registerBt)).check(matches(withText("registration")));
    }

    @Test
    public void checkIfUserNameIsEmpty(){
        onView(withId(R.id.userNameEt)).perform(typeText(""));
        onView(withId(R.id.passwordEt)).perform(typeText("Password_123456"));
        onView(withId(R.id.loginBt)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_USER_NAME)));
    }

    @Test
    public void checkIfPasswordIsEmpty(){
        onView(withId(R.id.userNameEt)).perform(typeText("userName"));
        onView(withId(R.id.passwordEt)).perform(typeText(""));
        onView(withId(R.id.loginBt)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.EMPTY_PASSWORD)));
    }

    @Test
    public void checkIfUserNameIsCorrect() {
        onView(withId(R.id.userNameEt)).perform(typeText("!#$^&*123"));
        onView(withId(R.id.passwordEt)).perform(typeText("Password_123456"));
        onView(withId(R.id.loginBt)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INCORRECT_LOGIN_INFO)));
    }

    @Test
    public void checkIfPasswordIsCorrect() {
        onView(withId(R.id.userNameEt)).perform(typeText("userName"));
        onView(withId(R.id.passwordEt)).perform(typeText("123"));
        onView(withId(R.id.loginBt)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INCORRECT_LOGIN_INFO)));
    }

    @Test
    public void checkUserNameAndPasswordIsNotMatch(){
        onView(withId(R.id.userNameEt)).perform(typeText("!#$^&*123"));
        onView(withId(R.id.passwordEt)).perform(typeText("123"));
        onView(withId(R.id.loginBt)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText(R.string.INCORRECT_LOGIN_INFO)));
    }
}