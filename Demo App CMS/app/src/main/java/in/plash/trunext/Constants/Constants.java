package in.plash.trunext.Constants;

/**
 * Created by Kiran on 9/21/2015.
 */
public class Constants {

    //  public static final String SERVER_URL_PRO = "http://app.tru-next.com/trunext/endpoint.htm";
    //    public static final String SERVER_URL_DEV2 = "http://trunxtdev.cloudapp.net/trunext/endpoint.htm";
   // public static final String SERVER_URL_DEV2 = "http://trunextdev2.cloudapp.net:8080/trunext/endpoint.htm";
       public static final String SERVER_URL_DEV2 = "http://demo.tru-next.com/trunext/endpoint.htm";

    public static final String JSON_HEADER = "jsonheader=";
    public static final String JSON_BODY = "&jsonbody=";

    public static final String JSON_HEADER1 = "jsonheader";
    public static final String JSON_BODY1 = "jsonbody";


    public static final String LOGOUT_EVENT = "LOGOUT";
    public static final String SHARE_EVENT = "SHAREARTICLE";


    public static  int PUBLISHERID = 49;  // forbes 30, limitless 38
    public static  int PUBLICATIONID = 65; // forbes 34, limitless 42


    public static final String API_LOG_IN = "login";
    public static final String API_FACEBOOK_SIGN_IN = "fbLogin";
    public static final String API_CATEGORY = "category";
    public static final String API_GET_ARTICLES = "getArticleForCategoryId";
    public static final String API_ARTICLE_DETAIL = "articleDetail";
    public static final String API_BOOKMARK = "bookmark";
    public static final String API_GET_BOOKMARK = "getBookMark";
    public static final String API_GET_BOOKMARK_IDS = "getBookMarkIds";
    public static final String API_REMOVE_BOOKMARK = "removeBookMark";
    public static final String API_NEXT_SET_OF_ARTICLES = "GetNextSetOfArticlesForCategoryId";
    public static final String API_LOGOUT = "Logout";
    public static final String API_SHARE = "Share";
    public static final String API_NOTIFICATION = "notification";
    public static final String API_PAYMENT = "payment";
    public static final String API_PAYMENT_SUBSCRIPTION = "paymentSubscription";
    public static final String API_PASSWORDLINK_FOR_EMAIL_ID = "getMagicLink";
    public static final String API_SIGNUP_FOR_EMAIL_ID = "SignUp";
    public static final String API_MAGIC_LINK_VALIDATION = "magicLinkValidation";

    public static final String ANNONYMOUS_LOGIN_PROCESS_ID = "1";
    public static final String FB_LOGIN_PROCESS_ID = "2";
    public static final String GET_PUBLICATION_PUBLISHER_PROCESS_ID = "3";
    public static final String GET_ISSUES_PUBLISHER_PROCESS_ID = "61";       // 4
    public static final String GET_ARTICLE_CATEGORY_PROCESS_ID = "62";       // 5
    public static final String GET_ARTICLE_BODY_PROCESS_ID = "6";
    public static final String BOOK_MARK_PROCESS_ID = "7";
    public static final String GET_BOOKMARK_PROCESS_ID = "8";
    public static final String GET_ARTICLE_DETAILS_PROCESS_ID = "63";        // 9
    public static final String GET_CATEGORIES_PUBLISHER_PROCESS_ID = "10";
    public static final String GET_BOOKMARK_IDS_PROCESS_ID = "11";
    public static final String REMOVE_BOOKMARK_PROCESS_ID = "12";
    public static final String LOGOUT = "13";
    public static final String GET_PAYMENT_SCHEME_PROCESS_ID = "14";
    public static final String MAKE_PAYMENT_PROCESS_ID = "15";
    public static final String DEVICE_TOKEN_FOR_NOTIFICATION_PROCESS_ID = "16";
    public static final String TURN_NOTIFICATION_ON_OFF_PROCESS_ID = "17";
    public static final String SEND_PAYMENT_RELATED_DETAILS = "18";
    public static final String GET_SUBSCRIPTION_DETAILS_ID = "19";
    public static final String GET_PASSWORDLINK_FOR_EMAIL_ID = "158";
    public static final String MAGIC_LINK_VALIDATION = "53";
    public static final String SIGNUP_FOR_EMAIL_ID = "38";


    //Notification constants
    // AIzaSyBDqS9jlBVnPkLuUjzzxR3ZDNo-ldyESL8 - kiran GCM Key
    public static String GCM_TOKEN = "gcm token";
    public static final String PROJECT_NUMBER = "27750224655";
    public static final String PROJECT_API_KEY = "AIzaSyBDqS9jlBVnPkLuUjzzxR3ZDNo-ldyESL8";
    public static final String PREF_NAME = "Notification";

    public static String USER_ID = null;
    public static String ANDROID_ID = "0";
    public static final String LIST = "list";

    public static String COUNTLY_APP_ID = "5652d4c2457440cc25af9b0e";
    public static String COUNTLY_APP_KEY = "7c81d400cca7968103ec43eb90e2995dd72daf47";




    public static String SERVER_ERROR = "Unable to connect to server, Please Try after some time...";
    public static boolean isANNONYMOUS = false;
    public static String STYLE_3 = "file:///android_asset/Style3.css";
    public static String PAR_STYLE_3 = "file:///android_asset/";
    public static String STYLE_2 = "file:///android_asset/Style2.css";



    // DB Constants
    public static String DB_LOGIN_USER_ID = "userID";
    public static String DB_LOGIN_PUBLICATIONID = "publicationID";
    public static String DB_LOGIN_PUBLISHERID = "publisherID";
    public static String DB_TAG_DATE = "DATE";
    public static String ASSETS_PATH  = "file:///android_asset/";
}
