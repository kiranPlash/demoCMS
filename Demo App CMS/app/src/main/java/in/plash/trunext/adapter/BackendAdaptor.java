package in.plash.trunext.adapter;


import android.content.Context;

import in.plash.trunext.activity.SuperActivity;
import in.plash.trunext.fragments.SuperFragment;
import in.plash.trunext.network.BaseData;
import in.plash.trunext.network.DataNetworkCall;


public class BackendAdaptor {


    Context context;
    SuperActivity superActivity;
    SuperFragment superFragment;
    String string = "";
    DataNetworkCall dataNetworkCall;
    BaseData baseData;

    public BackendAdaptor(SuperActivity superActivity, Context context) {
        this.superActivity = superActivity;
        this.context = context;
    }

    public BackendAdaptor(SuperFragment superFragment, Context context) {
        this.superFragment = superFragment;
        this.context = context;
    }


    public String getNetworkData(Object object) {
        baseData = (BaseData) object;
        dataNetworkCall = new DataNetworkCall(context, baseData);
        dataNetworkCall.setCallback(superActivity != null ? superActivity : superFragment);
        return string;
    }

    public void setPauseWork(boolean pauseWork) {
        if (dataNetworkCall != null) {
            //   dataNetworkCall.setPauseWork(pauseWork);
        }

    }

    public void cancelTask(boolean cancel) {
        if (dataNetworkCall != null) {
           //  dataNetworkCall.onCancelled(cancel);
        }

    }
}
