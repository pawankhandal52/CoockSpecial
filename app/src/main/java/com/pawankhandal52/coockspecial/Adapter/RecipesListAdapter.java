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
import android.widget.ImageView;
import android.widget.TextView;

import com.pawankhandal52.coockspecial.Models.Recipe;
import com.pawankhandal52.coockspecial.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is the recipeList adapter which provide recipe to the recycler view
 */
public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder> {
    
    private final String TAG = RecipesListAdapter.class.getSimpleName();
    private List<Recipe> mRecipeList;
    private Context mContext;
    private RecipesItemClickListener mRecipesItemClickListener;
    
    private int[] mLocalImage = {
            R.drawable.nutella_pie, R.drawable.brownii, R.drawable.yellow_cake, R.drawable.cheesecake
    };
    
    public RecipesListAdapter(List<Recipe> recipeList, Context context, RecipesItemClickListener recipesItemClickListener) {
        mRecipeList = recipeList;
        mContext = context;
        mRecipesItemClickListener = recipesItemClickListener;
    }
    
    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new RecipesViewHolder(layoutInflater.inflate(R.layout.list_recipe_item, viewGroup, false));
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int i) {
        Recipe recipe = mRecipeList.get(i);
        if (recipe != null) {
            if (recipe.getName() != null) {
                recipesViewHolder.mRecipeNameTextview.setText(recipe.getName());
            }
            if (recipe.getImage() != null && recipe.getImage().length() != 0) {
                Picasso.with(mContext).load(recipe.getImage()).placeholder(R.drawable.ic_room_service_black_24dp).into(recipesViewHolder.mRecipeImage);
            } else {
                Picasso.with(mContext).load(mLocalImage[i]).placeholder(R.drawable.ic_room_service_black_24dp).into(recipesViewHolder.mRecipeImage);
            }
        }
    }
    
    @Override
    public int getItemCount() {
        if (mRecipeList == null) return 0;
        return mRecipeList.size();
    }
    
    public void swapList(List<Recipe> recipeList) {
        
        mRecipeList = recipeList;
        if (mRecipeList != null) {
            this.notifyDataSetChanged();
        }
    }
    
    /**
     * This interface help to get the click postion in List
     */
    public interface RecipesItemClickListener {
        void onRecipeItemClick(int position);
    }
    
    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_imageView)
        ImageView mRecipeImage;
        @BindView(R.id.recipe_name_textview)
        TextView mRecipeNameTextview;
        
        RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mRecipesItemClickListener.onRecipeItemClick(clickPosition);
            
        }
    }
}
