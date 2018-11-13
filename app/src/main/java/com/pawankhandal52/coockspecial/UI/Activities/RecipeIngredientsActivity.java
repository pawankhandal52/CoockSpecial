/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pawankhandal52.coockspecial.Adapter.IngredientsAdapter;
import com.pawankhandal52.coockspecial.Models.Ingredient;
import com.pawankhandal52.coockspecial.Models.Step;
import com.pawankhandal52.coockspecial.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeIngredientsSinglePaneActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeIngredientsActivity extends AppCompatActivity {
    
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static List<Ingredient> mIngredientList;
    private static List<Step> mStepList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        
        
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        
        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        
        
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.ingredients_list_key))){
            mIngredientList = intent.getParcelableArrayListExtra(getString(R.string.ingredients_list_key));
        }
        if (intent.hasExtra(getString(R.string.step_list_key))){
            mStepList = intent.getParcelableArrayListExtra(getString(R.string.step_list_key));
        }
        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupStepRecyclerView((RecyclerView) recyclerView);
        
        View ingredientsRecyclerView = findViewById(R.id.ingredients_recycler_view);
        assert ingredientsRecyclerView!=null;
        setupIngredientsRecyclerView((RecyclerView) ingredientsRecyclerView);
        /*ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mobileArray);
        ListView listView = findViewById(R.id.ingredients_list_view);
        listView.setAdapter(arrayAdapter);*/
    
        
    }
    
    private void setupIngredientsRecyclerView(RecyclerView ingredientsRecyclerView) {
        
        
        ingredientsRecyclerView.setAdapter(new IngredientsAdapter(mIngredientList,this));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void setupStepRecyclerView(@NonNull RecyclerView recyclerView) {
        
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mStepList, mTwoPane));
    }
    
    public  class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        
        private final RecipeIngredientsActivity mParentActivity;
        private final List<Step> mStepList1;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
            
            }
        };
        
        SimpleItemRecyclerViewAdapter(RecipeIngredientsActivity parent,
                                      List<Step> items,
                                      boolean twoPane) {
            mStepList1 = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_steps_content, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Step step = mStepList1.get(position);
            if (step==null){
                return;
            }
            if (step.getShortDescription()!=null){
                holder.mStepListTextView.setText(String.format("%d: %s", position+1, step.getShortDescription()));
            }
            
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePositionOfStep(holder.getAdapterPosition());
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putParcelableArrayList(RecipeDetailFragment.STEP_LIST, new ArrayList<Parcelable>(mStepList1));
                        arguments.putParcelableArrayList(RecipeDetailFragment.INGREDIENT_LIST, new ArrayList<Parcelable>(mIngredientList));
                        arguments.putInt(RecipeDetailFragment.ARG_STEP_POSITION,holder.getAdapterPosition());
                        RecipeDetailFragment fragment = new RecipeDetailFragment();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RecipeIngredientsSinglePaneActivity.class);
                        intent.putParcelableArrayListExtra(RecipeDetailFragment.STEP_LIST,new ArrayList<Parcelable>(mStepList1));
                        intent.putParcelableArrayListExtra(RecipeDetailFragment.INGREDIENT_LIST, new ArrayList<Parcelable>(mIngredientList));
                        intent.putExtra(RecipeDetailFragment.ARG_STEP_POSITION,holder.getAdapterPosition());
                        context.startActivity(intent);
                    }
                }
            });
            
        }
        
        @Override
        public int getItemCount() {
            if(mStepList1 == null) return 0;
            
            return mStepList1.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.steps_list_text_view)
            TextView mStepListTextView;
            
            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this,view);
            }
        }
    }
    
    private void savePositionOfStep(int position){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Recipe_Step", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // String json=recipies.get(position).toString();
        editor.putInt("step_position", position);
        editor.apply();
    }
    
    
}
