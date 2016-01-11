package in.plash.trunext.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.backend.BookmarkHelper;
import in.plash.trunext.backend.CountlyHelper;
import in.plash.trunext.backend.DataHelper;
import in.plash.trunext.backend.NotificationHelper;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.ResponseArticleBody;
import in.plash.trunext.responceObjects.ResponseBookMarkIds;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Snack;
import in.plash.trunext.util.Utilities;


public class ArticleBodyFragment extends SuperFragment {

    public static int issueID, categoryID, articleID;
    ResponseArticleBody articleBodyObj;
    ImageView bookmark;
    ImageView imgShare;
    Gson gson;
    View view;
    int count = 0;
    Boolean flag = false;
    ResponseBookMarkIds bookMarkIds;
    WebView webView;
    BookmarkHelper bookmarkHelper;
    NotificationHelper notificationHelper;
    public BackendAdaptor backendAdaptor;
    Context mContext;
    private final String TEMP_1 = "temp1.html", TEMP_2 = "temp2.html", TEMP_3 = "temp3.html", TEMP_4 = "temp4.html", TEMP_5 = "temp5.html",TEMP_6 = "temp6.html";

    public ArticleBodyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_body, container, false);
        // MainActivity.setToolBarTitle("");
        mContext = getActivity();
        bookmarkHelper = new BookmarkHelper();
        bookmark = (ImageView) view.findViewById(R.id.img_book_mark);
        imgShare = (ImageView) view.findViewById(R.id.img_share);

        notificationHelper = new NotificationHelper();
        if (getArguments() != null) {
            int[] jsonBodyPrams = getArguments().getIntArray("articleRequestDetails");
            if (jsonBodyPrams != null) {
                issueID = jsonBodyPrams[0];
                categoryID = jsonBodyPrams[1];
                articleID = jsonBodyPrams[2];

                if (Utilities.isInternetConnectivityAvailable(getActivity())) {
                    doArticleBodyNetworkCall(issueID, categoryID, articleID);
                } else {
                    L.T(getActivity(), "Please Connect to Internet");
                }
            }
        }

        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        CountlyHelper.StartTimer();


        bookmarkHelper.doBookMarksIdsCall();

        bookMarkIds = BookmarkHelper.getBookMarkIds(); //doBookMarksListCall();
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    if (count % 2 == 0) {
                        bookmark.setImageResource(R.drawable.ic_deselected_bookmarks);
                        count++;
                        bookmarkHelper.doBookMarkRemoveApiCall(articleID, issueID);
                        Snack.showShort(getActivity(), "Bookmark Removed SUCCESSFUL");
                    } else {
                        bookmark.setImageResource(R.drawable.ic_selected_bookmark);
                        count++;
                        bookmarkHelper.doBookMarkSelectedApiCall(articleID, issueID);
                        Snack.showShort(getActivity(), "Bookmark Added SUCCESSFUL");
                    }
                } else {
                    if (count % 2 == 0) {
                        bookmark.setImageResource(R.drawable.ic_selected_bookmark);
                        count++;
                        bookmarkHelper.doBookMarkSelectedApiCall(articleID, issueID);
                        Snack.showShort(getActivity(), "Bookmark Added SUCCESSFUL");
                    } else {
                        bookmark.setImageResource(R.drawable.ic_deselected_bookmarks);
                        count++;
                        bookmarkHelper.doBookMarkRemoveApiCall(articleID, issueID);
                        Snack.showShort(getActivity(), "Bookmark Removed SUCCESSFUL");
                    }
                }

            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper.shareApp(getActivity(), articleBodyObj.getJsonobject().getLink());
            }
        });
    }


    private void doArticleBodyNetworkCall(int issueID, int categoryID, int articleID) {
        // Utilities.setProgressBar(getActivity());
        showProgressBar(true);
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_ARTICLE_DETAIL);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.GET_ARTICLE_DETAILS_PROCESS_ID);
        bodyReq.setIssueid(issueID);
        bodyReq.setCategoryid(categoryID);
        bodyReq.setArticleId(articleID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor = new BackendAdaptor(this, getActivity());
        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void articleDetailCallBack(String response) {
        L.m("Article Details ", "Successful " + response);
        //   Utilities.CancelProgressBar();
        showProgressBar(false);
        gson = new Gson();
        articleBodyObj = gson.fromJson(response, ResponseArticleBody.class);
        flag = checkIsBookMarked(articleBodyObj.getJsonobject().getId());
        //     MainActivity.setToolBarTitle(articleBodyObj.getJsonobject().getArticleHeadline());
        initializeWebView(articleBodyObj, flag);
    }

    @Override
    public void errorCallBack(String response) {
        //  Utilities.CancelProgressBar();
        showProgressBar(false);
        try {
            L.T(getActivity(), "" + Constants.SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        L.m("Http Error: ", "-->" + response);
    }

    private Boolean checkIsBookMarked(int id) {
        if (bookMarkIds != null) {
            for (int j = 0; j < bookMarkIds.getList().size(); j++) {

                if (bookMarkIds.getList().get(j) == id) {
                    return true;
                }
            }
        }
        return false;
    }


    private void initializeWebView(ResponseArticleBody articleBodyObj, Boolean bookmarked) {
        //showProgressBar(false);
        webView = (WebView) view.findViewById(R.id.web_view);
        if (bookmarked) {
            bookmark.setImageResource(R.drawable.ic_selected_bookmark);
        }
        webView.setHapticFeedbackEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);

        String URL = "";
        if (articleBodyObj.getJsonobject().getTemplatesUrl() != null) {
            String tempHTML = getTemplateID(articleBodyObj.getJsonobject().getTemplatesUrl());
            URL = DataHelper.getWebData(tempHTML, articleBodyObj, getActivity());
        } else {
            URL = DataHelper.getWebData(TEMP_1, articleBodyObj, getActivity());
        }

        webView.loadDataWithBaseURL(Constants.ASSETS_PATH, URL, "text/html", "UTF-8", "");


        //   webView.loadDataWithBaseURL("file:///android_asset/Style2.css", URL, "text/html", "UTF-8", "");


        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
    }

    private String getTemplateID(String BodyObj) {
        switch (BodyObj) {
            case "1":
                return TEMP_1;
            case "2":
                return TEMP_2;
            case "3":
                return TEMP_3;
            case "4":
                return TEMP_4;
            case "5":
                return TEMP_5;
            case "6":
                return TEMP_6;
            default:
                return TEMP_1;
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        CountlyHelper.StopTimer(issueID, categoryID, articleID);
    }

    public void showProgressBar(boolean b) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linear_login_progress_articlebody);
        if (b == true) {
            layout.setVisibility(View.VISIBLE);


        } else {
            layout.setVisibility(View.GONE);
        }
    }

}


