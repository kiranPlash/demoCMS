package in.plash.trunext.backend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import in.plash.trunext.util.L;

/**
 * Created by Kiran on 11/26/2015.
 */
public class NavDrawerHelper {

    public static void sendEmail(Context context) {
        L.m("Send email", "");
        String[] TO = {"care@demo.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, ");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //finish();
            L.m("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            L.t(context, "There is no email client installed.");

        }
    }
}
