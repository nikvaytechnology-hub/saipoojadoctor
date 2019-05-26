package com.nikvay.doctorapplication.firebaseservice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nikvay.doctorapplication.MainActivity;
import com.nikvay.doctorapplication.R;
import com.nikvay.doctorapplication.utils.SharedUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private Context mContext;
    private static final String TAG = "FCM Service";
    public static final String CHANNEL_ID = "mychannelid";

    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    int m;

    public MyFirebaseMessagingService() {
        // empty required
    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        SharedUtils.putDeviceToken(getApplicationContext(),token);

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        mContext = this;

        String title = null, description = null, redirectId = null;
        try {
            JSONObject json = new JSONObject(remoteMessage.getData().get("data"));

            title = json.getString("title");
            description = json.getString("description");
            redirectId = json.getString("redirect_id");


        } catch (JSONException e) {
            e.printStackTrace();
        }

//        EventBus.getDefault().post(new HomeActivity.MessageEvent(title,description));

        sendNotification(title, description, redirectId);

    }

    private void sendNotification(String title, String description, String redirectId) {

        final String NOTIFICATION_CHANNEL_ID = "10001";
        Random random = new Random();
        m = random.nextInt(9999 - 1000) + 1000;
        long[] v = {500, 1000};

        Intent intent;

        intent = new Intent(this, MainActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("REDIRECT_ID", redirectId);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//        PendingIntent  pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

//        RemoteViews notificationSmall = new RemoteViews(getPackageName(), R.layout.notificaton_small);
//        RemoteViews notificationBig = new RemoteViews(getPackageName(), R.layout.notificaton_big);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        notificationSmall.setTextViewText(R.id.tv_notification_name_small, "Hey " + userName + " new notification received.");
//        notificationSmall.setTextViewText(R.id.tv_notification_title_small, title);
//        notificationSmall.setTextViewText(R.id.tv_notification_desc_small, description);
//
//        notificationBig.setTextViewText(R.id.tv_notification_name_big, "Hey " + userName + " new notification received.");
//        notificationBig.setTextViewText(R.id.tv_notification_title_big, title);
//        notificationBig.setTextViewText(R.id.tv_notification_desc_big, description);
//        notificationBig.setImageViewResource(R.id.iv_notification_image_big, R.drawable.app_background);

        builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(description)
//                .setCustomContentView(notificationSmall)
//                .setCustomBigContentView(notificationBig)
//                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setAutoCancel(true)
                .setSound(uri)
                .setVibrate(v)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentIntent(pendingIntent);


        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0, builder.build());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_LOW);
//        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        builder.setChannelId(CHANNEL_ID);

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }

        if (notificationManager != null) {
            notificationManager.notify(m, builder.build());
        }
    }
}
