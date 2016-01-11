package in.plash.trunext.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.Interfaces.RequestCallbacks;
import in.plash.trunext.util.L;

/**
 * Created by Kiran on 11/16/2015.
 */
public class DataNetworkCall {

    private RequestCallbacks callback;
    Context context;
    Object object;
    String theCallingAPI;
    ProgressDialog progress;

    public DataNetworkCall(Context context, Object object) {
        this.context = context;
        this.object = object;
        docall();
    }


    public void setCallback(RequestCallbacks callback) {
        this.callback = callback;
    }

    private void docall() {
        Gson gson = new Gson();
        BaseData baseData = (BaseData) object;
        String jsonRequest = null;
        theCallingAPI = baseData.getApi();
        jsonRequest = gson.toJson(baseData.getHeaderObj());
        try {
            if (jsonRequest != null) {
                doNetworkCall(baseData);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public void doNetworkCall(final BaseData baseData) {


        StringRequest strReq = new StringRequest(Method.POST,
                baseData.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


               /* switch (theCallingAPI) {
                    case Constants.API_NEXT_SET_OF_ARTICLES:
                        callback.paginationCallBack(response);
                        break;
                    case Constants.API_BOOKMARK:
                        callback.bookMarkCallBack(response);
                        break;
                    case Constants.API_REMOVE_BOOKMARK:
                        callback.removeBookMarkCallBack(response);
                        break;
                    case Constants.API_LOGOUT:
                        callback.logoutCallBack(response);
                        break;
                    case Constants.API_SHARE:
                        callback.shareCallBack(response);
                        break;
                    case Constants.API_ARTICLE_DETAIL:
                        callback.articleDetailCallBack(response);
                        break;
                    case Constants.API_GET_BOOKMARK:
                        callback.bookMarkCallBack(response);
                        break;
                    case Constants.API_GET_BOOKMARK_IDS:
                        callback.getAllBookMarkedIdsCallBack(response);
                        break;
                    case Constants.API_NOTIFICATION:
                        callback.getNotificationCallBack(response);
                        break;
                    case Constants.API_PAYMENT_SUBSCRIPTION:
                        callback.getSubscriptionDetailsCallBack(response);
                        break;
                    case Constants.API_PAYMENT:
                        callback.getPaymentDetailes(response);
                        break;
                    case Constants.API_PASSWORDLINK_FOR_EMAIL_ID:
                        callback.getMagicLinkCallBack(response);
                        break;
                    case Constants.API_MAGIC_LINK_VALIDATION:
                        callback.getMagicValidationCallBack(response);
                        break;
                    case Constants.SIGNUP_FOR_EMAIL_ID:
                        callback.signUpRegCallBack(response);
                        break;
                    default:
                        callback.requestCompleted(response);
                        break;

                }*/

                if (theCallingAPI.equalsIgnoreCase(Constants.API_NEXT_SET_OF_ARTICLES)) {
                    callback.paginationCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_BOOKMARK)) {
                    callback.bookMarkCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_REMOVE_BOOKMARK)) {
                    callback.removeBookMarkCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_LOGOUT)) {
                    callback.logoutCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_SHARE)) {
                    callback.shareCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_ARTICLE_DETAIL)) {
                    callback.articleDetailCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_GET_BOOKMARK)) {
                    callback.getAllBookMarkedCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_GET_BOOKMARK_IDS)) {
                    callback.getAllBookMarkedIdsCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_NOTIFICATION)) {
                    callback.getNotificationCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_PAYMENT_SUBSCRIPTION)) {
                    callback.getSubscriptionDetailsCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_PAYMENT)) {
                    callback.getPaymentDetailes(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_PASSWORDLINK_FOR_EMAIL_ID)) {
                    callback.getMagicLinkCallBack(response);
                } else if (theCallingAPI.equalsIgnoreCase(Constants.API_MAGIC_LINK_VALIDATION)) {
                    callback.getMagicValidationCallBack(response);
                } else {
                    callback.requestCompleted(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.m("Volley Error: ", " " + error.getLocalizedMessage());

                if (error != null) {
                    callback.errorCallBack(error.getMessage());
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                //      L.m("", "JSON_HEADER " + (baseData.getObject()).toString());
                Gson gson = new Gson();
                String header = gson.toJson(baseData.getHeaderObj());
                String body = gson.toJson(baseData.getRequestBody());

                params.put(Constants.JSON_HEADER1, header);
                params.put(Constants.JSON_BODY1, body);

                //        L.m("", "JSON_BODY " + (baseData.getRequestBody()).toString());

                L.m("Network Request", " " + theCallingAPI + " :::::: " + baseData.getUrl() + "?jsonheader=" + header + "&jsonbody=" + body);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(90000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance().addToRequestQueue(strReq);
    }
}
