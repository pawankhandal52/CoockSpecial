/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.UI.Activities;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pawankhandal52.coockspecial.Adapter.RecipesListAdapter;
import com.pawankhandal52.coockspecial.Models.Recipe;
import com.pawankhandal52.coockspecial.R;
import com.pawankhandal52.coockspecial.UI.widgets.RecipeIngredientAppWidget;
import com.pawankhandal52.coockspecial.Utils.RecipeSharedPrefrance;
import com.pawankhandal52.coockspecial.ViewModel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is the main activity of this app which shows the recipe list.
 */
public class RecipeActivity extends AppCompatActivity implements RecipesListAdapter.RecipesItemClickListener {
    @BindView(R.id.recipes_recyclerview)
    RecyclerView mRecipeRecyclerView;
    
    @BindView(R.id.empty_recipe_info_rl)
    RelativeLayout mEmptyRelativeLayout;
    
    @BindView(R.id.error_textview)
    TextView mErrorTextView;
    
    @BindView(R.id.recipe_progressbar)
    ProgressBar mProgressBar;
    
    private final String TAG = RecipeActivity.class.getSimpleName();
    private RecipesListAdapter mRecipesListAdapter;
    private RecipeViewModel recipeViewModel;
    
    List<Recipe> recipeList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        mRecipesListAdapter = new RecipesListAdapter(null,this,this);
        
        //This is used to make the activity in two way
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels/displayMetrics.density;
        if (screenWidth>=600)
            mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        else
            mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
    
    
        mRecipeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setAdapter(mRecipesListAdapter);
    
        hideEmptyView();
        loadRecipes();
    }
    
    private void showEmptyView(){
        mEmptyRelativeLayout.setVisibility(View.VISIBLE);
    }
    
    private void hideEmptyView(){
        mEmptyRelativeLayout.setVisibility(View.GONE);
    }
    @Override
    public void onRecipeItemClick(int position) {
        Toast.makeText(this, "Click postion "+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,RecipeIngredientsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putParcelableArrayListExtra(getString(R.string.ingredients_list_key),new ArrayList<Parcelable>(recipeList.get(position).getIngredients()));
        intent.putParcelableArrayListExtra(getString(R.string.step_list_key),new ArrayList<Parcelable>(recipeList.get(position).getSteps()));
        RecipeSharedPrefrance.saveSelectedRecipe(getApplicationContext(),recipeList.get(position));
        startActivity(intent);
    
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_widget_list);
        RecipeIngredientAppWidget.updateIngredientWidgets(this, appWidgetManager, appWidgetIds);
    }
    
    private void loadRecipes(){
         //RecipeViewModelFactory recipeViewModelFactory = new RecipeViewModelFactory();
         recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
         
        recipeViewModel.getMoviesResponseLiveData().observe(this, listResponse->{
            mProgressBar.setVisibility(View.GONE);
            Log.e(TAG, "onChanged: load recipe" );
            
            if (listResponse == null){
                showEmptyView();
                mErrorTextView.setText(getString(R.string.seems_you_lose_the_internet_connection));
                return;
            }
            
            if (listResponse.isSuccessful()){
                     recipeList = listResponse.body();
                    if (recipeList== null){
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.something_went_wrong));
                        return;
                    }
                    
                    if (recipeList.size() == 0){
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.recycler_view_empty));
                        return;
                    }
                    mRecipesListAdapter.swapList(recipeList);
                    
                    
            }else{
                mProgressBar.setVisibility(View.GONE);
                switch (listResponse.code()) {
                    case 400:
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.error_400));
                        break;
                    case 401:
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.error_401));
                        break;
                    case 403:
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.error_403));
                        break;
                    case 404:
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.error_404));
                        break;
                    case 408:
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.error_408));
                        break;
                    default:
                        showEmptyView();
                        mErrorTextView.setText(getString(R.string.error_default));
                        break;
                }
            }
        });
    }
}
