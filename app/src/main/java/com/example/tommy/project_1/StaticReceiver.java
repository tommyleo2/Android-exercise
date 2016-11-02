package com.example.tommy.project_1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import layout.FruitWidget;

/**
 * Created by tommy on 10/21/16.
 */

public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getResources().getString(R.string.static_broadcast))) {
            Intent onClickedNotificationIntent = new Intent(context, StartupActivity.class);
            PendingIntent onClickedNotificationPendingIntent = PendingIntent.getActivity(
                    context, 0, onClickedNotificationIntent, 0);

            Notification.Builder notificationBuilder = new Notification.Builder(context);
            Bundle bundle = intent.getExtras();
            notificationBuilder.setContentTitle("静态广播")
                    .setContentText(bundle.getString("name"))
                    .setSmallIcon(R.mipmap.dynamic)
                    .setTicker(bundle.getString("name"))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), bundle.getInt("picId")))
                    .setContentIntent(onClickedNotificationPendingIntent)
                    .setAutoCancel(true);
            Log.i("tick", bundle.getString("name"));
            Notification notify = notificationBuilder.build();
            NotificationManager notificationManager = (NotificationManager)context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notify);

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.fruit_widget);
            remoteView.setTextViewText(R.id.widget_text, bundle.getCharSequence("name"));
            remoteView.setImageViewResource(R.id.widget_image, bundle.getInt("picId"));

            int[] id = AppWidgetManager.getInstance(context)
                    .getAppWidgetIds(new ComponentName(context, FruitWidget.class));

            AppWidgetManager.getInstance(context).updateAppWidget(id, remoteView);
        }
    }
}
