package in.plash.trunext.util;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.activity.LoginActivity;


/*
 * This service is designed to run in the background and receive messages from gcm. If the app is in the foreground
 * when a message is received, it will immediately be posted. If the app is not in the foreground, the message will be saved
 * and a notification is posted to the NotificationManager.
 */
public class NotificationReciver extends Service {
    private GoogleCloudMessaging gcm;
    public static SharedPreferences savedValues;
    String TAG = "NotificationReciver";

    public static void sendToApp(Bundle extras, Context context) {
        Intent newIntent = new Intent();
        newIntent.setClass(context, LoginActivity.class);
        newIntent.putExtras(extras);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }

    public void onCreate() {
        super.onCreate();
        String preferences = Constants.PREF_NAME;
        savedValues = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        // In later versions multi_process is no longer the default
        if (VERSION.SDK_INT > 9) {
            savedValues = getSharedPreferences(preferences, Context.MODE_MULTI_PROCESS);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        String deviceToken = sharedPreferences.getString(Constants.GCM_TOKEN, "");
        Log.d("device token", deviceToken);

        Log.d(TAG, "isPlayServicesAvailable: " + checkPlayServices());
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(getBaseContext());
            if (deviceToken.equals("")) {
                register();
            }
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return false;
    }

    private void register() {
        new AsyncTask() {
            protected Object doInBackground(final Object... params) {
                String token;
                try {
                    token = gcm.register(Constants.PROJECT_NUMBER);
                    Constants.GCM_TOKEN = token;
                    Log.i("registrationId::::::: ", token);
                    String preferences = Constants.PREF_NAME;
                    SharedPreferences sharedPreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.GCM_TOKEN, token);
                    editor.commit();
                } catch (IOException e) {

                        e.printStackTrace();

                }
                return true;
            }
        }.execute(null, null, null);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}