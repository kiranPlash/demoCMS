package in.plash.trunext.responceObjects;

/**
 * Created by Kiran on 11/17/2015.
 */
public class LoginResponse {

    /**
     * id : 643
     * UserName : null
     * avatar : https://upload.wikimedia.org/wikipedia/en/b/b1/Portrait_placeholder.png
     * latitude :
     * longitude :
     * city :
     * country :
     * joindate : Nov 19, 2015 1:47:49 PM
     * DeviceSerialNo : 62fdd4610962c80a
     * DeviceType : Android 22
     * SocialUserId :
     * anonymoususerid : 62fdd4610962c80a
     * AccessToken :
     * EmailAddress : null
     * gender :
     */

    private JsonobjectEntity jsonobject;

    public void setJsonobject(JsonobjectEntity jsonobject) {
        this.jsonobject = jsonobject;
    }

    public JsonobjectEntity getJsonobject() {
        return jsonobject;
    }

    public static class JsonobjectEntity {
        private int id;
        private String UserName;
        private String avatar;
        private String latitude;
        private String longitude;
        private String city;
        private String country;
        private String joindate;
        private String DeviceSerialNo;
        private String DeviceType;
        private String SocialUserId;
        private String anonymoususerid;
        private String AccessToken;
        private String EmailAddress;
        private String gender;

        public void setId(int id) {
            this.id = id;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setJoindate(String joindate) {
            this.joindate = joindate;
        }

        public void setDeviceSerialNo(String DeviceSerialNo) {
            this.DeviceSerialNo = DeviceSerialNo;
        }

        public void setDeviceType(String DeviceType) {
            this.DeviceType = DeviceType;
        }

        public void setSocialUserId(String SocialUserId) {
            this.SocialUserId = SocialUserId;
        }

        public void setAnonymoususerid(String anonymoususerid) {
            this.anonymoususerid = anonymoususerid;
        }

        public void setAccessToken(String AccessToken) {
            this.AccessToken = AccessToken;
        }

        public void setEmailAddress(String EmailAddress) {
            this.EmailAddress = EmailAddress;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public String getUserName() {
            return UserName;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getJoindate() {
            return joindate;
        }

        public String getDeviceSerialNo() {
            return DeviceSerialNo;
        }

        public String getDeviceType() {
            return DeviceType;
        }

        public String getSocialUserId() {
            return SocialUserId;
        }

        public String getAnonymoususerid() {
            return anonymoususerid;
        }

        public String getAccessToken() {
            return AccessToken;
        }

        public String getEmailAddress() {
            return EmailAddress;
        }

        public String getGender() {
            return gender;
        }
    }
}
