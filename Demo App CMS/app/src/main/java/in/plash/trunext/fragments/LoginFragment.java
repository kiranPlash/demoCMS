package in.plash.trunext.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.activity.LoginActivity;
import in.plash.trunext.activity.MainActivity;
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


public class LoginFragment extends SuperFragment implements View.OnClickListener {

    EditText emailId, password;
    LoginButton loginButton;
    ImageButton mShowPassword, mTwitter, mFacebook, mLinkdIn;
    TextView mCancel, mRegister, mSignInBtn, mContinueAnonymous;
    String userName, passwordStr;
    int buss_ind_red = 0xff0195bc;
    BackendAdaptor backendAdaptor;
    LoginActivity loginActivity;

    // FaceBook
    Profile userProfile;
    AccessToken accessToken;
    CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private View view;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_forbes, container, false);

        emailId = (EditText) view.findViewById(R.id.edit_txt_inside_username);
        loginButton = (LoginButton) view.findViewById(R.id.login_forbes_button);
        // If using in a fragment
        //  loginButton.setFragment(in.plash.trunext.fragments.LoginFragment);
        loginButton.setFragment(this);

        mTwitter = (ImageButton) view.findViewById(R.id.btn_inside_twitter);
        mFacebook = (ImageButton) view.findViewById(R.id.btn_inside_facebook);
        mLinkdIn = (ImageButton) view.findViewById(R.id.btn_inside_linkdin);


        mSignInBtn = (TextView) view.findViewById(R.id.txt_inside_btn_sign_in);
        mRegister = (TextView) view.findViewById(R.id.txt_register);
        // mContinueAnonymous = (TextView) findViewById(R.id.txt_inside_continueAnonymous);


        mTwitter.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mLinkdIn.setOnClickListener(this);
        mRegister.setOnClickListener(this);
//        mContinueAnonymous.setOnClickListener(this);
        mSignInBtn.setOnClickListener(this);


        setFacebookLogin();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    hideKeyboard();
                    String emailID = emailId.getText().toString().trim();
                    if (!emailID.isEmpty()) {

                        if (Utilities.isEmailValid(emailID)) {
                            goToMagicLinkFragment(emailID);

                        } else {
                            Snack.showShort(getActivity(), "Please Enter Valid Email ID");
                        }
                    } else {
                        Snack.showShort(getActivity(), "Please Enter Valid Email ID");
                    }

                    return true;
                }
                return false;
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
                loginButton.performClick();
                break;

            case R.id.btn_inside_linkdin:
                setLinkedInBackground(true);
                doAnonymousUserNetworkcall();
                break;

            case R.id.txt_inside_btn_sign_in:
                hideKeyboard();
                userName = emailId.getText().toString().trim();


                if (!userName.isEmpty()) {

                    goToMagicLinkFragment(userName);

                }

                break;

            case R.id.txt_register:
                goToNewRigistrationCall();
                break;


        }
    }

    private void goToNewRigistrationCall() {
        fragment = new LoginNewRegistrationFrag();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.content_login_frame, fragment);

        }
        fragmentTransaction.commit();
    }

    private void goToMagicLinkFragment(String userName) {

        Bundle bundle = new Bundle();
        bundle.putString("email", userName);

        fragment = new getMagicLinkFragment();
        fragment.setArguments(bundle);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.content_login_frame, fragment);

        }
        fragmentTransaction.commit();
    }


    public void setFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                L.T(getActivity(), "Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                L.T(getActivity(), "Error" + error);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void doAnonymousUserNetworkcall() {

        if (Utilities.isInternetConnectivityAvailable(getActivity())) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_LOG_IN);
            headerReg.setProcessid(Constants.ANNONYMOUS_LOGIN_PROCESS_ID);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
            baseData.setHeaderObj(headerReg);
            baseData.setRequestBody(bodyReq);
            backendAdaptor = new BackendAdaptor(this, getActivity());
            backendAdaptor.getNetworkData(baseData);
            Utilities.setProgressBar(getActivity());
        } else {
            L.T(getActivity(), "Please Connect to Internet");
        }
    }

    public void doFacebookLoginNtwCall(AccessToken accessToken, final Profile profile) {

        if (Utilities.isInternetConnectivityAvailable(getActivity())) {
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
            backendAdaptor = new BackendAdaptor(this, getActivity());
            backendAdaptor.getNetworkData(baseData);
            Utilities.setProgressBar(getActivity());
        } else {
            L.T(getActivity(), "Please Connect to Internet");
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
        Snack.showLong(getActivity(), "Unable to connect to server, Please Try after some time...");
        // L.t(this, "Unable to connect to server, Please Try after some time...");
    }

    public void goToMainActivity() {
        // check login status
        boolean isRegistered = NotificationHelper.checkNotificationRegSatus();
        if (isRegistered) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
           getActivity().finish();
        } else {
            // doNotificationRegistrationCall();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }

    public void setTwitterBackground(Boolean a) {
        if (a) {
            mTwitter.setColorFilter(buss_ind_red);
            mFacebook.clearColorFilter();
            mLinkdIn.clearColorFilter();
            // mContinueAnonymous.setTextColor(Color.DKGRAY);
        } else {
            mTwitter.clearColorFilter();
        }
    }

    public void setFacebookBackground(Boolean a) {
        if (a) {
            mFacebook.setColorFilter(buss_ind_red);
            mTwitter.clearColorFilter();
            mLinkdIn.clearColorFilter();
            // mContinueAnonymous.setTextColor(Color.DKGRAY);
        } else {
            mFacebook.clearColorFilter();
        }
    }

    public void setLinkedInBackground(Boolean a) {
        if (a) {
            mLinkdIn.setColorFilter(buss_ind_red);
            mTwitter.clearColorFilter();
            mFacebook.clearColorFilter();

        } else {
            mLinkdIn.clearColorFilter();
        }
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
