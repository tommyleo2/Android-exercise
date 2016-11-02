package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.tommy.project_1.FruitListActivity;
import com.example.tommy.project_1.R;
import com.example.tommy.project_1.StartupActivity;

/**
 * Implementation of App Widget functionality.
 */
public class FruitWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.widget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fruit_widget);
        views.setTextViewText(R.id.widget_text, widgetText);

        Intent onClickedIntent = new Intent(context, StartupActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, onClickedIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views);
        Log.i("Setting onclick", Integer.toString(appWidgetId));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

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
}

