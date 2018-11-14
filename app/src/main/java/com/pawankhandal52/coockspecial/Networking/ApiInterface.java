/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.Networking;

import com.pawankhandal52.coockspecial.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * This interface class have a all url to get the data from web
 */
interface ApiInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
