package in.plash.trunext.backend;

import android.app.Activity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.activity.SuperActivity;
import in.plash.trunext.databases.DataBaseArticlesList;
import in.plash.trunext.databases.DataBasePublisher;
import in.plash.trunext.responceObjects.ResponseArticleBody;
import in.plash.trunext.responceObjects.ResponseArticles;
import in.plash.trunext.responceObjects.ResponsePublication;
import in.plash.trunext.util.L;

/**
 * Created by Kiran on 11/17/2015.
 */
public class DataHelper extends SuperActivity {

    private static DataBasePublisher dataBasePublisher;
    private static Gson gson;
    private static DataBaseArticlesList dataBaseArticlesList;

    public static int DB_TAG_CATEGORY_ID;


    // static List<ResponseArticles.ListEntity> responseArticlesObjList = new ArrayList<>();


    public static ResponsePublication getPublicationData() {
        dataBasePublisher = DataBasePublisher.getIssueData();
        if (dataBasePublisher != null) {
            if (checkLastUpdated(dataBasePublisher.time)) {
                String responseData = dataBasePublisher.PublisherJson;
                gson = new Gson();
                ResponsePublication responsePublicationObj = gson.fromJson(responseData, ResponsePublication.class);
                L.m("PublicationData", "from DB" + responseData);
                return responsePublicationObj;
            } else {
                return null;
            }
        } else {
            L.m("PublicationData", "from Network");
            return null;
        }
    }

    private static boolean checkLastUpdated(long lastUpdatedTime) {
        long currentTime = System.currentTimeMillis();
        long diff = (currentTime - lastUpdatedTime) / 60000;
        L.m("time ", " :: " + diff);
        return (diff >= 0 && diff <= 1);
    }

    public static void UpdatePublicationDB(String response) {
        DataBasePublisher.deleteDBPub();
        long StartTime = System.currentTimeMillis();
        dataBasePublisher = new DataBasePublisher("NEW", StartTime, response);
        dataBasePublisher.save();
    }


    public static List<ResponseArticles.ListEntity> getArticlesData(int categoryID) {
        List<ResponseArticles.ListEntity> responseArticlesObjList = new ArrayList<>();

        dataBaseArticlesList = DataBaseArticlesList.getArticleData(categoryID);
        if (dataBaseArticlesList != null/* && dataBaseArticlesList.articleLists.length() > 10*/) {
            String responsereply = dataBaseArticlesList.articleLists;
            gson = new Gson();
            ResponseArticles responseArticlesObj = gson.fromJson(responsereply, ResponseArticles.class);
            responseArticlesObjList.addAll(responseArticlesObj.getList());
            L.m("ArticleData", "from DB" + responsereply);
            return responseArticlesObjList;
        } else {
            return null;
        }

    }

    public static void UpdateArticleDB(String response, int categoryID) {
        DataBaseArticlesList.deleteDBArticle(categoryID);
        long StartTime = System.currentTimeMillis();
        dataBaseArticlesList = new DataBaseArticlesList(categoryID, response, StartTime);
        dataBaseArticlesList.save();
    }

    public static ResponseArticles paginationDataUpdateDB(String response, int categoryID) {
        DataBaseArticlesList dataBaseArticles = DataBaseArticlesList.getArticleData(categoryID);
        String currentList = "";


        if (dataBaseArticles != null) {
            currentList = dataBaseArticles.articleLists;
        }
        try {
            JSONArray jAry1 = new JSONObject(currentList).getJSONArray(Constants.LIST);
            JSONArray jAry2 = new JSONObject(response).getJSONArray(Constants.LIST);
            JSONObject jObj = new JSONObject();
            jObj.put(Constants.LIST, concatArray(jAry1, jAry2));
            String jsonData = jObj.toString();
            DataHelper.UpdateArticleDB(jsonData, categoryID);

            L.m("Pagination ", jsonData);

            // only sending the paginated list
            gson = new Gson();
            ResponseArticles resPaginationObj = gson.fromJson(response, ResponseArticles.class);

            return resPaginationObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONArray concatArray(JSONArray... arrs)
            throws JSONException {
        JSONArray result = new JSONArray();
        for (JSONArray arr : arrs) {
            for (int i = 0; i < arr.length(); i++) {
                result.put(arr.get(i));
            }
        }
        return result;
    }


    // for Html file direct editing

    public static String getWebData(String htmlFileName, ResponseArticleBody articleDetail, Activity context) {
        String str = null;
        try {
            InputStream is = context.getAssets().open(htmlFileName);
           //  InputStream is = context.getAssets().open("demo.html");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            str = new String(buffer);
            str = str.replace("{{CategoryName}}", articleDetail.getJsonobject().getCategoryName());
            str = str.replace("{{ArticleHeadline}}", articleDetail.getJsonobject().getArticleHeadline());
            str = str.replace("{{{ArticleBody}}}", articleDetail.getJsonobject().getArticleBody());
            str = str.replace("{{imageurl}}", articleDetail.getJsonobject().getImageurl());
            str = str.replace("{{COLOURNAME}}", articleDetail.getJsonobject().getTagcolour());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;

    }

}
