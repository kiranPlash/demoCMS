package in.plash.trunext.backend;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.activity.SuperActivity;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.databases.DbLoginStatus;
import in.plash.trunext.databases.DbUserDetails;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.LoginResponse;
import in.plash.trunext.util.L;

/**
 * Created by Kiran on 11/17/2015.
 */
public class LoginHelper extends SuperActivity {
    private static DbLoginStatus dbLoginStatus;
    boolean status = false;
    BackendAdaptor backendAdaptor = new BackendAdaptor(this, this);
    public static DbUserDetails dbUserDetails;


    public static boolean checkLoginStatus() {
        Boolean loginStatus = false;

        dbLoginStatus = new DbLoginStatus();
        int count = new Select().from(DbLoginStatus.class).where("tag = ?", "status").execute().size();

        if (count > 0) {
            dbLoginStatus = new Select().from(DbLoginStatus.class).where("tag = ?", "status").executeSingle();
            loginStatus = Boolean.valueOf(dbLoginStatus.userLoginStatus);
            L.m("Login", "" + loginStatus);
        }

        return loginStatus;
    }


    public static void saveToDb(String response, int userID) {
        dbLoginStatus = new DbLoginStatus("true", "status");
        dbLoginStatus.save();
        dbLoginStatus = new DbLoginStatus(("" + userID), "userID");
        dbLoginStatus.save();
        // Publication and Publisher ID for Demo App only
        dbLoginStatus = new DbLoginStatus(("" + Constants.PUBLICATIONID), Constants.DB_LOGIN_PUBLICATIONID);
        dbLoginStatus.save();
        dbLoginStatus = new DbLoginStatus(("" + Constants.PUBLISHERID), Constants.DB_LOGIN_PUBLISHERID);
        dbLoginStatus.save();

        DbUserDetails dbUserDetails = new DbUserDetails("details", response);
        dbUserDetails.save();
    }

    public static void setUserId() {
        dbLoginStatus = new DbLoginStatus();
        int count = DbLoginStatus.getUserCount();

        if (count > 0) {
            dbLoginStatus = DbLoginStatus.getUserID(Constants.DB_LOGIN_USER_ID);
            Constants.USER_ID = dbLoginStatus.userLoginStatus;
            L.m("User ID", "::::: " + Constants.USER_ID);
        }
    }

    public static void setPublicationID(){
        dbLoginStatus = new DbLoginStatus();
        int count = DbLoginStatus.getUserCount();
        if (count > 0) {

            dbLoginStatus = DbLoginStatus.getUserID(Constants.DB_LOGIN_PUBLICATIONID);
        //    L.m("User PUBLICATIONID", "::::: " + dbLoginStatus.userLoginStatus);
            Constants.PUBLICATIONID = Integer.parseInt(dbLoginStatus.userLoginStatus);


            dbLoginStatus = new DbLoginStatus();
            dbLoginStatus = DbLoginStatus.getUserID(Constants.DB_LOGIN_PUBLISHERID);
            Constants.PUBLISHERID = Integer.parseInt(dbLoginStatus.userLoginStatus);

            L.m("ID's", " Publi " + Constants.PUBLICATIONID + " Pub " + Constants.PUBLISHERID);
        }

    }


    public void doLogoutNetworkCall() {

        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_LOGOUT);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.LOGOUT);
        bodyReq.setEventId(0);
        bodyReq.setEvent("LOGOUT");
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);


        //   goToLoginActivity();


    }

    @Override
    public void logoutCallBack(String response) {
        L.m("Logout", "Success");
        // resetDatabase();

    }

    @Override
    public void errorCallBack(String response) {
        super.errorCallBack(response);
    }


    public void resetDatabase() {
        SQLiteDatabase db = ActiveAndroid.getDatabase();
        List<String> tables = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence")) {
                tables.add(tableName);
            }
            cursor.moveToNext();
        }
        cursor.close();
        for (String tableName : tables) {
            db.execSQL("DELETE FROM " + tableName);
            L.m("DB", " Deleted Table: " + tableName);
        }
        db.close();
    }

    public static LoginResponse getUserDetails() {

        dbUserDetails = DbUserDetails.getUserDetails();
        if (dbUserDetails != null) {
            String responsereply = dbUserDetails.jsonBody;
            L.m("jsonlogin", " " + responsereply);
            Gson gson = new Gson();
            return gson.fromJson(responsereply, LoginResponse.class);

        }
        return null;
    }
}
