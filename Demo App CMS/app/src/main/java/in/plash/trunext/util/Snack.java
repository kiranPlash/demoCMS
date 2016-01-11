package in.plash.trunext.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import in.plash.trunext.R;


/**
 * Created by Madhur on 15/10/2015.
 */
public final class Snack {

    public static void showLong(Context mContext, String str) {
        Activity mActivity = (Activity) mContext;
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG).setAction("Action", null);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(mContext.getResources().getColor(R.color.snack_bar_blue));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public static void showShort(Context mContext, String str) {
        Activity mActivity = (Activity) mContext;
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), str, Snackbar.LENGTH_SHORT).setAction("Action", null);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(mContext.getResources().getColor(R.color.snack_bar_blue));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void show(Context mContext, String str, int background, int text) {
        Activity mActivity = (Activity) mContext;
        Snackbar snackbar = Snackbar.make(mActivity.findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG).setAction("Action", null);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(background);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(text);
        snackbar.show();
    }

}
