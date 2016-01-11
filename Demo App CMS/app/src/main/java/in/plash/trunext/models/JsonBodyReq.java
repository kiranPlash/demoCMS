package in.plash.trunext.models;

import java.io.Serializable;

import in.plash.trunext.Constants.Constants;

/**
 * Created by Kiran on 9/21/2015.
 */
public class JsonBodyReq implements Serializable {
    private int issueid;
    private int beforeid;
    private int afterid;
    private int publicationid = Constants.PUBLICATIONID;
    private int categoryid;
    private int Id;
    private int articleId;
    private String event = null;
    private int eventId;
    private String googleId = null;
    private String emailid = null;
    private String name = null;
    private String phoneno = null;
    private String appleid;
    private String windowsuri;
    private String amount;
    private int subscriptionid;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(int subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }


    public String getAppleid() {
        return appleid;
    }

    public void setAppleid(String appleid) {
        this.appleid = appleid;
    }

    public String getWindowsuri() {
        return windowsuri;
    }

    public void setWindowsuri(String windowsuri) {
        this.windowsuri = windowsuri;
    }

    public int getIssueid() {
        return issueid;
    }

    public void setIssueid(int issueid) {
        this.issueid = issueid;
    }

    public int getBeforeid() {
        return beforeid;
    }

    public void setBeforeid(int beforeid) {
        this.beforeid = beforeid;
    }

    public int getAfterid() {
        return afterid;
    }

    public void setAfterid(int afterid) {
        this.afterid = afterid;
    }

    public int getPublicationid() {
        return publicationid;
    }

    public void setPublicationid(int publicationid) {
        this.publicationid = publicationid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    //IMP read this

    /**
     * Since the json accepts only string
     * it is modified like below to parse
     */

    public static String getJson() {
        return null;
    }


    @Override
    public String toString() {
        return "&jsonbody={" +
                "\"issueid\":" + issueid +
                ",\"beforeid\":" + beforeid +
                ",\"afterid\":" + afterid +
                ",\"publicationid\":" + publicationid +
                ",\"categoryid\":" + categoryid +
                ",\"event\":\"" + event + "\"" +
                ",\"eventid\":" + eventId +
                ",\"Id\":" + Id +
                ",\"articleId\":" + articleId +
                ",\"googleid\":\"" + googleId + "\"" +
                ",\"appleid\":\"" + appleid + "\"" +
                ",\"windowsuri\":\"" + windowsuri + "\"" +
                ",\"name\":" + name +
                ",\"emailid\":" + emailid +
                ",\"phoneno\":" + phoneno + "}";
    }
}
