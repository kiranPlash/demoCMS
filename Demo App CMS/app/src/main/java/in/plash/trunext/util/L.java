package in.plash.trunext.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kiran on 10/12/2015.
 */
public class L {

    public static void m(String tag, String message) {
        Log.d("Trunext -"+tag, "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

    public static void T(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
