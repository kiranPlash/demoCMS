package in.plash.trunext.responceObjects;

import java.util.List;

/**
 * Created by Kiran on 11/27/2015.
 */
public class ResSubcription {

    /**
     * period : SINGLE
     * price : 20.0
     * discount : 0.0
     * issuescount : 1
     * subscriptionid : 1
     * subscriptionname : Single Issue
     */

    private List<ListEntity> list;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        private String period;
        private String price;
        private String discount;
        private int issuescount;
        private int subscriptionid;
        private String subscriptionname;

        public void setPeriod(String period) {
            this.period = period;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public void setIssuescount(int issuescount) {
            this.issuescount = issuescount;
        }

        public void setSubscriptionid(int subscriptionid) {
            this.subscriptionid = subscriptionid;
        }

        public void setSubscriptionname(String subscriptionname) {
            this.subscriptionname = subscriptionname;
        }

        public String getPeriod() {
            return period;
        }

        public String getPrice() {
            return price;
        }

        public String getDiscount() {
            return discount;
        }

        public int getIssuescount() {
            return issuescount;
        }

        public int getSubscriptionid() {
            return subscriptionid;
        }

        public String getSubscriptionname() {
            return subscriptionname;
        }
    }
}
