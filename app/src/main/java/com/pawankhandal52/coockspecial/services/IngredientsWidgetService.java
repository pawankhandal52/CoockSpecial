/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.pawankhandal52.coockspecial.UI.widgets.IngredientWidgetFactory;

/**
 * This service is used to get the data from activity for widgets
 */
public class IngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientWidgetFactory(this);
    }
}
