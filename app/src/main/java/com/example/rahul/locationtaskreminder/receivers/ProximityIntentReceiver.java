package com.example.rahul.locationtaskreminder.receivers;

/**
 * Created by Rahul on 12/26/2016.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.rahul.locationtaskreminder.Constants.Constant;
import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.activities.MainActivity;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

public class ProximityIntentReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        ItemPojo itemPojo = (ItemPojo) bundle.getSerializable("object");
        if (itemPojo != null) {

            String key = LocationManager.KEY_PROXIMITY_ENTERING;
            Boolean entering = intent.getBooleanExtra(key, false);
            if (entering) {
                Log.d(getClass().getSimpleName(), "entering");
            } else {
                Log.d(getClass().getSimpleName(), "exiting");
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
            Notification notification = notificationCompat.setContentIntent(pendingIntent).setSmallIcon(R.mipmap.ic_location_notification).setWhen(System.currentTimeMillis()).setContentTitle(itemPojo.getName())
                    .setAutoCancel(false).setColor(Color.WHITE).setContentText(itemPojo.getDescription()).setDefaults(Notification.DEFAULT_VIBRATE).setDefaults(Notification.DEFAULT_LIGHTS).build();

            notificationManager.notify(NOTIFICATION_ID, notification);
            itemPojo.setTaskStatus("completed");

            Log.e("idsd", itemPojo.getId() + "");
            Constant.dbHelper.updateTask(itemPojo);
        }
    }


}

