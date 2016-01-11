package in.plash.trunext.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by Kiran on 10/3/2015.
 */
@Table(name = "UserDetails")
public class DbUserDetails extends Model {

    @Column(name = "tag")
    public String tag;

    @Column(name = "jsonBody")
    public String jsonBody;


    public DbUserDetails() {
    }

    public DbUserDetails(String tag, String jsonBody) {

        this.tag = tag;
        this.jsonBody = jsonBody;
    }

    public static DbUserDetails getUserDetails() {
        return new Select().from(DbUserDetails.class).where("tag = ?", "details").executeSingle();
    }

    public static int getUserCount() {
        return new Select().from(DbUserDetails.class).where("tag = ?", "details").execute().size();
    }
}
