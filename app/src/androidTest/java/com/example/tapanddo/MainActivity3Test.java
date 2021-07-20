package com.example.tapanddo;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivity3Test {

    @Rule
    public ActivityTestRule<MainActivity3> mainActivity3TestRule = new ActivityTestRule<>(MainActivity3.class);
    MainActivity3 mainActivity3;

    @Before
    public void setUp() throws Exception {
        mainActivity3 = mainActivity3TestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity3 = null;
    }

    /* Visibility Tests below */
    @Test
    public void MainActivity3InView(){
        onView(withId(R.id.signup)).check(matches(isDisplayed()));
    }

    @Test
    public void TitleAndInfoVisible(){
        onView(withId(R.id.signup_title)).check(matches(isDisplayed()));
        onView(withId(R.id.signup_logo)).check(matches(isDisplayed()));
    }

    @Test
    public void TextFieldForUserDetailVisible(){
        onView(withId(R.id.signup_username)).check(matches(isDisplayed()));
        onView(withId(R.id.signup_email)).check(matches(isDisplayed()));
        onView(withId(R.id.signup_pw)).check(matches(isDisplayed()));
        onView(withId(R.id.signup_pw2)).check(matches(isDisplayed()));
    }

    @Test
    public void ButtonVisible(){
        onView(withId(R.id.signup_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void ActionBarVisible(){
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }

    /* Matching Tests below */
    @Test
    public void TitleAndInfoDisplayed(){
        onView(withId(R.id.signup_title)).check(matches(withText(R.string.signup)));
    }

    @Test
    public void ButtonDisplayed(){
        onView(withId(R.id.signup_btn)).check(matches(withText(R.string.signup)));
    }

    /* Navigating Tests below */
    @Test
    public void navMainActivity2(){
        onView(withId(R.id.signup_username)).perform(click()).perform(typeText("Guest"));
        onView(withId(R.id.signup_email)).perform(click()).perform(typeText("guest@test.test"));
        onView(withId(R.id.signup_pw)).perform(click()).perform(typeText("guest1"));
        onView(withId(R.id.signup_pw2)).perform(click()).perform(typeText("guest1"));
        onView(withId(R.id.signup_btn)).perform(click());
        onView(withId(R.id.workplace)).check(matches(isDisplayed()));
    } // having problem Espresso to use EditTextLayout
}