/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.pawankhandal52.coockspecial.Models.Recipe;
import com.pawankhandal52.coockspecial.Networking.RecipesRepository;

import java.util.List;

import retrofit2.Response;

/**
 * This is view model for a Recipe and it save the state of ui and prevent to recall network operation
 */
public class RecipeViewModel extends ViewModel {
    private LiveData<Response<List<Recipe>>> mResponseLiveData;
    private final static String TAG = RecipeViewModel.class.getSimpleName();
    
    public RecipeViewModel() {
        if (mResponseLiveData != null){
            Log.e(TAG, "RecipeViewModel: Data"+mResponseLiveData );
            return;
        }
        
        loadRecipeFromNetwork();
    }
    
    
    private void loadRecipeFromNetwork(){
        mResponseLiveData = RecipesRepository.getInstance().getRecipesFromServer();
    }
    
    /**
     * This function is return the response from Retrofit api
     * @return Livedata
     */
    public LiveData<Response<List<Recipe>>> getMoviesResponseLiveData(){
        return mResponseLiveData;
    }
}
