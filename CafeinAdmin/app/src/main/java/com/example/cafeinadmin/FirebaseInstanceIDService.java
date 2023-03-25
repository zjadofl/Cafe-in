package com.example.cafeinadmin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseInstanceIDService extends FirebaseMessagingService {
    //구글 토큰을 없는 값.
    //토큰은 앱이 설치된 기기에 대한 고유값으로 푸시를 보낼 때 사용.
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("FCM Log", "Refreshed token : " + token);
    }

    //메세지를 받았을 경우 그 메세지에 대하여 구현하는 부분.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            Log.d("FCM Log", "알림 메세지: "+ remoteMessage.getNotification().getBody());
            String messageBody = remoteMessage.getNotification().getBody();
            String messageTitle = remoteMessage.getNotification().getTitle();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Channel ID";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(messageTitle)
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelName = "Channel Name";
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}




