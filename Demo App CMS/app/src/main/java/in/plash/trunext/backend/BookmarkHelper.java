package in.plash.trunext.backend;

import com.activeandroid.query.Select;
import com.google.gson.Gson;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.activity.SuperActivity;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.databases.DataBaseBookMark;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.ResponseArticles;
import in.plash.trunext.responceObjects.ResponseBookMarkIds;
import in.plash.trunext.util.L;

/**
 * Created by Kiran on 11/17/2015.
 */
public class BookmarkHelper extends SuperActivity {

    public static  DataBaseBookMark dataBaseBookMark;
    private static Gson gson;
    public static ResponseBookMarkIds bookMarkIds;
    public static ResponseArticles bookMarkData;
    public BackendAdaptor backendAdaptor = new BackendAdaptor(this, this);
    public static final String TAG_ID = "TAG_ID";
    public static final String DB_NEW = "NEW";

    public static ResponseBookMarkIds getBookMarkIds() {
     //   dataBaseBookMark = new Select().from(DataBaseBookMark.class).where("flag = ?", "TAG_ID").executeSingle();
        dataBaseBookMark = DataBaseBookMark.getBookMarkData(TAG_ID);
        if (dataBaseBookMark != null) {
            String response = dataBaseBookMark.JsonBody;
            L.m("Get Bookmark ID's DB: ", " " + response);
            gson = new Gson();
            return bookMarkIds = gson.fromJson(response, ResponseBookMarkIds.class);
        } else {
            L.m("Get Bookmark ID's DB: ", "Failed to get ");
            return null;
        }
    }

    public void doBookMarksIdsCall() {  // Fetching all bookmarked ID's Data
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_GET_BOOKMARK_IDS);

        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();

        headerReg.setProcessid(Constants.GET_BOOKMARK_IDS_PROCESS_ID);

        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);

        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void getAllBookMarkedIdsCallBack(String response) {   // Fetching all bookmarked ID's Data - Success Callback
        L.m("Get Bookmark ID's NET: ", "Successful " + response);
        dataBaseBookMark = DataBaseBookMark.getBookMarkData(TAG_ID);
        if (dataBaseBookMark != null) {
          DataBaseBookMark.deleteDBbookMark(TAG_ID);
          //  new Delete().from(DataBaseBookMark.class).where("flag = ?", "TAG_ID").execute();
        }
        dataBaseBookMark = new DataBaseBookMark(TAG_ID, response);
        dataBaseBookMark.save();
        L.m("Db BookMark Ids ::::::", " Updated");
    }

    @Override
    public void errorCallBack(String response) {
        L.m("Get Bookmark ID's: ", "failed" + response);
    }


    public void updateBookMarkDataBase() {   // Fetching all bookmarked Data
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_GET_BOOKMARK);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.GET_BOOKMARK_PROCESS_ID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void getAllBookMarkedCallBack(String response) { // Fetching all bookmarked Data - Success Callback
        L.m("Get Bookmark :", "Successful" + response);
        if (dataBaseBookMark != null) {
            DataBaseBookMark.deleteDBbookMark(DB_NEW);
            //  new Delete().from(DataBaseBookMark.class).execute();
        }
        dataBaseBookMark = new DataBaseBookMark(DB_NEW, response);
        dataBaseBookMark.save();
    }


    public static ResponseArticles getBookMarkedData() {
        dataBaseBookMark = new Select().from(DataBaseBookMark.class).where("flag = ?", "NEW").executeSingle();
        if (dataBaseBookMark != null) {
            String response = dataBaseBookMark.JsonBody;
            gson = new Gson();
            return bookMarkData = gson.fromJson(response, ResponseArticles.class);
        } else {
            return null;
        }
    }


    public void doBookMarkSelectedApiCall(int articleID, int issueID) {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_BOOKMARK);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.BOOK_MARK_PROCESS_ID);
        bodyReq.setIssueid(issueID);
        bodyReq.setId(articleID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void bookMarkCallBack(String response) {
        L.m("Bookmark Add: ", "SUCCESSFUL");
        doBookMarksIdsCall();
        updateBookMarkDataBase();
    }

    public void doBookMarkRemoveApiCall(int articleID, int issueID) {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_REMOVE_BOOKMARK);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.REMOVE_BOOKMARK_PROCESS_ID);
        bodyReq.setIssueid(issueID);
        bodyReq.setId(articleID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void removeBookMarkCallBack(String response) {
        L.m("Bookmark Remove: ", "SUCCESSFUL");
        doBookMarksIdsCall();
        updateBookMarkDataBase();
    }
}
