package in.plash.trunext.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.activity.MainActivity;
import in.plash.trunext.adapter.BackendAdaptor;
import in.plash.trunext.adapter.DemoIssueAdapter;
import in.plash.trunext.adapter.ForbesIssueAdapter;
import in.plash.trunext.backend.DataHelper;
import in.plash.trunext.models.JsonBodyReq;
import in.plash.trunext.models.JsonHeaderReg;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.responceObjects.ResponsePublication;
import in.plash.trunext.util.L;
import in.plash.trunext.util.Utilities;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by Kiran on 10/15/2015.
 */
public class IssueFeedFragment extends SuperFragment  implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
   // private ForbesIssueAdapter mAdapter;
    private DemoIssueAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ResponsePublication responsePublicationObj;
    View view;
    private static final String BUNDLE_RECYCLER_LAYOUT = "IssuesState";
    private Bundle mListState;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static int SPLASH_TIME_OUT = 2000;

    public IssueFeedFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.issue_recyclerview_lyt, container, false);


        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            MainActivity.setHomeButtonVisibility(false);

        }
        MainActivity.setToolBarTitle("");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.article_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        responsePublicationObj = DataHelper.getPublicationData();
        if (responsePublicationObj != null) {
            setRecyclerViewAdapter();
        } else {
            if (Utilities.isInternetConnectivityAvailable(getActivity())) {
                doPublicationNetworkCall();
            } else {
                L.T(getActivity(), "Please Connect to Internet");
            }

        }
    }

    public void doPublicationNetworkCall() {
        BaseData baseData = new BaseData();
        baseData.setApi(Constants.API_CATEGORY);
        JsonHeaderReg headerReg = new JsonHeaderReg();
        JsonBodyReq bodyReq = new JsonBodyReq();
        headerReg.setProcessid(Constants.GET_ISSUES_PUBLISHER_PROCESS_ID);
        headerReg.setUserid(Constants.USER_ID);
        baseData.setHeaderObj(headerReg);
        baseData.setRequestBody(bodyReq);
        BackendAdaptor backendAdaptor = new BackendAdaptor(this, getActivity());
        backendAdaptor.getNetworkData(baseData);
        Utilities.setProgressBar(getActivity());

    }

    @Override
    public void requestCompleted(String response) {

        Utilities.CancelProgressBar();
        DataHelper.UpdatePublicationDB(response);
        responsePublicationObj = DataHelper.getPublicationData();
        if (responsePublicationObj != null) {
            setRecyclerViewAdapter();
        }
    }


    @Override
    public void errorCallBack(String response) {
        L.m("Http Error: ", "-->" + response);
        Utilities.CancelProgressBar();
        responsePublicationObj = DataHelper.getPublicationData();
        if (responsePublicationObj != null) {
            setRecyclerViewAdapter();
        } else {
            L.T(getActivity(), Constants.SERVER_ERROR);
        }
    }


    private void setRecyclerViewAdapter() {
        // specify an adapter
      /* Use for pagination
      mAdapter = new ForbesIssueAdapter(getActivity(), responsePublicationObj.getList(), mRecyclerView); */

        mRecyclerView.setHasFixedSize(true);
       // mAdapter = new ForbesIssueAdapter(responsePublicationObj.getList(), getActivity());
        mAdapter = new DemoIssueAdapter(responsePublicationObj.getList(), getActivity());
        restoreInstanceState();


// Animation
       /* AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setDuration(300);
        mRecyclerView.setAdapter(alphaAdapter);*/


           mRecyclerView.setAdapter(mAdapter);

    }

    private void restoreInstanceState() {
        if (mListState != null) {
            Parcelable savedRecyclerLayoutState = mListState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mListState = new Bundle();
        mListState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onStop() {
        super.onStop();
        mRecyclerView.clearOnScrollListeners();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshList();
    }

    public void refreshList() {

        doPublicationNetworkCall();


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
