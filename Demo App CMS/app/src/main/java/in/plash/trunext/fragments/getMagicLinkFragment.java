package in.plash.trunext.fragments;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class getMagicLinkFragment extends SuperFragment {

    TextView getMagicLick;
    private View view;
    private BackendAdaptor backendAdaptor;
    private String email;
    ImageView back;

    public getMagicLinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_get_magic_link, container, false);
        getMagicLick = (TextView) view.findViewById(R.id.txt_btn_sign_up2);
        back = (ImageView) view.findViewById(R.id.signup_back);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* if (getArguments() != null) {
            if (getArguments().containsKey("email")) {

                 email = getArguments().getString("email");
            }
        }*/

        email = getArguments().getString("email");
        getMagicLick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEmailNetworkcall(email);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                // getActivity().dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            }
        });
    }

    private void doEmailNetworkcall(String emailID) {

        if (Utilities.isInternetConnectivityAvailable(getActivity())) {
            JsonHeaderReg headerReg = new JsonHeaderReg();
            JsonBodyReq bodyReq = new JsonBodyReq();
            BaseData baseData = new BaseData();
            baseData.setApi(Constants.API_PASSWORDLINK_FOR_EMAIL_ID);
            headerReg.setProcessid(Constants.GET_PASSWORDLINK_FOR_EMAIL_ID);
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
    public void getMagicLinkCallBack(String response) {
        Utilities.CancelProgressBar();
        L.m("Login Magic Link ", " Success: " + response);
        Snack.showShort(getActivity(), "Please Check your email for Login Link");

        new AlertDialog.Builder(getActivity())
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


                    /*    getActivity().getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, new GridViewFragment()).commit();*/

                    }
                }).show();
    }

    @Override
    public void errorCallBack(String response) {
        Utilities.CancelProgressBar();
        L.m("Login Magic Link ", " Failure: " + response);
    }
}
