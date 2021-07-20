package com.example.tapanddo;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class IntroActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> introActivityTestRule = new ActivityTestRule<>(IntroActivity.class);
    IntroActivity introActivity;

    @Before
    public void setUp() throws Exception {
        introActivity = introActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        introActivity = null;
    }

    /* Visibility Tests below */
    @Test
    public void IntroActivityInView(){
        onView(withId(R.id.intro)).check(matches(isDisplayed()));
    }
}