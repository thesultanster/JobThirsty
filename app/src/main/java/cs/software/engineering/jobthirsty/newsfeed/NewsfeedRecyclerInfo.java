package cs.software.engineering.jobthirsty.newsfeed;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class NewsfeedRecyclerInfo {

    ParseObject news;

    public NewsfeedRecyclerInfo() {
        super();

    }

    public NewsfeedRecyclerInfo(ParseObject news) {
        super();
        this.news = news;
    }

    public String getTitle(){
        return news.get("title").toString();
    }
    public String getUpdate(){
        return news.get("update").toString();
    }

    public String getParseObjectId() {
        return news.getObjectId();
    }

}
