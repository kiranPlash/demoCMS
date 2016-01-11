package in.plash.trunext.responceObjects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kiran on 9/18/2015.
 */
public class ResponsePublication implements Serializable {


    private List<ListEntity> list;

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity implements Serializable {
        /**
         * {
         * "Id": 43,
         * "PublisherId": 20,
         * "PublicationId": 22,
         * "identifier": "",
         * "IssueName": "Crunch",
         * "MagazineCoverImage": "http://businessforall.in/images/magazine/BFA-Sept15-Cover1.png",
         * "FeedUrl": "http://deathtothestockphoto.com/feed/",
         * "FiveFiltersPass": 0,
         * "RssReader": "",
         * "jsoupparse": 1,
         * "price": 0.0,
         * "year": 2015,
         * "month": 10,
         * "isfree": 1,
         * "Flag": 0,
         * "takecontent": 1,
         * "Category": [
         * {
         * "Categoryid": 488,
         * "identifier": "24f5dc39-5e2d-4955-955e-204b3386a1d6",
         * "CategoryName": "Pack",
         * "publisherid": 20,
         * "publicationid": 22,
         * "issueid": 43,
         * "flag": 1,
         * "CategoryThumbImageUrl": "",
         * "CategoryMainImageUrl": ""
         * },  }
         * ],
         * "ispurchased": false
         * },
         */

        private int Id;
        private int PublisherId;
        private int PublicationId;
        private String identifier;
        private String IssueName;
        private String MagazineCoverImage;
        private String FeedUrl;
        private int FiveFiltersPass;
        private String RssReader;
        private int jsoupparse;
        private int price;
        private int Flag;
        boolean ispurchased;
        boolean isfreetest;
        //  boolean isfree;
        private long year;
        private long month;
        private int takecontent;
        private int layoutID;

        public int getLayoutID() {
            return layoutID;
        }

        public void setLayoutID(int layoutID) {
            this.layoutID = layoutID;
        }

        public String getIssuepubdate() {
            return issuepubdate;
        }

        public void setIssuepubdate(String issuepubdate) {
            this.issuepubdate = issuepubdate;
        }

        private String issuecolour;
        private String summary;
        private List<CategoryEntity> Category;
        private String issuepubdate;


        public boolean ispurchased() {
            return ispurchased;
        }

        public void setIspurchased(boolean ispurchased) {
            this.ispurchased = ispurchased;
        }

     /*   public boolean isfree() {
            return isfree;
        }

        public void setIsfree(boolean isfree) {
            this.isfree = isfree;
        }*/

        public boolean isfreetest() {
            return isfreetest;
        }

        public void setIsfreetest(boolean isfreetest) {
            this.isfreetest = isfreetest;
        }

        public long getYear() {
            return year;
        }

        public void setYear(long year) {
            this.year = year;
        }

        public long getMonth() {
            return month;
        }

        public void setMonth(long month) {
            this.month = month;
        }

        public String getIssuecolour() {
            return issuecolour;
        }

        public void setIssuecolour(String issuecolour) {
            this.issuecolour = issuecolour;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setPublisherId(int PublisherId) {
            this.PublisherId = PublisherId;
        }

        public void setPublicationId(int PublicationId) {
            this.PublicationId = PublicationId;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
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

        public void setFiveFiltersPass(int FiveFiltersPass) {
            this.FiveFiltersPass = FiveFiltersPass;
        }

        public void setRssReader(String RssReader) {
            this.RssReader = RssReader;
        }

        public void setJsoupparse(int jsoupparse) {
            this.jsoupparse = jsoupparse;
        }

        public void setPrice(int price) {
            this.price = price;
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

        public String getIdentifier() {
            return identifier;
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

        public int getFiveFiltersPass() {
            return FiveFiltersPass;
        }

        public String getRssReader() {
            return RssReader;
        }

        public int getJsoupparse() {
            return jsoupparse;
        }

        public int getPrice() {
            return price;
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

        public static class CategoryEntity implements Serializable {
            /**
             * Categoryid : 291
             * identifier : 1cbb3661-ba85-4c61-bc5d-3939a8632827
             * CategoryName : Not Just Kidding
             * publisherid : 2
             * publicationid : 5
             * issueid : 14
             * flag : 1
             * CategoryThumbImageUrl :
             * CategoryMainImageUrl :
             */

            private int Categoryid;
            private String identifier;
            private String CategoryName;
            private int publisherid;
            private int publicationid;
            private int issueid;
            private int flag;
            private String CategoryThumbImageUrl;
            private String CategoryMainImageUrl;

            public void setCategoryid(int Categoryid) {
                this.Categoryid = Categoryid;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
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

            public void setCategoryThumbImageUrl(String CategoryThumbImageUrl) {
                this.CategoryThumbImageUrl = CategoryThumbImageUrl;
            }

            public void setCategoryMainImageUrl(String CategoryMainImageUrl) {
                this.CategoryMainImageUrl = CategoryMainImageUrl;
            }

            public int getCategoryid() {
                return Categoryid;
            }

            public String getIdentifier() {
                return identifier;
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

            public String getCategoryThumbImageUrl() {
                return CategoryThumbImageUrl;
            }

            public String getCategoryMainImageUrl() {
                return CategoryMainImageUrl;
            }
        }
    }
}
