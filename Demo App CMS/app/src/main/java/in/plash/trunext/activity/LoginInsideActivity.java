package in.plash.trunext.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.backend.LoginHelper;
import in.plash.trunext.backend.NotificationHelper;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.LoginResponse;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Snack;
import in.plash.trunext.util.Utilities;


public class LoginInsideActivity extends SuperActivity implements View.OnClickListener {

    EditText emailId, password;
    ImageButton mShowPassword, mTwitter, mFacebook, mGooglePlus;
    TextView mCancel, mSignIn, mSignInBtn, mContinueAnonymous;
    String userName, passwordStr;
    int buss_ind_red = 0xff0195bc;
    BackendAdaptor backendAdaptor;


    @Override
    protected void onStart() {
        super.onStart();
      /*  if (! isServiceRunning()) {
            startService(new Intent(this, NotificationReciver.class));
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isLoggedIn = LoginHelper.checkLoginStatus();
        if (isLoggedIn) {
            LoginHelper.setPublicationID();
            goToMainActivity();
        } else {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inside);


        // Handling Magic Link Here
        Intent intent = getIntent();
        Uri data = intent.getData();

        //  L.m("magic_link ", "Data :::: " + data + " Ac " + action);

        if (data != null) {

            String publisherID = data.getQueryParameter("publisherID");
            String publicationID = data.getQueryParameter("publicationid");

            Constants.PUBLISHERID = Integer.parseInt(publisherID);
            Constants.PUBLICATIONID = Integer.parseInt(publicationID);

            L.m("magic_link ", "publisherID :::: " + publisherID);
            L.m("magic_link ", "publicationID :::: " + publicationID);


           /* String accessToken = data.getQueryParameter("token");
            L.m("magic_link ", "accessToken :::: " + accessToken);
            doEmailValidationNetworkcall(accessToken);*/
            doAnonymousUserNetworkcall();
        }


        emailId = (EditText) findViewById(R.id.edit_txt_inside_username);
        password = (EditText) findViewById(R.id.edit_txt_inside_password);

        mShowPassword = (ImageButton) findViewById(R.id.image_show_password);
        mTwitter = (ImageButton) findViewById(R.id.btn_inside_twitter);
        mFacebook = (ImageButton) findViewById(R.id.btn_inside_facebook);
        mGooglePlus = (ImageButton) findViewById(R.id.btn_inside_google);

        mCancel = (TextView) findViewById(R.id.txt_l_inside_cancel);
        mSignIn = (TextView) findViewById(R.id.txt_l_inside_signin);
        mSignInBtn = (TextView) findViewById(R.id.txt_inside_btn_sign_in);
        mContinueAnonymous = (TextView) findViewById(R.id.txt_inside_continueAnonymous);
        setEmailEntryFunctinality();

        mTwitter.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mGooglePlus.setOnClickListener(this);
        mContinueAnonymous.setOnClickListener(this);
        mSignInBtn.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        setTwitterLogin();
        setFacebookLogin();
        setGoogleLogin();


    }

    public void setGoogleLogin() {

    }

    public void setFacebookLogin() {

    }

    public void setTwitterLogin() {

    }

    private void setEmailEntryFunctinality() {
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                L.m("trunext-text", " " + s + " start " + start + " before " + before + " count " + count);


                if (s.length() == 0) {
                    mSignInBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_signin));
                    mContinueAnonymous.setVisibility(View.VISIBLE);
                } else if (s != null) {
                    mContinueAnonymous.setVisibility(View.GONE);
                    setSignBtnBackground(true);
                    //  mSignInBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_signed));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mShowPassword.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
        });

        mShowPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final boolean isOutsideView = event.getX() < 0 ||
                        event.getX() > v.getWidth() ||
                        event.getY() < 0 ||
                        event.getY() > v.getHeight();

                // change input type will reset cursor position, so we want to save it
                final int cursor = password.getSelectionStart();

                if (isOutsideView || MotionEvent.ACTION_UP == event.getAction())
                    password.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                else
                    password.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                password.setSelection(cursor);
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_inside_twitter:
                setTwitterBackground(true);
                doAnonymousUserNetworkcall();
                break;

            case R.id.btn_inside_facebook:
                setFacebookBackground(true);
                doAnonymousUserNetworkcall();
                break;

            case R.id.btn_inside_google:
                setGooglePlusBackground(true);
                doAnonymousUserNetworkcall();
                break;

            case R.id.txt_inside_btn_sign_in:
                hideKeyboard();
                userName = emailId.getText().toString();
                passwordStr = password.getText().toString();


                if (!userName.isEmpty()) {
                    //setSignBtnBackground(true);
                    Snack.showLong(this, "Please Login using CMS");

                    //   doEmailNetworkcall(userName);
                }

                break;

            case R.id.txt_inside_continueAnonymous:
                setAnonymousBackground(true);
                doAnonymousUserNetworkcall();
                break;

            case R.id.txt_l_inside_cancel:
                finish();
                break;
            case R.id.txt_l_inside_signin:
                break;

        }
    }

    private void doEmailValidationNetworkcall(String accessToken) {

        if (Utilities.isInternetConnectivityAvailable(this)) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_MAGIC_LINK_VALIDATION);
            headerReg.setProcessid(Constants.MAGIC_LINK_VALIDATION);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);

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

    private void doEmailNetworkcall(String emailID) {

        if (Utilities.isInternetConnectivityAvailable(this)) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_PASSWORDLINK_FOR_EMAIL_ID);
            headerReg.setProcessid(Constants.GET_PASSWORDLINK_FOR_EMAIL_ID);
            //   headerReg.setProcessid(Constants.MAGIC_LINK_VALIDATION);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
            bodyReq.setEmailid(emailID);
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
    public void getMagicLinkCallBack(String response) {
        Utilities.CancelProgressBar();
        L.m("Login Magic Link ", " Success: " + response);
        Snack.showShort(this, "Please Check your email for Login Link");

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Please Check your email for Login Link")//data.getStringExtra("result"))
                .setPositiveButton(R.string.openEmailClient, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        final Intent emailLauncher = new Intent(Intent.ACTION_MAIN);
                        emailLauncher.addCategory(Intent.CATEGORY_APP_EMAIL);

                        try {
                            startActivity(emailLauncher);
                        } catch (ActivityNotFoundException e) {

                        }

                                 /*   getActivity().getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.content_frame, new GridViewFragment()).commit();*/
                    }
                }).show();
    }

    public void setTwitterBackground(Boolean a) {
        if (a) {
            mTwitter.setColorFilter(buss_ind_red);
            mFacebook.clearColorFilter();
            mGooglePlus.clearColorFilter();
            mContinueAnonymous.setTextColor(Color.DKGRAY);
        } else {
            mTwitter.clearColorFilter();
        }
    }

    public void setFacebookBackground(Boolean a) {
        if (a) {
            mFacebook.setColorFilter(buss_ind_red);
            mTwitter.clearColorFilter();
            mGooglePlus.clearColorFilter();
            mContinueAnonymous.setTextColor(Color.DKGRAY);
        } else {
            mFacebook.clearColorFilter();
        }
    }

    public void setGooglePlusBackground(Boolean a) {
        if (a) {
            mGooglePlus.setColorFilter(buss_ind_red);
            mTwitter.clearColorFilter();
            mFacebook.clearColorFilter();
            mContinueAnonymous.setTextColor(Color.DKGRAY);
        } else {
            mGooglePlus.clearColorFilter();
        }
    }

    public void setAnonymousBackground(Boolean a) {
        if (a) {
            mContinueAnonymous.setTextColor(buss_ind_red);
            mGooglePlus.clearColorFilter();
            mTwitter.clearColorFilter();
            mFacebook.clearColorFilter();
        } else {
            mContinueAnonymous.setTextColor(Color.DKGRAY);
        }
    }

    public void setSignBtnBackground(Boolean a) {
        if (a) {
            mSignInBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_btn_buss_ind_signed));
            mTwitter.clearColorFilter();
            mFacebook.clearColorFilter();
            mGooglePlus.clearColorFilter();
        } else {
            mSignInBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corner_signin));
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
        L.m("Login Anonymous", " failure: " + response);
        L.t(this, "Unable to connect to server, Please Try after some time...");
    }


    public void goToMainActivity() {
        // check login status
        boolean isRegistered = NotificationHelper.checkNotificationRegSatus();
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


    public boolean isServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if ("in.media.plash.trunext.util.NotificationReciver".equals(service.service.getClassName())) {
                L.m("Notification Reg", " Registered Notification Services");
                return true;
            }
        }
        return false;
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
