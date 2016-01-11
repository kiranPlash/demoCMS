package in.plash.trunext.util;

import java.util.List;

/**
 * Created by Kiran on 10/17/2015.
 */
public class test {


    /**
     * Id : 220
     * PublisherId : 1
     * PublicationId : 76
     * IssueName : Hello Evie
     * MagazineCoverImage : https://trunextprodstorage.blob.core.windows.net/dfce66e31452483353432/dfce66e31452483353432.jpg
     * FeedUrl :
     * jsoupparse : 1
     * year : 2015
     * month : 11
     * issuecolour : bc2222
     * summary : Welcome to Trunext
     * isVisible : 1
     * layoutID : 1
     * deleted : 0
     * issueCreator : demolmtlss
     * isfree : 1
     * Flag : 1
     * takecontent : 1
     * Category : [{"Categoryid":980,"CategoryName":"General","publisherid":1,"publicationid":76,"issueid":220,"flag":1}]
     */

    private List<ListEntity> list;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        private int Id;
        private int PublisherId;
        private int PublicationId;
        private String IssueName;
        private String MagazineCoverImage;
        private String FeedUrl;
        private int jsoupparse;
        private int year;
        private int month;
        private String issuecolour;
        private String summary;
        private String isVisible;
        private int layoutID;
        private int deleted;
        private String issueCreator;
        private int isfree;
        private int Flag;
        private int takecontent;
        /**
         * Categoryid : 980
         * CategoryName : General
         * publisherid : 1
         * publicationid : 76
         * issueid : 220
         * flag : 1
         */

        private List<CategoryEntity> Category;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setPublisherId(int PublisherId) {
            this.PublisherId = PublisherId;
        }

        public void setPublicationId(int PublicationId) {
            this.PublicationId = PublicationId;
        }

        public void setIssueName(String IssueName) {
            this.IssueName = IssueName;
        }

        public void setMagazineCoverImage(String MagazineCoverImage) {
            this.MagazineCoverImage = MagazineCoverImage;
        }

        public void setFeedUrl(String FeedUrl) {
            this.FeedUrl = FeedUrl;
        }

        public void setJsoupparse(int jsoupparse) {
            this.jsoupparse = jsoupparse;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public void setIssuecolour(String issuecolour) {
            this.issuecolour = issuecolour;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setIsVisible(String isVisible) {
            this.isVisible = isVisible;
        }

        public void setLayoutID(int layoutID) {
            this.layoutID = layoutID;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public void setIssueCreator(String issueCreator) {
            this.issueCreator = issueCreator;
        }

        public void setIsfree(int isfree) {
            this.isfree = isfree;
        }

        public void setFlag(int Flag) {
            this.Flag = Flag;
        }

        public void setTakecontent(int takecontent) {
            this.takecontent = takecontent;
        }

        public void setCategory(List<CategoryEntity> Category) {
            this.Category = Category;
        }

        public int getId() {
            return Id;
        }

        public int getPublisherId() {
            return PublisherId;
        }

        public int getPublicationId() {
            return PublicationId;
        }

        public String getIssueName() {
            return IssueName;
        }

        public String getMagazineCoverImage() {
            return MagazineCoverImage;
        }

        public String getFeedUrl() {
            return FeedUrl;
        }

        public int getJsoupparse() {
            return jsoupparse;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public String getIssuecolour() {
            return issuecolour;
        }

        public String getSummary() {
            return summary;
        }

        public String getIsVisible() {
            return isVisible;
        }

        public int getLayoutID() {
            return layoutID;
        }

        public int getDeleted() {
            return deleted;
        }

        public String getIssueCreator() {
            return issueCreator;
        }

        public int getIsfree() {
            return isfree;
        }

        public int getFlag() {
            return Flag;
        }

        public int getTakecontent() {
            return takecontent;
        }

        public List<CategoryEntity> getCategory() {
            return Category;
        }

        public static class CategoryEntity {
            private int Categoryid;
            private String CategoryName;
            private int publisherid;
            private int publicationid;
            private int issueid;
            private int flag;

            public void setCategoryid(int Categoryid) {
                this.Categoryid = Categoryid;
            }

            public void setCategoryName(String CategoryName) {
                this.CategoryName = CategoryName;
            }

            public void setPublisherid(int publisherid) {
                this.publisherid = publisherid;
            }

            public void setPublicationid(int publicationid) {
                this.publicationid = publicationid;
            }

            public void setIssueid(int issueid) {
                this.issueid = issueid;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getCategoryid() {
                return Categoryid;
            }

            public String getCategoryName() {
                return CategoryName;
            }

            public int getPublisherid() {
                return publisherid;
            }

            public int getPublicationid() {
                return publicationid;
            }

            public int getIssueid() {
                return issueid;
            }

            public int getFlag() {
                return flag;
            }
        }
    }
}
