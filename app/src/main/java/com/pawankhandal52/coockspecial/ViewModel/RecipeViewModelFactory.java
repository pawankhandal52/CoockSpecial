/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Pawan Khandal on 11/6/18,53
 */
public class RecipeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    
    public RecipeViewModelFactory() {
    }
    
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel();
    }
}
