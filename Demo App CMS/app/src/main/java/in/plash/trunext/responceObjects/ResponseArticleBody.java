package in.plash.trunext.responceObjects;

/**
 * Created by Kiran on 9/23/2015.
 */
public class ResponseArticleBody {

    /**
     * jsonobject : {"Id":1047,"publisherid":12,"publicationid":14,"link":"http://businessforall.in/aug15-rss/61-energy/234-turning-plastic-into-fuel","ArticleHeadline":"Turning plastic into FUEL","ArticleSummary":"A n environmentalist at heart, Punebased entrepreneur Shirish Phadtares second love is trekking and photography. Hes, in fact, just returned from Leh and Ladakh where he says the snowfall had almost threatened to prevent his return on time. The wall of his office is adorned with framed pictures of tigers, deer, and other wild animals which he has shot in the Kanha Tiger Sanctuary. But then whats his first love? Its a new business venture that","AuthorName":"contact@businessforall.in (BUSINESS FOR ALL)","CategoryName":"Energy","publishercategoryid":409,"issueid":31,"identifier":"6d1b856a-9238-4bfd-aad9-1b38f8b5b2bf","imageurl":"http://businessforall.in/images/success-stories/Turning-plastic-into-Fuel-2.png","imageheight":"","imagewidth":"","ArticleBody":"<div> \n <div> \n  <div> \n   <img src=\"http://businessforall.in/images/success-stories/Turning-plastic-into-Fuel-2.png\"> \n  <\/div> \n  <div> \n   <p><span>A <\/span>n environmentalist at heart, Pune-based entrepreneur Shirish Phadtare's second love is trekking and photography. He's, in fact, just returned from Leh and Ladakh where he says the snowfall had almost threatened to prevent his return on time. The wall of his office is adorned with framed pictures of tigers, deer, and other wild animals which he has shot in the Kanha Tiger Sanctuary. But then what's his first love? It's a new business venture that will turn plastic garbage into fuel with the help of a machine that he and his partners have designed and put into operation. Rudra Environmental Solution India, the company set up for this purpose, has set up its first plant at Jejuri near Pune to convert plastic waste into poly fuel through a process called pyrolysis.<\/p> \n   <p>Pyrolysis, Phadtare explains, is the thermo-chemical decomposition of organic material at elevated temperatures in the absence of oxygen or any halogen. It involves the simultaneous change of chemical composition and physical phase, and is irreversible. Pyrolysis is a type of thermolysis, and is most commonly observed in organic materials exposed to high temperatures.<\/p> \n  <\/div> \n <\/div> \n<\/div>","flag":1}
     */

    private JsonobjectEntity jsonobject;

    public void setJsonobject(JsonobjectEntity jsonobject) {
        this.jsonobject = jsonobject;
    }

    public JsonobjectEntity getJsonobject() {
        return jsonobject;
    }

    public static class JsonobjectEntity {
        /**
         * Id : 1047
         * publisherid : 12
         * publicationid : 14
         * link : http://businessforall.in/aug15-rss/61-energy/234-turning-plastic-into-fuel
         * ArticleHeadline : Turning plastic into FUEL
         * ArticleSummary : A n environmentalist at heart, Punebased entrepreneur Shirish Phadtares second love is trekking and photography. Hes, in fact, just returned from Leh and Ladakh where he says the snowfall had almost threatened to prevent his return on time. The wall of his office is adorned with framed pictures of tigers, deer, and other wild animals which he has shot in the Kanha Tiger Sanctuary. But then whats his first love? Its a new business venture that
         * AuthorName : contact@businessforall.in (BUSINESS FOR ALL)
         * CategoryName : Energy
         * publishercategoryid : 409
         * issueid : 31
         * identifier : 6d1b856a-9238-4bfd-aad9-1b38f8b5b2bf
         * imageurl : http://businessforall.in/images/success-stories/Turning-plastic-into-Fuel-2.png
         * imageheight :
         * imagewidth :
         * ArticleBody : <div>
         * <div>
         * <div>
         * <img src="http://businessforall.in/images/success-stories/Turning-plastic-into-Fuel-2.png">
         * </div>
         * <div>
         * <p><span>A </span>n environmentalist at heart, Pune-based entrepreneur Shirish Phadtare's second love is trekking and photography. He's, in fact, just returned from Leh and Ladakh where he says the snowfall had almost threatened to prevent his return on time. The wall of his office is adorned with framed pictures of tigers, deer, and other wild animals which he has shot in the Kanha Tiger Sanctuary. But then what's his first love? It's a new business venture that will turn plastic garbage into fuel with the help of a machine that he and his partners have designed and put into operation. Rudra Environmental Solution India, the company set up for this purpose, has set up its first plant at Jejuri near Pune to convert plastic waste into poly fuel through a process called pyrolysis.</p>
         * <p>Pyrolysis, Phadtare explains, is the thermo-chemical decomposition of organic material at elevated temperatures in the absence of oxygen or any halogen. It involves the simultaneous change of chemical composition and physical phase, and is irreversible. Pyrolysis is a type of thermolysis, and is most commonly observed in organic materials exposed to high temperatures.</p>
         * </div>
         * </div>
         * </div>
         * flag : 1
         */

        private int Id;
        private int publisherid;
        private int publicationid;
        private String link;
        private String ArticleHeadline;
        private String ArticleSummary;
        private String AuthorName;
        private String CategoryName;
        private int publishercategoryid;
        private int issueid;
        private String identifier;
        private String imageurl;
        private String imageheight;
        private String imagewidth;
        private String ArticleBody;
        private String TemplatesUrl;

        public String getTagcolour() {
            return tagcolour;
        }

        public void setTagcolour(String tagcolour) {
            this.tagcolour = tagcolour;
        }

        private String tagcolour;
        private int flag;

        public String getTemplatesUrl() {
            return TemplatesUrl;
        }

        public void setTemplatesUrl(String templatesUrl) {
            TemplatesUrl = templatesUrl;
        }


        public void setId(int Id) {
            this.Id = Id;
        }

        public void setPublisherid(int publisherid) {
            this.publisherid = publisherid;
        }

        public void setPublicationid(int publicationid) {
            this.publicationid = publicationid;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public void setArticleHeadline(String ArticleHeadline) {
            this.ArticleHeadline = ArticleHeadline;
        }

        public void setArticleSummary(String ArticleSummary) {
            this.ArticleSummary = ArticleSummary;
        }

        public void setAuthorName(String AuthorName) {
            this.AuthorName = AuthorName;
        }

        public void setCategoryName(String CategoryName) {
            this.CategoryName = CategoryName;
        }

        public void setPublishercategoryid(int publishercategoryid) {
            this.publishercategoryid = publishercategoryid;
        }

        public void setIssueid(int issueid) {
            this.issueid = issueid;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public void setImageheight(String imageheight) {
            this.imageheight = imageheight;
        }

        public void setImagewidth(String imagewidth) {
            this.imagewidth = imagewidth;
        }

        public void setArticleBody(String ArticleBody) {
            this.ArticleBody = ArticleBody;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getId() {
            return Id;
        }

        public int getPublisherid() {
            return publisherid;
        }

        public int getPublicationid() {
            return publicationid;
        }

        public String getLink() {
            return link;
        }

        public String getArticleHeadline() {
            return ArticleHeadline;
        }

        public String getArticleSummary() {
            return ArticleSummary;
        }

        public String getAuthorName() {
            return AuthorName;
        }

        public String getCategoryName() {
            return CategoryName;
        }

        public int getPublishercategoryid() {
            return publishercategoryid;
        }

        public int getIssueid() {
            return issueid;
        }

        public String getIdentifier() {
            return identifier;
        }

        public String getImageurl() {
            return imageurl;
        }

        public String getImageheight() {
            return imageheight;
        }

        public String getImagewidth() {
            return imagewidth;
        }

        public String getArticleBody() {
            return ArticleBody;
        }

        public int getFlag() {
            return flag;
        }
    }
}
