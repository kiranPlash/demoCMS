package in.plash.trunext.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by Kiran on 10/3/2015.
 */
@Table(name = "User_Login_Status")
public class DbLoginStatus extends Model {

    @Column(name = "userLoginStatus")
    public String userLoginStatus;

    @Column(name = "tag")
    public String tag;

    public DbLoginStatus() {
    }

    public DbLoginStatus(String userLoginStatus, String tag) {
        this.userLoginStatus = userLoginStatus;
        this.tag = tag;
    }

    public static DbLoginStatus getUserID(String dbLoginUserId) {
        return new Select().from(DbLoginStatus.class).where("tag = ?", dbLoginUserId).executeSingle();
    }

    public static int getUserCount() {
        return new Select().from(DbLoginStatus.class).where("tag = ?", "userID").execute().size();
    }
}
