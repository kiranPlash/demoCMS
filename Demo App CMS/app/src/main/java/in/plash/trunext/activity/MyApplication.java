package in.plash.trunext.activity;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import in.plash.trunext.databases.DataBaseArticleBody;
import in.plash.trunext.databases.DataBaseArticlesList;
import in.plash.trunext.databases.DataBaseBookMark;
import in.plash.trunext.databases.DataBasePublisher;
import in.plash.trunext.databases.DbLoginStatus;
import in.plash.trunext.databases.DbUserDetails;
import in.plash.trunext.util.L;
import ly.count.android.sdk.Countly;


public class MyApplication extends Application {

    private static MyApplication sInstance;
    public static String COUNTLY_APP_KEY = "7c81d400cca7968103ec43eb90e2995dd72daf47";
    private String android_id;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeDB();
        sInstance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());

        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Countly.sharedInstance().init(this, "http://countlydev.cloudapp.net", COUNTLY_APP_KEY, android_id);

        //   parseNotificationEnable();   Enable when Notofication Services are needed

    }

    private void initializeDB() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(DataBaseArticleBody.class);
        configurationBuilder.addModelClasses(DataBaseArticlesList.class);
        configurationBuilder.addModelClasses(DataBaseBookMark.class);
        configurationBuilder.addModelClasses(DataBasePublisher.class);
        configurationBuilder.addModelClasses(DbLoginStatus.class);
        configurationBuilder.addModelClasses(DbUserDetails.class);

        ActiveAndroid.initialize(configurationBuilder.create());
    }

    private void parseNotificationEnable() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "s4HkuvFirwAixY4AK7CgwzN65zUtpdI7QofeZ6zb", "gQ285sSxaE1J0i1vUTU4lnDUlM7Eq4KjjAgdYW9N");
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                L.m("com.parse.push", "done" + e);
            }
        });
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        ParsePush.subscribeInBackground("all", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    L.m("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    L.m("com.parse.push", "failed to subscribe for push" + e);
                }
            }
        });
    }


    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

}
