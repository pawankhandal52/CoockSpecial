/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.UI.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pawankhandal52.coockspecial.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeIngredientsActivity}.
 */
public class RecipeIngredientsSinglePaneActivity extends AppCompatActivity {
    
    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    private String mRecipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        int oriention = getResources().getConfiguration().orientation;
        ButterKnife.bind(this);
        
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        
        if (intent.hasExtra(getString(R.string.recipe_name_key))){
            mRecipeName = intent.getStringExtra(getString(R.string.recipe_name_key));
            
        }
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (oriention == Configuration.ORIENTATION_LANDSCAPE) {
                actionBar.hide();
            }
            actionBar.setTitle(mRecipeName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(getString(R.string.recipe_name_key),mRecipeName);
            arguments.putParcelableArrayList(RecipeDetailFragment.STEP_LIST, getIntent().getParcelableArrayListExtra(RecipeDetailFragment.STEP_LIST));
            arguments.putParcelableArrayList(RecipeDetailFragment.INGREDIENT_LIST, getIntent().getParcelableArrayListExtra(RecipeDetailFragment.INGREDIENT_LIST));
            arguments.putInt(RecipeDetailFragment.ARG_STEP_POSITION, getIntent().getIntExtra(RecipeDetailFragment.ARG_STEP_POSITION, 0));
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RecipeIngredientsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
