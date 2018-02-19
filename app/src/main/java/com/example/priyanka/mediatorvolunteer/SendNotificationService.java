package com.example.priyanka.mediatorvolunteer;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.priyanka.mediatorvolunteer.Activities.HomeActivity;
import com.example.priyanka.mediatorvolunteer.Fragments.NotificationDialogFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class SendNotificationService extends FirebaseMessagingService {

    private static final String TAG = "SendNotificationService" ;
    public static String message = "";
    public static String request_id = "";
    public static String type = "";

    public SendNotificationService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG,"data received : "+ remoteMessage.getData().get("type") );

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            message = remoteMessage.getNotification().getBody();
            request_id = remoteMessage.getNotification().getTitle();
            type = remoteMessage.getData().get("type");
            Log.d(TAG,"request id: "+request_id);
        }
        else
        {
            Log.d(TAG,"msg is null");
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }


}
