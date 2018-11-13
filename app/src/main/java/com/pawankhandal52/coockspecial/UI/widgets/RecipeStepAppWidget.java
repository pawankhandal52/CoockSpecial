package com.pawankhandal52.coockspecial.UI.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.pawankhandal52.coockspecial.R;
import com.pawankhandal52.coockspecial.UI.Activities.RecipeIngredientsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.pawankhandal52.coockspecial.services.UpdateWidgetService.WIDGET_INGREDIENT_LIST;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeStepAppWidget extends AppWidgetProvider {
    
    
    public static List<String> sIngredientList = new ArrayList<>();
    private final String TAG = RecipeStepAppWidget.class.getSimpleName();
    
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    
        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, RecipeIngredientsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_step_app_widget);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_grid_view, pendingIntent);
    
        Intent intent1 = new Intent(context, GridRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent1);
        
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //RecipeStepAppWidget.updateRecipeIngredientWidget(context, appWidgetManager, appWidgetIds);
        
    }
    
    private static void updateRecipeIngredientWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
    
    @Override
    public void onReceive(Context context, Intent intent) {
        
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,RecipeStepAppWidget.class));
        
        String action = intent.getAction();
        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")){
            
            sIngredientList = intent.getExtras().getStringArrayList(WIDGET_INGREDIENT_LIST);
            //Log.e(TAG, "onReceive: "+sIngredientList.size() );
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            //Now update all widgets
            RecipeStepAppWidget.updateRecipeIngredientWidget(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    
       
    }
}

