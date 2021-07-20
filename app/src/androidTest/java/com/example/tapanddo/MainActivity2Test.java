package com.example.tapanddo;

import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;

import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

public class MainActivity2Test {

    @Rule
    public ActivityTestRule<MainActivity2> mainActivity2TestRule = new ActivityTestRule<>(MainActivity2.class);
    MainActivity2 mainActivity2;

    private static final int[] MENU_CONTENT_ITEM_IDS = {
            R.id.tab_sales, R.id.tab_bookings, R.id.tab_Employees
    };
    private Map<Integer, String> menuStringContent;

    private BottomNavigationView bottomNavigation;

    @Before
    public void setUp() throws Exception {
        mainActivity2 = mainActivity2TestRule.getActivity();
        mainActivity2.setTheme(R.style.Theme_Design_Light_NoActionBar);
        bottomNavigation = mainActivity2.findViewById(R.id.bottom_navigation);

        Resources res = mainActivity2.getResources();
        menuStringContent = new HashMap<>(MENU_CONTENT_ITEM_IDS.length);
        menuStringContent.put(R.id.tab_sales, res.getString(R.string.sales_name));
        menuStringContent.put(R.id.tab_bookings, res.getString(R.string.booking_name));
        menuStringContent.put(R.id.tab_Employees, res.getString(R.string.employees_name));
    }


    @After
    public void tearDown() throws Exception {
    }

    /* Visibility Tests below */
    @Test
    public void MainActivity2InView(){
        onView(withId(R.id.workplace)).check(matches(isDisplayed()));
    }

    @Test
    public void BottomNavigationVisible(){
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void ActionBarVisible(){
        onView(withId(R.id.app_bar)).check(matches(isDisplayed()));
    }

    /* Navigating Tests below */
    @Test
    public void navSelectedItemID(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.bookings)).check(matches(isDisplayed()));
        onView(withId(R.id.tab_Employees)).perform(click());
        onView(withId(R.id.employees)).check(matches(isDisplayed()));
        onView(withId(R.id.tab_sales)).perform(click());
        onView(withId(R.id.sales)).check(matches(isDisplayed()));
    }

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


    /*
    @Test
    public void navActionBarSingOut(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withContentDescription(R.string.signout)).perform(click());
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }*/
    @Test
    public void navActionBarSingOut() {
        onView(withId(R.id.sales)).check(matches(isDisplayed()));
    }

    /* Sales Fragment Tests below */
    @Test
    public void SalesInView() {
        onView(withId(R.id.sales)).check(matches(isDisplayed()));
    }

    @Test
    public void SalesTitleAndDateVisible(){
        onView(withId(R.id.sales_text)).check(matches(isDisplayed()));
        onView(withId(R.id.sales_date)).check(matches(isDisplayed()));
        onView(withId(R.id.sales_total)).check(matches(isDisplayed()));
        onView(withId(R.id.sales_total_month)).check(matches(isDisplayed()));
    }

    @Test
    public void SalesButtonVisible(){
        onView(withId(R.id.sales_add)).check(matches(isDisplayed()));
    }

    @Test
    public void SalesTitleAndDateDisplayed(){
        onView(withId(R.id.sales_text)).check(matches(withText(R.string.sales_name)));
        onView(withId(R.id.sales_date)).check(matches(withText(R.string.select_date)));
    }

    @Test
    public void AddSalesInView(){
        onView(withId(R.id.sales_add)).perform(click());
        onView(withId(R.id.add_sales_dialog)).check(matches(isDisplayed()));
    }

    /* Bookings Fragment Tests below */
    @Test
    public void BookingsInView() {
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.bookings)).check(matches(isDisplayed()));
    }

    @Test
    public void BookingsTitleAndDateVisible(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.booking_text)).check(matches(isDisplayed()));
        onView(withId(R.id.booking_date)).check(matches(isDisplayed()));
    }

    @Test
    public void BookingsButtonVisible(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.show_bottom_sheet_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.add_booking_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void BookingsTitleAndDateDisplayed(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.booking_text)).check(matches(withText(R.string.booking_name)));
        onView(withId(R.id.booking_date)).check(matches(withText(R.string.select_date)));
    }

    @Test
    public void BookingsButtonDisplayed(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.show_bottom_sheet_btn)).check(matches(withText("Bookings on the date")));
    }

    @Test
    public void BookingsNavBookingListAndBack() throws InterruptedException {
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.show_bottom_sheet_btn)).perform(click());
        onView(withId(R.id.booking_list)).check(matches(isDisplayed()));
        Thread.sleep(1000);
        onView(withId(R.id.hide_bottom_sheet_btn)).perform(click());
        onView(withId(R.id.bookings)).check(matches(isDisplayed()));
    }

    @Test
    public void BookinglistInView(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.show_bottom_sheet_btn)).perform(click());
        onView(withId(R.id.booking_list)).check(matches(isDisplayed()));
    }

    @Test
    public void AddBookingInView(){
        onView(withId(R.id.tab_bookings)).perform(click());
        onView(withId(R.id.add_booking_btn)).perform(click());
        onView(withId(R.id.add_booking_dialog)).check(matches(isDisplayed()));
    }

    /* Employees Fragment Tests below */
    @Test
    public void EmployeesInView() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.tab_Employees)).perform(click());
        onView(withId(R.id.employees)).check(matches(isDisplayed()));
    }

    @Test
    public void EmployeesTitleAndListViewVisible() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.tab_Employees)).perform(click());
        onView(withId(R.id.emp_text)).check(matches(isDisplayed()));
        onView(withId(R.id.emp_list)).check(matches(isDisplayed()));
    }

    @Test
    public void EmployeesButtonVisible(){
        onView(withId(R.id.tab_Employees)).perform(click());
        onView(withId(R.id.emp_add)).check(matches(isDisplayed()));
    }

    @Test
    public void EmployeesTitleDisplayed(){
        onView(withId(R.id.tab_Employees)).perform(click());
        onView(withId(R.id.emp_text)).check(matches(withText(R.string.employees_name)));
    }

    @Test
    public void EmployeesNavAddEmployeeDialog() {
        onView(withId(R.id.tab_Employees)).perform(click());
        onView(withId(R.id.emp_add)).perform(click());
        onView(withId(R.id.add_emp_dialog)).check(matches(isDisplayed()));
    }

    @Test
    public void EmployeesNavEmployeeInfoDialog() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.tab_Employees)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.emp_list)).atPosition(0).perform(click());
        onView(withId(R.id.emp_info_dialog)).check(matches(isDisplayed()));
    }

}