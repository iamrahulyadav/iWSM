package com.shoaibnwar.iwsm.Services;

/**
 * Created by gold on 6/25/2018.
 */


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shoaibnwar.iwsm.R;


/**
 * Created by Sanawal Alvi on 9/18/2017.
 */

public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "xxx";
    String reqId, destination, lat, lng, name, mobile, finalPickUpAddress, pickUpTime;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Log.e("TAg", "the message from fire base is: " + remoteMessage.toString());
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

//        Log.d("xxx", "reqId: "+ reqId);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try
            {


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(remoteMessage.getNotification() != null)
        {

            Log.e("TAg", "the notification is: " + remoteMessage.getNotification());


            }

    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        String title = intent.getExtras().getString("gcm.notification.title");
        String body = intent.getExtras().getString("gcm.notification.body");
        String data = intent.getExtras().getString("gcm.notification");

        Log.e("TAG: ", "Key is haree: " + title);
        Log.e("TAG: ", "Key is body: " + body);
        Log.e("TAG: ", "Key is data: " + data);



    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void notificationForDriverArried(String title, String body){



    }
            }
