/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.UI.widgets;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pawankhandal52.coockspecial.Models.Ingredient;
import com.pawankhandal52.coockspecial.Models.Recipe;
import com.pawankhandal52.coockspecial.R;
import com.pawankhandal52.coockspecial.Utils.RecipeSharedPrefrance;

/**
 * This is Factory Class which used to send data to app Widgets
 */
public class IngredientWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private final String TAG = IngredientWidgetFactory.class.getSimpleName();
    private Context mContext;
    private Recipe mRecipe;
    
    public IngredientWidgetFactory(Context context) {
        this.mContext = context;
    }
    
    @Override
    public void onCreate() {
    
    }
    
    @Override
    public void onDataSetChanged() {
        mRecipe = RecipeSharedPrefrance.getSelectedRecipeFromPreference(mContext);
    }
    
    @Override
    public void onDestroy() {
    
    }
    
    @Override
    public int getCount() {
        
        if (mRecipe == null) return 0;
        return mRecipe.getIngredients().size();
    }
    
    @Override
    public RemoteViews getViewAt(int position) {
        if (mRecipe == null)
            return null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_ingredient_app_widget);
        
        Ingredient ingredient = mRecipe.getIngredients().get(position);
        String content = position + 1 + ": " + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient();
        views.setTextViewText(R.id.ingredient_recipe_name, content);
        return views;
    }
    
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public boolean hasStableIds() {
        return true;
    }
    
}
