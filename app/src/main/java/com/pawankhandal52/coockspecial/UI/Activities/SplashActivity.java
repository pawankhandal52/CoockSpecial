/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pawankhandal52.coockspecial.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is launcher activity
 */
public class SplashActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    
        showRecipeActivity();
    }
    
    private void showRecipeActivity(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,RecipeActivity.class));
            }
        }, 2000);
    }
}
