package in.plash.trunext.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * Created by Kiran on 9/26/2015.
 */
@Table(name = "DataBaseArticlesList")
public class DataBaseArticlesList extends Model {

   /* @Column(name = "flag")
    public String flag;*/

    @Column(name = "categoryID")
    public int categoryID;

    @Column(name = "articleLists")
    public String articleLists;

    @Column(name = "time")
    public long time;

    public DataBaseArticlesList() {
    }

    public DataBaseArticlesList(int categoryID, String articleLists, long time) {
        //  this.flag = flag;
        this.categoryID = categoryID;
        this.articleLists = articleLists;
        this.time = time;

    }

    public static DataBaseArticlesList getArticleData(int tag) {
        return new Select().from(DataBaseArticlesList.class).where("categoryID = ?", tag).executeSingle();
    }

    public static void deleteDBArticle(int tag) {
        new Delete().from(DataBaseArticlesList.class).where("categoryID = ?", tag).execute();
    }

    public static int getArticleDbSize() {
        return new Select().from(DataBaseArticlesList.class).execute().size();
    }
}
