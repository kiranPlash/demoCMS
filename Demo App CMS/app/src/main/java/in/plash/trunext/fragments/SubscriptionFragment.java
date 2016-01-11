package in.plash.trunext.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Payu.PayuConstants;
import com.payu.payuui.PayUBaseActivity;

import java.util.ArrayList;
import java.util.List;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.activity.MainActivity;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.ResSubcription;
import in.plash.trunext.responceObjects.ResponsePublication;
import in.plash.trunext.responceObjects.paymentDetailResponse;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Utilities;


public class SubscriptionFragment extends SuperFragment implements View.OnClickListener {


    private int issueID, categoryID;
    private String imageURL;
    public TextView oneTPrice, monthPrice, yearPrice;
    public TextView oneTitle, monthTitle, yearTitle;
    public RadioButton oneBtnRadio, monthBtnRadio, yearBtnRadio;
    public TextView oneTDisc, monthDisc, yearDisc;
    public TextView subSelected, subSelPrice;
    public TextView placeOrder, continuePur;
    public EditText mobileNo, emailId;
    public ImageView subMagzineImg;
    LinearLayout price, userForm, oneTime, monthly, yearly;
    BackendAdaptor backendAdaptor = new BackendAdaptor(this, getActivity());
    private View view;
    Gson gson;
    private static int SUBCRIPTION_SELECTED = 0;
    private static String SUBCRIPTION_AMOUNT;


    private List<ResSubcription.ListEntity> list = new ArrayList<>();
    private paymentDetailResponse paymentObj;
    private String var1;

    public SubscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey("ResponsePublicationBuy")) {
                ResponsePublication.ListEntity publisherObj = (ResponsePublication.ListEntity) getArguments().getSerializable("ResponsePublicationBuy");
                if (publisherObj != null) {
                    issueID = publisherObj.getId();
                    categoryID = publisherObj.getCategory().get(0).getCategoryid();
                    imageURL = publisherObj.getMagazineCoverImage();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_subscription, container, false);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        MainActivity.setToolBarTitle("Choose a subscription");

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        doSubcriptionDetailsCall(issueID);

        price = (LinearLayout) view.findViewById(R.id.sub_price);
        userForm = (LinearLayout) view.findViewById(R.id.sub_user_form);
        oneTime = (LinearLayout) view.findViewById(R.id.one_time);
        monthly = (LinearLayout) view.findViewById(R.id.monthly_lyt);
        yearly = (LinearLayout) view.findViewById(R.id.yearly_lyt);

        price.setVisibility(View.VISIBLE);
        userForm.setVisibility(View.GONE);

        oneTitle = (TextView) view.findViewById(R.id.sub_one_title);
        monthTitle = (TextView) view.findViewById(R.id.sub_month_title);
        yearTitle = (TextView) view.findViewById(R.id.sub_year_title);

        oneTPrice = (TextView) view.findViewById(R.id.txt_sub_one_ruppee);
        monthPrice = (TextView) view.findViewById(R.id.txt_sub_month_ruppee);
        yearPrice = (TextView) view.findViewById(R.id.txt_sub_year_ruppee);

        oneBtnRadio = (RadioButton) view.findViewById(R.id.radio_btn_sub_one);
        monthBtnRadio = (RadioButton) view.findViewById(R.id.radio_btn_sub_month);
        yearBtnRadio = (RadioButton) view.findViewById(R.id.radio_btn_sub_year);

        placeOrder = (TextView) view.findViewById(R.id.sub_place_order);
        continuePur = (TextView) view.findViewById(R.id.pymt_btn_cntPurchase);

        oneTDisc = (TextView) view.findViewById(R.id.txt_sub_one_disc);
        monthDisc = (TextView) view.findViewById(R.id.txt_sub_month_disc);
        yearDisc = (TextView) view.findViewById(R.id.txt_sub_year_disc);

        subMagzineImg = (ImageView) view.findViewById(R.id.img_sub_issue);


        subSelected = (TextView) view.findViewById(R.id.pymt_info_subName);
        subSelPrice = (TextView) view.findViewById(R.id.pymt_info_price);

        mobileNo = (EditText) view.findViewById(R.id.pymt_et_txt_mobNum);
        emailId = (EditText) view.findViewById(R.id.pymt_et_txt_email);

        oneBtnRadio.setChecked(true);
        oneBtnRadio.setOnClickListener(this);
        monthBtnRadio.setOnClickListener(this);
        yearBtnRadio.setOnClickListener(this);
        placeOrder.setOnClickListener(this);
        continuePur.setOnClickListener(this);


        try {
            Glide.with(this)
                    .load(imageURL)
                    .centerCrop()
                    .crossFade()
                    .into(subMagzineImg);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void doSubcriptionDetailsCall(int issueID) {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_PAYMENT_SUBSCRIPTION);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.GET_SUBSCRIPTION_DETAILS_ID);
        bodyReq.setIssueid(issueID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);

        backendAdaptor.getNetworkData(baseData);
        Utilities.setProgressBar(getActivity());
    }

    @Override
    public void getSubscriptionDetailsCallBack(String response) {
        Utilities.CancelProgressBar();
        L.m("Subcription", " " + response);
        gson = new Gson();
        ResSubcription resSubcription = gson.fromJson(response, ResSubcription.class);


        list = resSubcription.getList();
        String period = list.get(0).getPeriod();

        if (period.equals("SINGLE")) {

            oneTime.setVisibility(View.VISIBLE);
            monthly.setVisibility(View.GONE);
            yearly.setVisibility(View.GONE);

            try {
                oneTitle.setText(list.get(0).getPeriod());
                oneTPrice.setText(list.get(0).getPrice());
                oneTDisc.setText(list.get(0).getDiscount());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                monthTitle.setText(list.get(1).getPeriod());
                monthPrice.setText(list.get(1).getPrice());
                monthDisc.setText(list.get(1).getDiscount());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                yearTitle.setText(list.get(2).getPeriod());
                yearPrice.setText(list.get(2).getPrice());
                yearDisc.setText(list.get(2).getDiscount());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                oneTitle.setText(list.get(0).getPeriod());
                oneTPrice.setText(list.get(0).getPrice());
                oneTDisc.setText(list.get(0).getDiscount());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                monthTitle.setText(list.get(1).getPeriod());
                monthPrice.setText(list.get(1).getPrice());
                monthDisc.setText(list.get(1).getDiscount());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                yearTitle.setText(list.get(2).getPeriod());
                yearPrice.setText(list.get(2).getPrice());
                yearDisc.setText(list.get(2).getDiscount());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void errorCallBack(String response) {
        L.m("Network ", "Error " + response);
        Utilities.CancelProgressBar();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_sub_one:
                monthBtnRadio.setChecked(false);
                yearBtnRadio.setChecked(false);
                break;
            case R.id.radio_btn_sub_month:
                oneBtnRadio.setChecked(false);
                yearBtnRadio.setChecked(false);
                break;
            case R.id.radio_btn_sub_year:
                monthBtnRadio.setChecked(false);
                oneBtnRadio.setChecked(false);
                break;

            case R.id.sub_place_order:
                showUserForm();
                break;

            case R.id.pymt_btn_cntPurchase:
                getUserDetails();

                break;
        }
    }

    private void getUserDetails() {
        if (mobileNo.getText().length() < 10) {
            mobileNo.setError("Enter valid mobile number");
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId.getText()).matches()) {
            emailId.setError("Enter valid email id");
        }

        if (mobileNo.getError() == null && emailId.getError() == null) {
            String mob = mobileNo.getText().toString();
            String email = emailId.getText().toString();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER-INFO", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USER-EMAIL", email);
            editor.putString("USER-MOBILE", mob);
            editor.apply();
            doContinuePurServerCall(SUBCRIPTION_SELECTED, SUBCRIPTION_AMOUNT, mob, email);

        }
    }

    private void doContinuePurServerCall(int subcriptionID, String subcriptionAmount, String mob, String email) {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_PAYMENT);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        bodyReq.setName("guest");
        bodyReq.setEmailid(email);
        bodyReq.setPhoneno(mob);
        bodyReq.setAmount(subcriptionAmount);
        bodyReq.setSubscriptionid(subcriptionID);
        headerReg.setProcessid(Constants.SEND_PAYMENT_RELATED_DETAILS);
        bodyReq.setIssueid(issueID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);

        backendAdaptor.getNetworkData(baseData);
        Utilities.setProgressBar(getActivity());
    }

    @Override
    public void getPaymentDetailes(String response) {
        Utilities.CancelProgressBar();
        L.m("Payment Details", " " + response);
        if (response != null) {
            paymentObj = gson.fromJson(response, paymentDetailResponse.class);
            doPayUCall(paymentObj);
        }
    }

    private void doPayUCall(paymentDetailResponse paymentObj) {
        PayuHashes payuHashes = new PayuHashes();
        PayuConfig payuConfig = new PayuConfig();

        PaymentParams mPaymentParams = new PaymentParams();

        mPaymentParams.setKey(paymentObj.getJsonobject().getKey());
        mPaymentParams.setAmount(paymentObj.getJsonobject().getAmount());
        mPaymentParams.setProductInfo(paymentObj.getJsonobject().getProductinfo());
        mPaymentParams.setFirstName(paymentObj.getJsonobject().getFirstname());
        mPaymentParams.setEmail(paymentObj.getJsonobject().getEmail());
        mPaymentParams.setTxnId(paymentObj.getJsonobject().getTxnid());
        mPaymentParams.setSurl(paymentObj.getJsonobject().getSurl());
        mPaymentParams.setFurl(paymentObj.getJsonobject().getFurl());
        mPaymentParams.setPg(paymentObj.getJsonobject().getPg());
        mPaymentParams.setHash(paymentObj.getJsonobject().getPaymentHash());

        mPaymentParams.setUdf1("");
        mPaymentParams.setUdf2("");
        mPaymentParams.setUdf3("");
        mPaymentParams.setUdf4("");
        mPaymentParams.setUdf5("");

        var1 = paymentObj.getJsonobject().getKey() + ":" + paymentObj.getJsonobject().getEmail();
        String environment = PayuConstants.MOBILE_STAGING_ENV + "";
        payuConfig.setEnvironment(environment.contentEquals("" + PayuConstants.PRODUCTION_ENV) ? PayuConstants.PRODUCTION_ENV : PayuConstants.MOBILE_STAGING_ENV);

        //  L.m("hash", "getPaymentHash " + paymentObj.getJsonobject().getPaymentHash());
        //  L.m("hash", "getPaymentRelatedDetailsForMobileSdkHash " + paymentObj.getJsonobject().getPaymentRelatedDetailsForMobileSdkHash());


        payuHashes.setPaymentHash(paymentObj.getJsonobject().getPaymentHash());
        payuHashes.setPaymentRelatedDetailsForMobileSdkHash(paymentObj.getJsonobject().getPaymentRelatedDetailsForMobileSdkHash());

        Intent intent = new Intent(getActivity(), PayUBaseActivity.class);
        intent.putExtra(PayuConstants.ENV, PayuConstants.MOBILE_STAGING_ENV);
        intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if (data != null) {
                if (resultCode == getActivity().RESULT_OK) {
                    new AlertDialog.Builder(getActivity())
                            .setCancelable(false)
                            .setMessage("Payment Successful")//data.getStringExtra("result"))
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.content_frame, new IssueFeedFragment()).commit();
                                }
                            }).show();
                } else if (resultCode == getActivity().RESULT_CANCELED) {
                    new AlertDialog.Builder(getActivity())
                            .setCancelable(false)
                            .setMessage("Payment Failed Please Re-try")//data.getStringExtra("result"))
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                   /* getActivity().getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.content_frame, new GridViewFragment()).commit();*/
                                }
                            }).show();
                }
            } else {
                Toast.makeText(getActivity(), "Could not receive data", Toast.LENGTH_LONG).show();
            }
        }

    }


    private void showUserForm() {
        price.setVisibility(View.GONE);
        userForm.setVisibility(View.VISIBLE);


        if (oneBtnRadio.isChecked()) {
            SUBCRIPTION_SELECTED = list.get(0).getSubscriptionid();
            SUBCRIPTION_AMOUNT = (list.get(0).getPrice());
            subSelected.setText(list.get(0).getPeriod());
            subSelPrice.setText(list.get(0).getPrice());

        } else if (monthBtnRadio.isChecked()) {

            SUBCRIPTION_SELECTED = list.get(1).getSubscriptionid();
            SUBCRIPTION_AMOUNT = (list.get(1).getPrice());
            subSelected.setText(list.get(1).getPeriod());
            subSelPrice.setText(list.get(1).getPrice());

        } else if (yearBtnRadio.isChecked()) {

            SUBCRIPTION_SELECTED = list.get(2).getSubscriptionid();
            SUBCRIPTION_AMOUNT = (list.get(2).getPrice());
            subSelected.setText(list.get(2).getPeriod());
            subSelPrice.setText(list.get(2).getPrice());
        }
    }
}
