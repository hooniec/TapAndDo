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
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> settingsActivityTestRule = new ActivityTestRule<>(SettingsActivity.class);
    SettingsActivity settingsActivity;

    @Before
    public void setUp() throws Exception {
        settingsActivity = settingsActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        settingsActivity = null;
    }

    /* Visibility Tests below */
    @Test
    public void SettingActivityInView(){
        onView(withId(R.id.setting)).check(matches(isDisplayed()));
    }

    @Test
    public void ActionBarVisible(){
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }
}