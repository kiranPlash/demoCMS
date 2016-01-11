package in.plash.trunext.Interfaces;

/**
 * Created by Kiran on 11/16/2015.
 */
public interface RequestCallbacks {

    void requestCompleted(String response);
    void errorCallBack(String response);
    void paginationCallBack(String response);
    void bookMarkCallBack(String response);
    void removeBookMarkCallBack(String response);
    void shareCallBack(String response);
    void logoutCallBack(String response);
    void articleDetailCallBack(String response);
    void getAllBookMarkedCallBack(String response);
    void getNotificationCallBack(String response);
    void getPaymentDetailes(String response);
    void getSubscriptionDetailsCallBack(String response);
    void getAllBookMarkedIdsCallBack(String response);
    void getMagicLinkCallBack(String response);
    void getMagicValidationCallBack(String response);
    void signUpRegCallBack(String response);
}
