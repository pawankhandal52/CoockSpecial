/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.Networking;

import com.pawankhandal52.coockspecial.Utils.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is used to build the retrofit client which help to make a network calls
 */
public class RetrofitClient {
    private static Retrofit sRetrofit;
    
    
    public static Retrofit getRetrofitClient(){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(AppConstants.BASE_URL)
                    .build();
        }
        return sRetrofit;
    }
}
