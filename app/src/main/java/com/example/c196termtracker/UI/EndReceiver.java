package com.example.c196termtracker.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.c196termtracker.R;

import androidx.core.app.NotificationCompat;

public class EndReceiver extends BroadcastReceiver {
    String channel_id = "test";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context,channel_id);
        Notification aEnd = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("Assessment Due Today").build();
        NotificationManager managerEnd = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        managerEnd.notify(notificationID++, aEnd);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

    }
    private void createNotificationChannel(Context context,String CHANNEL_ID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }
}