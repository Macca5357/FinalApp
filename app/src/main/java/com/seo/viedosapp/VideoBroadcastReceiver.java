/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.seo.viedosapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class VideoBroadcastReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (action.equals("VIDEO_STARTED")) {
            // Video started playing, show notification
            String videoTitle = intent.getStringExtra("videoTitle");
             showNotification(context,videoTitle,"Playing ");
            NotificationCompat.Builder builder;
            builder = new NotificationCompat.Builder(context, "video_channel")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Now Playing")
                    .setContentText(videoTitle)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Show the notification
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        } else if (action.equals("VIDEO_STOPPED")) {
            // Video stopped playing, remove notification
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }
    private void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel
        String channelId = "MyBroadcastReceiverApp";
        CharSequence channelName = "MyBroadcastReceiverApp";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, channelName, importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show the notification
        notificationManager.notify(0, builder.build());
    }


}
