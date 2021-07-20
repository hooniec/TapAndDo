package com.example.tapanddo;

import androidx.annotation.ChecksSdkIntAtLeast;
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

public class HelpTest {

    @Rule
    public ActivityTestRule<Help> helpActivityTestRule = new ActivityTestRule<>(Help.class);
    Help help;

    @Before
    public void setUp() throws Exception {
        help = helpActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        help = null;
    }

    /* Visibility Tests below */
    @Test
    public void HelpActivityInView(){
        onView(withId(R.id.help)).check(matches(isDisplayed()));
    }

    @Test
    public void HelpTitleVisible(){
        onView(withId(R.id.help_title)).check(matches(isDisplayed()));
    }

    @Test
    public void HelpLogoVisible(){
        onView(withId(R.id.help_logo)).check(matches(isDisplayed()));
    }

    @Test
    public void HelpGuideVisible(){
        onView(withId(R.id.txt_help_guide)).check(matches(isDisplayed()));
    }

    @Test
    public void HelpEmailVisible(){
        onView(withId(R.id.txt_help_email)).check(matches(isDisplayed()));
    }

    @Test
    public void ActionBarVisible(){
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }

    /* Matching Tests below */
    @Test
    public void HelpTitleDisplayed(){
        onView(withId(R.id.help_title)).check(matches(withText(R.string.contact_us)));
    }

    @Test
    public void HelpGuideDisplayed(){
        onView(withId(R.id.txt_help_guide)).check(matches(withText(R.string.help_guide)));
    }

    @Test
    public void HelpEmailDisplayed(){
        onView(withId(R.id.txt_help_email)).check(matches(withText(R.string.contact_detail)));
    }
}