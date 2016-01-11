package in.plash.trunext.databases;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Kiran on 9/26/2015.
 */
@Table(name = "DataBaseArticleBody")
public class DataBaseArticleBody extends Model {

    @Column(name = "flag")
    public String flag;

    @Column(name = "articlebody")
    public String articlebody;

    public DataBaseArticleBody() {
    }

    public DataBaseArticleBody(String flag, String articlebody) {
        this.flag = flag;
        this.articlebody = articlebody;
    }
}
