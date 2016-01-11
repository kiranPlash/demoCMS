package in.plash.trunext.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.backend.LoginHelper;
import in.plash.trunext.backend.NotificationHelper;
import in.plash.trunext.fragments.LoginFragment;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.LoginResponse;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Utilities;

public class LoginForbesActivity extends SuperActivity {

    private BackendAdaptor backendAdaptor;

    @Override
    protected void onResume() {
        super.onResume();
        boolean isLoggedIn = LoginHelper.checkLoginStatus();
        if (isLoggedIn) {
            goToMainActivity();
        } else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forbes);


        // Handling Magic Link Here
        Intent intent = getIntent();
        String action = intent.getStringExtra("token");
        Uri data = intent.getData();

        L.m("magic_link ", "Data :::: " + data + " Ac " + action);

        if (data != null) {
            String accessToken = data.getQueryParameter("token");
            L.m("magic_link ", "accessToken :::: " + accessToken);
            doEmailValidationNetworkcall(accessToken);
            //   doAnonymousUserNetworkcall();
        }

        Fragment fragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.setCustomAnimations(0, R.anim.slide_out_up);
            fragmentTransaction.replace(R.id.content_login_frame, fragment);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    }

    private void doEmailValidationNetworkcall(String accessToken) {
        if (Utilities.isInternetConnectivityAvailable(this)) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_MAGIC_LINK_VALIDATION);
            headerReg.setProcessid(Constants.MAGIC_LINK_VALIDATION);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
            bodyReq.setToken(accessToken);
            // bodyReq.setEmailid(emailID);

            baseData.setHeaderObj(headerReg);
            baseData.setRequestBody(bodyReq);
            backendAdaptor = new BackendAdaptor(this, this);
            backendAdaptor.getNetworkData(baseData);
            Utilities.setProgressBar(this);
        } else {
            L.T(this, "Please Connect to Internet");
        }
    }


    @Override
    public void getMagicValidationCallBack(String response) {
        Utilities.CancelProgressBar();
        L.m("Validation Magic Link ", " Success: " + response);


        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
        int userID = loginResponse.getJsonobject().getId();
        Constants.USER_ID = "" + userID;
        L.m("User ID", "::::: " + Constants.USER_ID);
        LoginHelper.saveToDb(response, userID);
        goToMainActivity();

    }


    public void doNotificationRegistrationCall() {

        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_NOTIFICATION);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.DEVICE_TOKEN_FOR_NOTIFICATION_PROCESS_ID);
        bodyReq.setGoogleId(Constants.GCM_TOKEN);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor = new BackendAdaptor(this, this);
        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void getNotificationCallBack(String response) {
        NotificationHelper.registerNotificationRes();
    }

    public void goToMainActivity() {
        // check login status
        boolean isRegistered = NotificationHelper.checkNotificationRegSatus();
        if (isRegistered) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // doNotificationRegistrationCall();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void doAnonymousUserNetworkcall() {
        Constants.isANNONYMOUS = true;
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
            // Constants.isANNONYMOUS = true;
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
        L.m("Login Anonymous", " failure: " + response);
        L.t(this, "Unable to connect to server, Please Try after some time...");
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
