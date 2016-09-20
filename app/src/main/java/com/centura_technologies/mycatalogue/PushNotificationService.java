package com.centura_technologies.mycatalogue;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.centura_technologies.mycatalogue.Support.GenericData;
import com.google.android.gms.games.Notifications;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by kundan on 10/22/2015.
 */
public class PushNotificationService extends GcmListenerService {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        NotificationManager manager;
        Notification myNotication;
        GenericData.notificationList.add(data);
        //  startActivity(new Intent(PushNotificationService.this,Notifications.class));
        Intent intent = new Intent(PushNotificationService.this,Notifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(PushNotificationService.this, 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(PushNotificationService.this);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder.setAutoCancel(true);
        builder.setTicker(data.getString("tickerText"));
        builder.setContentTitle(data.getString("title"));
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_setting_dark);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.setSubText(data.getString("subtitle"));   //API level 16
        builder.setNumber(100);
        if(data.getString("vibrate").matches("1"))
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000 });

        //LED
        builder.setLights(Color.RED, 3000, 3000);

        //Ton
        builder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));

        builder.build();
        myNotication = builder.getNotification();
        manager.notify((int) System.currentTimeMillis(), myNotication);

        //manager.cancel((int)System.currentTimeMillis());
    }
}

