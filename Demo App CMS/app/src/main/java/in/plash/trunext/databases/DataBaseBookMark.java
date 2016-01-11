package in.plash.trunext.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

/**
 * Created by Kiran on 9/30/2015.
 */
@Table(name = "DataBaseBookMark")
public class DataBaseBookMark extends Model {

    @Column(name = "flag")
   public String flag;

    @Column(name = "JsonBody")
   public String JsonBody;

    public DataBaseBookMark() {
    }

    public DataBaseBookMark(String flag, String jsonBody) {
        this.flag = flag;
        this.JsonBody = jsonBody;
    }

    public static DataBaseBookMark getBookMarkData(String tag) {
        return new Select().from(DataBaseBookMark.class).where("flag = ?", tag).executeSingle();
    }

    public static void deleteDBbookMark(String tag){
        new Delete().from(DataBaseBookMark.class).where("flag = ?", tag).execute();
    }

    public static int getBookMarkDbSize(){
        return new Select().from(DataBaseBookMark.class).execute().size();
    }

}
