/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pawankhandal52.coockspecial.Models.Recipe;

/**
 * This class is used to store the recipe data locally till app life in phone
 */
public class RecipeSharedPrefrance {
    public static final String RECIPE_APP_PREFERENCE = "recipe_app_preference";
    public static final String SELECTED_RECIPE_KEY = "seleted_recipe";
    
    public static Recipe getSelectedRecipeFromPreference(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(RECIPE_APP_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String selectedRecipe = sharedPreferences.getString(SELECTED_RECIPE_KEY, "");
        return gson.fromJson(selectedRecipe,Recipe.class);
    }
    
    public static void saveSelectedRecipe(Context context,Recipe recipe){
        SharedPreferences sharedPreferences = context.getSharedPreferences(RECIPE_APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        // String json=recipies.get(position).toString();
        editor.putString(SELECTED_RECIPE_KEY, json);
        editor.apply();
    }
    
}
