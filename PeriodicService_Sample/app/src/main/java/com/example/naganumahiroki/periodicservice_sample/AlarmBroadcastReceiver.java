package com.example.naganumahiroki.periodicservice_sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by naganumahiroki on 2015/09/15.
 */
public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {

    private Context alarmReceiverContext;
    private int notificationProvisionalId;

    @Override
    public void onReceive(Context context, Intent receivedIntent) {

        alarmReceiverContext = context;

        notificationProvisionalId = receivedIntent.getIntExtra("notificationId", 0);
        NotificationManager myNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = prepareNotification();
        myNotification.notify(notificationProvisionalId, notification);

    }

    private Notification prepareNotification(){

        Intent bootIntent =
                new Intent(alarmReceiverContext, MainActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(alarmReceiverContext, 0, bootIntent, 0);
        Notification.Builder builder = new Notification.Builder(
                alarmReceiverContext);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                .setTicker("Notification")
                .setContentTitle("Natification Came")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent);


        return builder.build();

    }
}

