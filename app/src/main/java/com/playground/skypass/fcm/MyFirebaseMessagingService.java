package com.playground.skypass.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.playground.skypass.R;
import com.playground.skypass.activity.MainActivity2;
import com.playground.skypass.app.util.GlobalVariable;

/**
 * Created by aderifaldi on 08/12/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final int CHAT = 1;
    private static final int PROPERTY = 2;
    private static final int MORTGAGE = 3;

    private int notificationId, requestCode;
    private String url, pageName, title, message;
    private Uri defaultSoundUri;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private Intent intent;
    private PendingIntent pendingIntent;
    private String proses_id, primary_id;
    private long prosesId, primaryId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        message = remoteMessage.getNotification().getBody();

        notificationId = GlobalVariable.getNotificationId(this);
        requestCode = GlobalVariable.getRequestCode(this);

        GlobalVariable.saveNotificationId(this, notificationId + 1);
        GlobalVariable.saveRequestCode(this, requestCode + 1);

//        url = remoteMessage.getData().get("url");
//        pageName = remoteMessage.getData().get("title");
        title = remoteMessage.getData().get("title");
        proses_id = remoteMessage.getData().get("proses_id");
        primary_id = remoteMessage.getData().get("primary_id");
        message = remoteMessage.getData().get("message");

        prosesId = Integer.parseInt(proses_id);
        primaryId = Long.parseLong(primary_id);

        sendNotification();

    }

    private void sendNotification() {

        intent = new Intent(this, MainActivity2.class);
        intent.putExtra("first_menu", "PROMOTION");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);

        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
