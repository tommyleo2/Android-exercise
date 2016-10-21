package com.example.tommy.project_1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class DynamicReceiver extends BroadcastReceiver {
    public DynamicReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(context.getResources().getString(R.string.dynamic_broadcast))) {
            Intent onClickedNotificationIntent = new Intent(context, StartupActivity.class);
            PendingIntent onClickedNotificationPendingIntent = PendingIntent.getActivity(
                    context, 0, onClickedNotificationIntent, 0);

            Notification.Builder notificationBuilder = new Notification.Builder(context);
            Bundle bundle = intent.getExtras();
            notificationBuilder.setContentTitle("动态广播")
                    .setContentText(bundle.getString("text"))
                    .setTicker("New message")
                    .setSmallIcon(R.mipmap.dynamic)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.dynamic))
                    .setContentIntent(onClickedNotificationPendingIntent)
                    .setAutoCancel(true);
            Notification notify = notificationBuilder.build();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notify);
        }
    }
}
