/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial;

import android.os.Handler;
import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pawankhandal52.coockspecial.UI.Activities.RecipeActivity;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Recipe activity Instrumented Test
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    private static final String  RECIPE_TEXT = "Recipes";
    private static final String  FIRST_RECIPE_NAME = "Nutella Pie";
    
    @Rule
    public ActivityTestRule<RecipeActivity> mRecipeActivityActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);
    
    
    
    @Test
    public void clickOnRecipeItem_WaitForDataToDownloadTest(){
        new Thread(() -> {
            Looper.prepare();
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                onData(CoreMatchers.anything()).inAdapterView(withId(R.id.recipe_list)).atPosition(0).perform(click());
                onView(withId(R.id.recipe_name_textview)).check(matches(withText(FIRST_RECIPE_NAME)));
            }, 5000);
        }).run();
    }
    @Test
    public void checkRecipeTextOnScreen(){
        //Find the View
        onView(withId(R.id.recipe_textView)).check(matches(withText(RECIPE_TEXT)));
    }
    
    
}
