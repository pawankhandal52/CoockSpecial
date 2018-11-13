package com.pawankhandal52.coockspecial.UI.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pawankhandal52.coockspecial.R;

import java.util.List;

import static com.pawankhandal52.coockspecial.UI.widgets.RecipeStepAppWidget.sIngredientList;

/**
 * Created by Pawan Khandal on 11/13/18,25
 */
public class GridRemoteViewsService extends RemoteViewsService {
    private final String TAG = GridRemoteViewsService.class.getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        
        return new GridRemoteViewsFactory(this.getApplicationContext());
        
    }
    
    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        Context mContext;
        List<String> mIngredientList;
    
        public GridRemoteViewsFactory(Context context) {
            mContext = context;
        }
    
        @Override
        public void onCreate() {
        
        }
    
        @Override
        public void onDataSetChanged() {
            Log.e(TAG, "onDataSetChanged: "+sIngredientList.size() );
            mIngredientList = sIngredientList;
        }
    
        @Override
        public void onDestroy() {
        
        }
    
        @Override
        public int getCount() {
            if (mIngredientList!=null){
                return mIngredientList.size();
            }
            return 0;
        }
    
        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.gridview_item_widgets);
    
            views.setTextViewText(R.id.gridview_item_widgets, mIngredientList.get(position));
    
            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.ingredients_widget_textview, fillInIntent);
    
            return views;
        }
    
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }
    
        @Override
        public int getViewTypeCount() {
            return 1;
        }
    
        @Override
        public long getItemId(int position) {
            return position;
        }
    
        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
