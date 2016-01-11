package in.plash.trunext.backend;

import android.content.Context;
import android.content.Intent;

import com.activeandroid.query.Select;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.activity.SuperActivity;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.databases.DbLoginStatus;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.util.L;

/**
 * Created by Kiran on 11/17/2015.
 */
public class NotificationHelper extends SuperActivity {
    private static DbLoginStatus dbLoginStatus;
    public BackendAdaptor backendAdaptor = new BackendAdaptor(this, this);

    public static boolean checkNotificationRegSatus() {
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


    public static void registerNotificationRes() {
        dbLoginStatus = new DbLoginStatus("true", "Notification_Status");
        dbLoginStatus.save();
        L.m("Notification ", " Notification Registration Success");
    }


    public void shareApp(Context context, String url) {
        // doShareAPICall();
        Intent sendTextIntent = new Intent();
        sendTextIntent.setAction(Intent.ACTION_SEND);
        sendTextIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.label_share_app_subject));
        sendTextIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendTextIntent.setType("text/plain");
        context.startActivity(sendTextIntent);
    }


    private void doShareAPICall(int issueID, int categoryID, int articleID) {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_SHARE);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();

        headerReg.setProcessid(Constants.SHARE_EVENT);
        bodyReq.setIssueid(issueID);
        bodyReq.setCategoryid(categoryID);
        bodyReq.setArticleId(articleID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);

    }

    @Override
    public void shareCallBack(String response) {
        super.shareCallBack(response);
    }
}
