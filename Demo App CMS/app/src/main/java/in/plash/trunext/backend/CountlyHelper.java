package in.plash.trunext.backend;

import java.util.HashMap;

import in.plash.trunext.util.L;
import ly.count.android.sdk.Countly;

/**
 * Created by Kiran on 11/23/2015.
 */
public class CountlyHelper {


    public static long EndTime, StartTime;


    public static void StartTimer() {
        StartTime = System.currentTimeMillis();
        L.m("coutly", " Start Time " + StartTime);

    }


    public static void StopTimer(int issueID, int categoryID, int articleID) {
        try {

            String EventKey = "Article_Reads";
            int count = 1;

            Double timeSpent = GetTimeSpent();
            L.m("coutly", " timeSpent " + timeSpent);

            //Accepted time range 0 to 30 minutes (1800 sec)
            //ArticleBody check need to be done as to make sure that the full article is avalible
            if (timeSpent > 0 && timeSpent < 1800) {


                HashMap<String, String> segmentation = new HashMap<String, String>();
                segmentation.put("Issue", "I" + issueID);
                segmentation.put("Category", "C" + categoryID);
                segmentation.put("Article", "A" + articleID);
                segmentation.put("TimeSpent", GetTimeSpentSegmentation(timeSpent));

                int sum = timeSpent.intValue();


                Countly.sharedInstance().recordEvent(EventKey, segmentation, count, sum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double GetTimeSpent() {

        try {
            EndTime = System.currentTimeMillis();
            L.m("coutly", " End Time " + EndTime);
            long finalTime = (EndTime - StartTime) / 1000;

            return finalTime;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    public static String GetTimeSpentSegmentation(Double timeSpentInSecond) {

        int time = timeSpentInSecond.intValue();
        int timeSpentInSeconds = time;

        if (timeSpentInSeconds > 0 && timeSpentInSeconds <= 10) return "0-10 seconds";
        if (timeSpentInSeconds >= 11 && timeSpentInSeconds <= 30) return "11-30 seconds";
        if (timeSpentInSeconds >= 31 && timeSpentInSeconds <= 60) return "31-60 seconds";
        int timeSpentInMinutes = timeSpentInSeconds / 60;
        if (timeSpentInMinutes >= 1 && timeSpentInMinutes <= 3) return "1-3 minutes";
        if (timeSpentInMinutes >= 3 && timeSpentInMinutes <= 10) return "3-10 minutes";
        if (timeSpentInMinutes >= 10) return ">10 minutes";

        return "Error";

    }
}
