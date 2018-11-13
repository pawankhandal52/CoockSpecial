package com.pawankhandal52.coockspecial.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateWidgetService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPDATE_WIDGET = "com.pawankhandal52.coockspecial.services.action.upate_recipe_ingredients";
    private static final String TAG = UpdateWidgetService.class.getSimpleName();
    
    public static final String WIDGET_INGREDIENT_LIST = "widget_ingredient_list";
    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }
    
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionUpdateRecipeIngredients(Context context,ArrayList<String> ingredients) {
        Log.e(TAG, "startActionUpdateRecipeIngredients: "+ingredients.size() );
        Intent intent = new Intent(context, UpdateWidgetService.class);
        //intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(WIDGET_INGREDIENT_LIST,ingredients);
        context.startService(intent);
    }
    
    
    
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.e(TAG, "onHandleIntent: "+intent.getParcelableArrayListExtra(WIDGET_INGREDIENT_LIST).size() );
                if (intent.hasExtra(WIDGET_INGREDIENT_LIST)){
                    handleActionUpdateWidget(intent.getStringArrayListExtra(WIDGET_INGREDIENT_LIST));
                }
                
            
        }
    }
    
    private void handleActionUpdateWidget(ArrayList<String> ingredients) {
        Log.e(TAG, "handleActionUpdateWidget: "+ingredients.size() );
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(WIDGET_INGREDIENT_LIST,ingredients);
        sendBroadcast(intent);
    }
    
}
