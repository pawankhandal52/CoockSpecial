/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */

package com.pawankhandal52.coockspecial.Networking;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pawankhandal52.coockspecial.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class is used to do all the data operation throughout the application
 */
public class RecipesRepository {
    private static RecipesRepository sRecipesRepository;
    private static final String  TAG = RecipesRepository.class.getSimpleName();
    
    /**
     * This method is used to create a instance of this class if its null
     * @return instance of this class
     */
    public static RecipesRepository getInstance(){
        if (sRecipesRepository == null){
            sRecipesRepository = new RecipesRepository();
        }
        return sRecipesRepository;
    }
    
    /**
     *
     * @return Recipes response from server
     */
    public LiveData<Response<List<Recipe>>> getRecipesFromServer(){
        MutableLiveData<Response<List<Recipe>>> recipeResponseMutableLiveData = new MutableLiveData<>();
        
        Call<List<Recipe>> recipeCall = callRecipeApi();
        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                Log.d(TAG, "onResponse: getRecipeFromServer "+response);
                recipeResponseMutableLiveData.setValue(response);
            }
    
            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: getRecipeFromServer",t.getCause() );
                recipeResponseMutableLiveData.setValue(null);
            }
        });
        
        return recipeResponseMutableLiveData;
    }
    
    /**
     *
     * @return instance of Retrofit Client with address.
     */
    private Call<List<Recipe>> callRecipeApi() {
        return RetrofitClient.getRetrofitClient().create(ApiInterface.class).getRecipes();
    }
}
