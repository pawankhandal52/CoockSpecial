package com.pawankhandal52.coockspecial.UI.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.pawankhandal52.coockspecial.R;
import com.pawankhandal52.coockspecial.Utils.RecipeSharedPrefrance;
import com.pawankhandal52.coockspecial.services.IngredientsWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientAppWidget extends AppWidgetProvider {
    
    private final String TAG = RecipeIngredientAppWidget.class.getSimpleName();
    
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        
        CharSequence widgetText = RecipeSharedPrefrance.getSelectedRecipeFromPreference(context).getName();
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredient_app_widget);
        views.setTextViewText(R.id.ingredient_recipe_name, widgetText);
        
        Intent intent = new Intent(context, IngredientsWidgetService.class);
        views.setRemoteAdapter(R.id.ingredient_widget_list, intent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    
    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int ignored : appWidgetIds) {
            updateIngredientWidgets(context, appWidgetManager, appWidgetIds);
        }
    }
    
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }
    
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    
}

