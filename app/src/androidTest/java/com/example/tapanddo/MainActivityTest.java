package com.example.tapanddo;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.annotation.Nonnull;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.tapanddo.MainActivityTest.CustomViewActions.replaceTextInput;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    MainActivity mainActivity;
    private static final String INPUT_EMAIL = "test@test.test";
    private static final String INPUT_PW = "tester";

    public static class CustomViewActions {
        public static ViewAction replaceTextInput(@Nonnull String stringToBeSet) {
            return actionWithAssertions(new TextInputLayoutActions(stringToBeSet));
        }
    }

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    /* Visibility Tests below */
    @Test
    public void MainActivityInView(){
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void TitleAndLogoVisible(){
        onView(withId(R.id.login_title)).check(matches(isDisplayed()));
        onView(withId(R.id.login_logo)).check(matches(isDisplayed()));
    }

    @Test
    public void TextFieldForEmailAndPasswordVisible(){
        onView(withId(R.id.login_email)).check(matches(isDisplayed()));
        onView(withId(R.id.login_pw)).check(matches(isDisplayed()));
    }

    @Test
    public void ButtonVisible(){
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.login_reset_pw)).check(matches(isDisplayed()));
        onView(withId(R.id.login_sign_up)).check(matches(isDisplayed()));
    }

    @Test
    public void ActionBarVisible(){
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }

    /* Matching Tests below */
    @Test
    public void TitleDisplayed(){
        onView(withId(R.id.login_title)).check(matches(withText(R.string.login_welcome)));
    }

    @Test
    public void ButtonDisplayed(){
        onView(withId(R.id.login_btn)).check(matches(withText(R.string.login_title)));
        onView(withId(R.id.login_reset_pw)).check(matches(withText(R.string.reset_password)));
        onView(withId(R.id.login_sign_up)).check(matches(withText(R.string.signup)));
    }

    /* Navigating Tests below */
    @Test
    public void navSignUp(){
        onView(withId(R.id.login_sign_up)).perform(click());
        onView(withId(R.id.signup)).check(matches(isDisplayed()));
    }

    @Test
    public void navMainActivity2(){
        onView(withId(R.id.login_email)).perform(replaceTextInput(INPUT_EMAIL));
        onView(withId(R.id.login_pw)).perform(replaceTextInput(INPUT_PW));
        onView(withId(R.id.login_btn)).perform(click());
        onView(withId(R.id.workplace)).check(matches(isDisplayed()));
    } // having problem Espresso to use EditTextLayout

    @Test
    public void navActionBarHelpMenu(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withContentDescription(R.string.help_name)).perform(click());
        onView(withId(R.id.help)).check(matches(isDisplayed()));
    }

    @Test
    public void navActionBarSettingsMenu(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withContentDescription(R.string.settings_name)).perform(click());
        onView(withId(R.id.setting)).check(matches(isDisplayed()));
    }
}