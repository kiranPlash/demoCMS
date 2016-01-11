package in.plash.trunext.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.query.Select;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;


import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.backend.LoginHelper;
import in.plash.trunext.customfonts.MyTextView;
import in.plash.trunext.databases.DbLoginStatus;
import in.plash.trunext.databases.DbUserDetails;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.LoginResponse;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Utilities;

public class LoginActivity extends SuperActivity {

    MyTextView txtFacebook, txtAnonymous;
    LoginButton loginButton;
    CallbackManager callbackManager;
    DbLoginStatus dbLoginStatus;
    AccessToken accessToken;
    Profile userProfile;
    String android_id;
    DbUserDetails dbUserDetails;
    AsyncHttpClient client;
    private ProfileTracker mProfileTracker;
    private BackendAdaptor backendAdaptor;


    @Override
    protected void onResume() {
        super.onResume();
        boolean isLoggedIn = LoginHelper.checkLoginStatus();
        if (isLoggedIn) {
            goToMainActivity();
        } else {

        }
        // Logs 'install' and 'app activate' App Events.
       /* AppEventsLogger.activateApp(this);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        txtFacebook = (MyTextView) findViewById(R.id.txt_facebook);
        txtAnonymous = (MyTextView) findViewById(R.id.txt_anonymous);
     //   loginButton = (LoginButton) findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
      /*  loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            Log.v("facebook - profile2", profile2.getId());
                            userProfile = profile2;
                            mProfileTracker.stopTracking();
                            doFacebookLoginNtwCall(accessToken, userProfile);
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    userProfile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", userProfile.getId());
                    doFacebookLoginNtwCall(accessToken, userProfile);
                }

                //  doFacebookLoginNtwCall(accessToken, userProfile);
                L.m("Facebook Login", "Success" + accessToken);
            }

            @Override
            public void onCancel() {
                L.T(LoginActivity.this, "Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                L.T(LoginActivity.this, "Error" + error);
            }
        });*/

        txtAnonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnonymousUserNetworkcall();

            }
        });

        txtFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
    }


    public void doFacebookLoginNtwCall(AccessToken accessToken, final Profile profile) {

        if (Utilities.isInternetConnectivityAvailable(this)) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_FACEBOOK_SIGN_IN);
            headerReg.setProcessid(Constants.FB_LOGIN_PROCESS_ID);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
            headerReg.setSocialuserid(profile.getId());
            headerReg.setAccesstoken(accessToken.toString());
            headerReg.setUsername(profile.getFirstName() + " " + profile.getLastName());
            baseData.setHeaderObj(headerReg);
            baseData.setRequestBody(bodyReq);
            backendAdaptor = new BackendAdaptor(this, this);
            backendAdaptor.getNetworkData(baseData);
            Utilities.setProgressBar(this);
        } else {
            L.T(this, "Please Connect to Internet");
        }
    }


    public void doAnonymousUserNetworkcall() {

        if (Utilities.isInternetConnectivityAvailable(this)) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_LOG_IN);
            headerReg.setProcessid(Constants.ANNONYMOUS_LOGIN_PROCESS_ID);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
            baseData.setHeaderObj(headerReg);
            baseData.setRequestBody(bodyReq);
            backendAdaptor = new BackendAdaptor(this, this);
            backendAdaptor.getNetworkData(baseData);
            Utilities.setProgressBar(this);
            Constants.isANNONYMOUS = true;
        } else {
            L.T(this, "Please Connect to Internet");
        }
    }

    @Override
    public void requestCompleted(String response) {
        super.requestCompleted(response);
        Utilities.CancelProgressBar();
        L.m("Login Anonymous", " Success: " + response);
        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
        int userID = loginResponse.getJsonobject().getId();
        Constants.USER_ID = "" + userID;
        L.m("User ID", "::::: " + Constants.USER_ID);
        LoginHelper.saveToDb(response, userID);
        goToMainActivity();
    }

    @Override
    public void errorCallBack(String response) {
        Utilities.CancelProgressBar();
        super.errorCallBack(response);
        L.m("Login Anonymous", " failure: " + response);
        L.t(this, "Unable to connect to server, Please Try after some time...");
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void goToMainActivity() {
        // check login status
        boolean isRegistered = checkNotificationRegSatus();
        if (isRegistered) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            doNotificationRegistrationCall();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void doNotificationRegistrationCall() {
        int count = new Select().from(DbLoginStatus.class).where("tag = ?", "userID").execute().size();

        if (count > 0) {
            dbLoginStatus = new Select().from(DbLoginStatus.class).where("tag = ?", "userID").executeSingle();
            Constants.USER_ID = dbLoginStatus.userLoginStatus;
            L.m("User ID", "::::: " + Constants.USER_ID);
        }

        String urlString = null;
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setPublisherid(Constants.PUBLISHERID);
        headerReg.setPublicationid(Constants.PUBLICATIONID);
        headerReg.setProcessid(Constants.DEVICE_TOKEN_FOR_NOTIFICATION_PROCESS_ID);
        headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
        headerReg.setDeviceserialno(android_id);
        headerReg.setUserid(Constants.USER_ID);
        bodyReq.setGoogleId(Constants.GCM_TOKEN);
        bodyReq.setAppleid(" ");
        bodyReq.setWindowsuri(" ");
        urlString = Constants.SERVER_URL_DEV2 + headerReg.toString() + bodyReq.toString();
        Log.i("Nottification Reg rqt ", "" + urlString);


        client = new AsyncHttpClient();
        client.get(urlString, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
// update DataBase
                dbLoginStatus = new DbLoginStatus("true", "Notification_Status");
                dbLoginStatus.save();
             //   L.T(getApplicationContext(), "Notification Registration Success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                L.m("Notification Reg", "failed to Register Notification Services");
            }
        });
    }


    private boolean checkNotificationRegSatus() {
        Boolean loginStatus = false;

        dbLoginStatus = new DbLoginStatus();
        int count = new Select().from(DbLoginStatus.class).where("tag = ?", "Notification_Status").execute().size();

        if (count > 0) {
            dbLoginStatus = new Select().from(DbLoginStatus.class).where("tag = ?", "Notification_Status").executeSingle();
            loginStatus = Boolean.valueOf(dbLoginStatus.userLoginStatus);
            L.m("Notification_Reg_Status", "" + loginStatus);
        }
        return loginStatus;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
