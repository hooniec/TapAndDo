package com.example.tapanddo;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.Matchers.allOf;

public class TextInputLayoutActions implements ViewAction {
    private String stringToBeSet;

    public TextInputLayoutActions(String value) {
        checkNotNull(value);
        this.stringToBeSet = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Matcher getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(TextInputEditText.class));
    }

    @Override
    public void perform(UiController uiController, View view) {
        TextInputEditText text = (TextInputEditText)view;
        text.setText(stringToBeSet);
    }

    @Override
    public String getDescription() {
        return "replace text";
    }
}
