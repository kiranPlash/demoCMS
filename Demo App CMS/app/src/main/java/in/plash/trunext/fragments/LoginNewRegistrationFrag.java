package in.plash.trunext.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Snack;
import in.plash.trunext.util.Utilities;


public class LoginNewRegistrationFrag extends SuperFragment {


    private View view;
    private EditText emailId;
    private TextView confirmMail;
    private BackendAdaptor backendAdaptor;


    public LoginNewRegistrationFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_signup, container, false);
        emailId = (EditText) view.findViewById(R.id.edit_txt_signup_email);
        confirmMail = (TextView) view.findViewById(R.id.txt_confrm_btn_sign_up);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        confirmMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailID = emailId.getText().toString().trim();
                if (!emailID.isEmpty()) {

                    if (Utilities.isEmailValid(emailID)) {


                        doSignUpNetworkCall(emailID);

                        //  Snack.showShort(getActivity(), "Please Enter Valid Email ID");
                    } else {
                        Snack.showShort(getActivity(), "Please Enter Valid Email ID");
                    }
                } else {
                    Snack.showShort(getActivity(), "Please Enter Valid Email ID");
                }
            }
        });
    }

    private void doSignUpNetworkCall(String emailID) {
        if (Utilities.isInternetConnectivityAvailable(getActivity())) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_SIGNUP_FOR_EMAIL_ID);
            headerReg.setProcessid(Constants.SIGNUP_FOR_EMAIL_ID);
            //   headerReg.setProcessid(Constants.MAGIC_LINK_VALIDATION);
            headerReg.setDevicetype("Android " + Build.VERSION.SDK_INT);
            bodyReq.setEmailid(emailID);
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
    public void signUpRegCallBack(String response) {
        L.m("SignUp ", " Success: " + response);
        Utilities.CancelProgressBar();
        goToConfirmationFrag();
    }

    @Override
    public void errorCallBack(String response) {
        L.m("SignUp ", " Failure: " + response);
        Utilities.CancelProgressBar();
        Snack.showShort(getActivity(), "Signup Failed Please Try Again");
    }

    private void goToConfirmationFrag() {
        Fragment fragment = new SignUpConfirmationFrag();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            fragmentTransaction.replace(R.id.content_login_frame, fragment);
            // fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    }


}
