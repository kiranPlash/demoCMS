package in.plash.trunext.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * Created by Kiran on 9/25/2015.
 */

@Table(name = "DataBasePublisher")
public class DataBasePublisher extends Model {

    @Column(name = "flag")
    public String flag;

    @Column(name = "PublisherJson")
    public String PublisherJson;

    @Column(name = "time")
    public long time;

    /* @Column(name = "time")
    public String timeStamp;*/


 /*   @Column(name = "IssueName")
    public String IssueName;
    @Column(name = "MagazineCoverImage")
    public String MagazineCoverImage;
    @Column(name = "Category")
    public List<ResponsePublication.ListEntity.CategoryEntity> Category;
*/

    public DataBasePublisher() {
        super();
    }

    public DataBasePublisher(String flag, long time, String publisherJson) {
        this.flag = flag;
        this.PublisherJson = publisherJson;
        this.time = time;
    }

    public static DataBasePublisher getIssueData() {
        return new Select().from(DataBasePublisher.class).where("flag = ?", "NEW").executeSingle();
    }

    public static void deleteDBPub(){
        new Delete().from(DataBasePublisher.class).where("flag = ?", "NEW").execute();
    }

    public static int getIssueDbSize(){
      return new Select().from(DataBasePublisher.class).execute().size();
    }
}
