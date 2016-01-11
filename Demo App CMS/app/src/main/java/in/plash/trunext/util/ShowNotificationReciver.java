package in.plash.trunext.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import java.util.Random;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.activity.LoginActivity;


public class ShowNotificationReciver extends ParsePushBroadcastReceiver {

    String message;
    String imageURL;
    int articleId;
    int issueId;


    @Override
    protected void onPushReceive(Context context, Intent intent) {
      //  super.onPushReceive(context, intent);
        if (intent != null) {
            Bundle extras = intent.getExtras();
            Log.d("BroadcastReciever:", "Bundle: " + extras);

            if (extras != null) {
                SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                message = extras.getString("msg");
                imageURL = extras.getString("image");
                articleId = extras.getInt("article");
                issueId = extras.getInt("issue");


                Log.d("BroadcastReciever MSG", "::::::::" + message);
                Log.d("BroadcastReciever", "imageURL from server: " + imageURL);
                Log.d("BroadcastReciever", "issue id from server: " + issueId);
                Log.d("BroadcastReciever", "article id from server: " + articleId);


                if (Utilities.checkNull(message)) {
                    postNotification(new Intent(context, LoginActivity.class), context, message, imageURL);
                }

            }

        }
    }

 /*   public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            Log.d("BroadcastReciever:", "Bundle: " + extras);

            if (extras != null) {
                SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                message = extras.getString("msg");
                imageURL = extras.getString("image");
                articleId = extras.getInt("article");
                issueId = extras.getInt("issue");


                Log.d("BroadcastReciever MSG", "::::::::" + message);
                Log.d("BroadcastReciever", "imageURL from server: " + imageURL);
                Log.d("BroadcastReciever", "issue id from server: " + issueId);
                Log.d("BroadcastReciever", "article id from server: " + articleId);


                if (Utilities.checkNull(message)) {
                    postNotification(new Intent(context, LoginActivity.class), context, message, imageURL);
                }

            }

        }
    }*/

    public static void postNotification(Intent intentAction, Context context, String message, String imageURL) {
        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentAction, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_UPDATE_CURRENT);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.img_placeholder)
                .setContentTitle("Notification")
                .setTicker("Notification")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        Notification notification = builder.getNotification();

        Random random = new Random();
        int notifID = random.nextInt(5000);
        mNotificationManager.notify(notifID, notification);

    }
}