package com.classschedule.classtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChanel1Notification("Class Schedule","Today's Class Shedule.");
        notificationHelper.getManager().notify(1, nb.build());








       /* if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            );
            if (noConnectivity) {
                nb = notificationHelper.getChanel1Notification("Online Notification","Your Are Now Online.Please check Today's Class Shedule.");
                notificationHelper.getManager().notify(1, nb.build());
            } else {
                nb = notificationHelper.getChanel1Notification("Offline Notification","You are disconnected to Class Schedule app.");
                notificationHelper.getManager().notify(1, nb.build());
            }
    }*/


    }

}
