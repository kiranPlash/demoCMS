package in.plash.trunext.models;

import in.plash.trunext.Constants.Constants;

/**
 * Created by Kiran on 9/21/2015.
 */
public class JsonHeaderReg {
    private String emailaddress = null;
    private String username = null;
    private String userid = Constants.USER_ID;
    private String socialuserid = null;
    private String gender = null;
    private String dateofbirth = null;
    private String accesstoken = null;
    private String usertype = null;
    private int publisherid = Constants.PUBLISHERID;
    private String deviceserialno = Constants.ANDROID_ID;
    private String devicetype = null;
    private String processid = null;
    private int publicationid = Constants.PUBLICATIONID;

    public int getPublicationid() {
        return publicationid;
    }

    public void setPublicationid(int publicationid) {
        this.publicationid = publicationid;
    }

    public String getAccesstoken() {
        return accesstoken;
    }


    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getDeviceserialno() {
        return deviceserialno;
    }

    public void setDeviceserialno(String deviceserialno) {
        this.deviceserialno = deviceserialno;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProcessid() {
        return processid;
    }

    public void setProcessid(String processid) {
        this.processid = processid;
    }

    public int getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(int publisherid) {
        this.publisherid = publisherid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getSocialuserid() {
        return socialuserid;
    }

    public void setSocialuserid(String socialuserid) {
        this.socialuserid = socialuserid;
    }

    @Override
    public String toString() {
        return "jsonheader={" +
                "\"emailaddress\":\"" + emailaddress + "\"" +
                ",\"username\":\"" + username + "\"" +
                ",\"userid\":" + userid +
                ",\"socialuserid\":\"" + socialuserid + "\"" +
                ",\"gender\":\"" + gender + "\"" +
                ",\"dateofbirth\":\"" + dateofbirth + "\"" +
                ",\"accesstoken\":\"" + accesstoken + "\"" +
                ",\"usertype\":\"" + usertype + "\"" +
                ",\"publisherid\":" + publisherid +
                ",\"publicationid\":" + publicationid +
                ",\"deviceserialno\":\"" + deviceserialno + "\"" +
                ",\"devicetype\":\"" + devicetype + "\"" +
                ",\"processid\":\"" + processid + "\"}";
    }
}
