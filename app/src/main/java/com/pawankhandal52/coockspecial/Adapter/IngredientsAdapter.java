/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pawankhandal52.coockspecial.Models.Ingredient;
import com.pawankhandal52.coockspecial.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This adapter is used to show the ingredients list of a recipe
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>{
    
    private final String TAG = RecipesListAdapter.class.getSimpleName();
    private List<Ingredient> mIngredientList;
    
    public IngredientsAdapter(List<Ingredient> ingredientList, Context context) {
        mIngredientList = ingredientList;
        Context context1 = context;
    }
    
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new IngredientsViewHolder(layoutInflater.inflate(R.layout.ingredients_list,viewGroup,false));
    }
    
    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        Ingredient ingredient = mIngredientList.get(i);
        if (ingredient.getQuantity()!=null&& ingredient.getMeasure()!=null&&ingredient.getIngredient()!=null){
            Double quantity = ingredient.getQuantity();
            ingredientsViewHolder.mIngredientsItemTextView.setText(
                    String.format("%s %s %s %s", String.valueOf(i) + 1, String.valueOf(quantity),
                            ingredient.getMeasure(), ingredient.getIngredient()));
        }
    }
    
    @Override
    public int getItemCount() {
        if (mIngredientList == null) return 0;
        
        return mIngredientList.size();
    }
    
    class IngredientsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ingredients_item_textview)
        TextView mIngredientsItemTextView;
        IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
