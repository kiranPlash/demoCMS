package in.plash.trunext.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.Interfaces.OnLoadMoreListener;
import in.plash.trunext.R;
import in.plash.trunext.activity.MainActivity;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.adapter.forbesArticleAdapter;
import in.plash.trunext.backend.BookmarkHelper;
import in.plash.trunext.backend.DataHelper;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.ResponseArticles;
import in.plash.trunext.responceObjects.ResponsePublication;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Utilities;

/**
 * Created by Kiran on 10/15/2015.
 */
public class ArticleFeedFragment extends SuperFragment implements SwipeRefreshLayout.OnRefreshListener {

    static int publicationID, issueID, articleID, categoryID;
    public static boolean paginationFlag = true;
    static ResponseArticles responseArticlesObj;
    ResponseArticles responseArticlesObjPagination;
    View view;
    private forbesArticleAdapter forbesArticleAdapter;
    RecyclerView mArticleRecyclerView;
    TextView emptyView;
    BackendAdaptor backendAdaptor = new BackendAdaptor(this, getActivity());
    private List<ResponseArticles.ListEntity> responseArticlesList = new ArrayList<>();
    private static final String BUNDLE_RECYCLER_LAYOUT = "ArticleState";
    Bundle mListState;
    protected Handler handler;
    public static int LIST_SIZE;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static int SPLASH_TIME_OUT = 2000;


    public ArticleFeedFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  showProgressBar(false);
        view = inflater.inflate(R.layout.issue_recyclerview_lyt, container, false);
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            MainActivity.setHomeButtonVisibility(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        MainActivity.setToolBarTitle("");

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  List<ResponseArticles.ListEntity> responseArticlesList = new ArrayList<>();
        handler = new Handler();
        mArticleRecyclerView = (RecyclerView) view.findViewById(R.id.article_recycler_view);
        emptyView = (TextView) view.findViewById(R.id.empty_view);


        if (getArguments() != null) {
            if (getArguments().containsKey("ResponsePublication")) {
                paginationFlag = true;
                ResponsePublication.ListEntity publisherObj = (ResponsePublication.ListEntity) getArguments().getSerializable("ResponsePublication");
                publicationID = Constants.PUBLICATIONID;
                if (publisherObj != null) {
                    issueID = publisherObj.getId();
                    categoryID = publisherObj.getCategory().get(0).getCategoryid();
                }

                responseArticlesList.clear();
                responseArticlesList = DataHelper.getArticlesData(categoryID);


                try {
                    LIST_SIZE = responseArticlesList.size(); // Swipeview
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (responseArticlesList != null) {
                    setArticleListAdapter(responseArticlesList);
                } else {

                    if (Utilities.isInternetConnectivityAvailable(getActivity())) {
                        doArticleNetworkCall(categoryID);
                    } else {
                        L.T(getActivity(), "Please Connect to Internet");
                    }

                }


            } else if (getArguments().containsKey("DrawerCall")) {
                {
                    int[] issueCatgoryID = getArguments().getIntArray("DrawerCall");
                    publicationID = Constants.PUBLICATIONID;
                    if (issueCatgoryID != null) {
                        issueID = issueCatgoryID[0];
                        categoryID = issueCatgoryID[1];
                    }
                    // dataBaseArticlesList = new Select().from(DataBaseArticlesList.class).where("flag = ?", "NEW").executeSingle();
                    if (Utilities.isInternetConnectivityAvailable(getActivity())) {
                        doArticleNetworkCall(categoryID);
                    } else {
                        L.T(getActivity(), "Please Connect to Internet");
                    }
                }
            } else if (getArguments().containsKey("bookMark")) {
                paginationFlag = false;
                InitialiseBookmarkViews();
                // showProgressBar(false);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mListState = new Bundle();
            mListState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mArticleRecyclerView.getLayoutManager().onSaveInstanceState());
            //  L.m("States: ", " onPause " + mListState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitialiseBookmarkViews() {

        responseArticlesObj = BookmarkHelper.getBookMarkedData();
        if (responseArticlesObj != null) {
            // List<ResponseArticles.ListEntity> responseArticlesList = new ArrayList<>();
            responseArticlesList.clear();
            responseArticlesList.addAll(responseArticlesObj.getList());


            try {
                LIST_SIZE = responseArticlesList.size(); // Swipeview
            } catch (Exception e) {
                e.printStackTrace();
            }


            setArticleListAdapter(responseArticlesList);
        } else {
            L.T(getActivity(), "No Articles BookMarked");
        }

    }

    public void doArticleNetworkCall(int categoryID) {

        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_GET_ARTICLES);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.GET_ARTICLE_CATEGORY_PROCESS_ID);
        bodyReq.setIssueid(issueID);
        bodyReq.setCategoryid(categoryID);

        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);
        Utilities.setProgressBar(getActivity());
    }

    @Override
    public void requestCompleted(String response) {
        L.m("Article Network ", "Response " + response);
        DataHelper.UpdateArticleDB(response, categoryID);

        responseArticlesList = new ArrayList();
        responseArticlesList = DataHelper.getArticlesData(categoryID);


        try {
            LIST_SIZE = responseArticlesList.size(); // Swipeview
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utilities.CancelProgressBar();
        if (responseArticlesList != null)
            setArticleListAdapter(responseArticlesList);

    }

    @Override
    public void errorCallBack(String response) {
        L.m("Http Error: ", "-->" + response);
        Utilities.CancelProgressBar();
        responseArticlesList = DataHelper.getArticlesData(categoryID);
        if (responseArticlesList != null) {
            setArticleListAdapter(responseArticlesList);
        } else {
            L.T(getActivity(), "Unable to connect to server, Please Try after some time...");
        }
    }


    private void setArticleListAdapter(final List<ResponseArticles.ListEntity> responseArticlesList) {

        mArticleRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mArticleRecyclerView.setLayoutManager(mLayoutManager);
        forbesArticleAdapter = new forbesArticleAdapter(getActivity(), responseArticlesList, mArticleRecyclerView);

// Animation
        //   mArticleRecyclerView.setAdapter(new AlphaInAnimationAdapter(forbesArticleAdapter));
      /*  AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(forbesArticleAdapter);
        alphaAdapter.setDuration(5000);
        mArticleRecyclerView.setAdapter(alphaAdapter);*/

        mArticleRecyclerView.setAdapter(forbesArticleAdapter);

        if (responseArticlesList.isEmpty()) {
            mArticleRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            mArticleRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        if (paginationFlag) {
            setAdapterPaginationListner();       // Set adapter Pagination Listener for Articles
        }

        setonClickListener();         // Set click Listener for Articles


        restoreInstanceState();         // Set state restore


    }

    private void setonClickListener() {
        forbesArticleAdapter.setOnItemClickListener(new forbesArticleAdapter.ClickArticleListener() {
            @Override
            public void onItemClick(int position, View v, int id) {

                // Swipe View Code
                List articleIDs = new ArrayList<>();

                try {
                    for (int i = 0; i < responseArticlesList.size(); i++) {
                        long j = responseArticlesList.get(i).getId();
                        articleIDs.add(j);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                L.m("array", " articleIDs " + articleIDs.toString());
                // Swipe View Code end

                Bundle bundle = new Bundle();

                articleID = id;
                int[] jsonBodyPrams = {issueID, categoryID, articleID};
                bundle.putIntArray("articleRequestDetails", jsonBodyPrams);
                bundle.putInt("SIZE", LIST_SIZE); // Swipeview
                bundle.putIntegerArrayList("ARTICLEIDs", (ArrayList<Integer>) articleIDs);

                Fragment fragment = new SwipeFragment();
                //    Fragment fragment = new ArticleBodyFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (fragment.isAdded()) {
                    transaction.show(fragment);
                } else {
                    //  transaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                }
                transaction.commit();
            }
        });
    }

    private void setAdapterPaginationListner() {
        forbesArticleAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int afterID) {
                doPageCall(afterID);
                //add null , so the adapter will check view_type and show progress bar at bottom
                responseArticlesList.add(null);
                forbesArticleAdapter.notifyItemInserted(responseArticlesList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        responseArticlesList.remove(responseArticlesList.size() - 1);
                        forbesArticleAdapter.notifyItemRemoved(responseArticlesList.size());

                        if (responseArticlesObjPagination != null) {
                            responseArticlesList.addAll(responseArticlesObjPagination.getList());
                            LIST_SIZE = responseArticlesList.size(); // Swipeview
                            L.m("ArrayData", "Updated " + responseArticlesList.size());
                            forbesArticleAdapter.notifyItemInserted(responseArticlesList.size());
                            forbesArticleAdapter.setLoaded();

                        }
                    }
                }, 3000);
            }
        });
    }

    private void restoreInstanceState() {
        if (mListState != null) {
            Parcelable savedRecyclerLayoutState = mListState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mArticleRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            L.m("State", "Called ");
        }
    }

    public void doPageCall(int id) {
        doArticlePagiNetworkCall(id, categoryID);
    }


    public void doArticlePagiNetworkCall(int id, int categoryID) {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_NEXT_SET_OF_ARTICLES);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.GET_ARTICLE_CATEGORY_PROCESS_ID);
        bodyReq.setIssueid(issueID);
        bodyReq.setCategoryid(categoryID);
        bodyReq.setBeforeid(0);
        bodyReq.setAfterid(id);

        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        backendAdaptor.getNetworkData(baseData);
    }

    @Override
    public void paginationCallBack(String response) {
        responseArticlesObjPagination = DataHelper.paginationDataUpdateDB(response, categoryID);
    }


    @Override
    public void onStop() {
        super.onStop();
        L.m("States: ", " onStop ");
        backendAdaptor.cancelTask(true);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshList();
    }

    public void refreshList() {
        // responseArticlesList.clear();
        doArticleNetworkCall(categoryID);
        forbesArticleAdapter.notifyDataSetChanged();

        // Remove this after getting data
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                swipeRefreshLayout.setRefreshing(false);
            }
        }, SPLASH_TIME_OUT);

        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false


    }
}
