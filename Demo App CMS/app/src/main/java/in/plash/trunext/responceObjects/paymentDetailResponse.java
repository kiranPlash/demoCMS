package in.plash.trunext.responceObjects;

/**
 * Created by Kiran on 11/28/2015.
 */
public class paymentDetailResponse {

    /**
     * paymenturl : https://test.payu.in/_payment
     * key : gtKFFx
     * txnid : f29dca93-91fa-4a2f-9f91-1c3510
     * amount : 20.0
     * productinfo : Digital Article
     * firstname : guest
     * email : kiran.kik@gmail.com
     * phone : 1234567890
     * surl : http://trunxtdev.cloudapp.net/trunext/notifypayment.htm
     * furl : http://trunxtdev.cloudapp.net/trunext/notifyfailedpayment.htm
     * PaymentHash : cf2ab2cdbd7b99e151a6ce66ee8d9a68ca950b851ed659bfeada960415fed2c8d2f59d8fef0c1e01019b45fe4255cc840853317c46794b92020ed21659a6e596
     * pg : NB,CC,DC
     */

    private JsonobjectEntity jsonobject;

    public void setJsonobject(JsonobjectEntity jsonobject) {
        this.jsonobject = jsonobject;
    }

    public JsonobjectEntity getJsonobject() {
        return jsonobject;
    }

    public static class JsonobjectEntity {
        private String paymenturl;
        private String key;
        private String txnid;
        private String amount;
        private String productinfo;
        private String firstname;
        private String email;
        private String phone;
        private String surl;
        private String furl;
        private String PaymentHash;
        private String pg;
        private String PaymentRelatedDetailsForMobileSdkHash;

        public String getPaymentRelatedDetailsForMobileSdkHash() {
            return PaymentRelatedDetailsForMobileSdkHash;
        }

        public void setPaymentRelatedDetailsForMobileSdkHash(String paymentRelatedDetailsForMobileSdkHash) {
            this.PaymentRelatedDetailsForMobileSdkHash = paymentRelatedDetailsForMobileSdkHash;
        }

        public void setPaymenturl(String paymenturl) {
            this.paymenturl = paymenturl;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setTxnid(String txnid) {
            this.txnid = txnid;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setProductinfo(String productinfo) {
            this.productinfo = productinfo;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSurl(String surl) {
            this.surl = surl;
        }

        public void setFurl(String furl) {
            this.furl = furl;
        }

        public void setPaymentHash(String paymentHash) {
            this.PaymentHash = paymentHash;
        }

        public void setPg(String pg) {
            this.pg = pg;
        }

        public String getPaymenturl() {
            return paymenturl;
        }

        public String getKey() {
            return key;
        }

        public String getTxnid() {
            return txnid;
        }

        public String getAmount() {
            return amount;
        }

        public String getProductinfo() {
            return productinfo;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getSurl() {
            return surl;
        }

        public String getFurl() {
            return furl;
        }

        public String getPaymentHash() {
            return PaymentHash;
        }

        public String getPg() {
            return pg;
        }
    }
}
