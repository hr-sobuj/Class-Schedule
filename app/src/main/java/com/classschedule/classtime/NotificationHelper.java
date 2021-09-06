package com.classschedule.classtime;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String Chanel1Id="Chanel1ID";
    public static final String Chanel1Name="Class Schedule";

    public static final String Chanel2Id="Chanel2ID";
    public static final String Chanel2Name="Notice";

    private NotificationManager mManger;

    public NotificationHelper(Context base) {
        super(base);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            creatChanel();
        }

    }


    @TargetApi(Build.VERSION_CODES.O)
    public void creatChanel(){


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(Chanel1Id,Chanel1Name, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.colorPrimary);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(channel);


            NotificationChannel channel1=new NotificationChannel(Chanel2Id,Chanel2Name, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(R.color.colorPrimary);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(channel1);

        }



    }


    public NotificationManager getManager(){

        if(mManger== null){

            mManger=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManger;

    }

    public NotificationCompat.Builder getChanel1Notification(String notetitle,String noteMessage){

        Uri sounduri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long vibrade[]={100,500,100,500};

        Intent resultIntent=new Intent(this,classactivity.class);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent broascast=new Intent(this,AlertReceiver.class);

        PendingIntent action=PendingIntent.getBroadcast(this,0,broascast,PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(),Chanel1Id)
        .setContentTitle(notetitle)
                .setContentText(noteMessage)
                .setSmallIcon(R.drawable.notificationicon)
                .setColor(Color.BLUE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setDefaults(Notification.DEFAULT_SOUND)


                .setSound(sounduri)
                .setVibrate(vibrade)

                .setContentIntent(resultPendingIntent);

    }

    public NotificationCompat.Builder getChane21Notification(String notetitle,String noteMessage){

        Intent resultIntent=new Intent(this,notice_display_activity.class);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(this,2,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);



        return new NotificationCompat.Builder(getApplicationContext(),Chanel2Id)
                .setContentTitle(notetitle)
                .setContentText(noteMessage)
                .setSmallIcon(R.drawable.notificationicon)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setColor(Color.BLUE)

                .setContentIntent(resultPendingIntent);
    }
}
