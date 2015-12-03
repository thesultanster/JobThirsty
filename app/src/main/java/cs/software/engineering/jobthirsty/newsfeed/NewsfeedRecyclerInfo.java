package cs.software.engineering.jobthirsty.newsfeed;

import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sultankhan on 10/14/15.
 */
public class NewsfeedRecyclerInfo {

    ParseObject news;
    ParseObject dataObject;
    ParseFile parseFile;
    ArrayList<String> involvedUserIdList;
    ArrayList<ParseUser> involvedUserList;

    public NewsfeedRecyclerInfo() {
        super();
    }

    public NewsfeedRecyclerInfo(ParseObject news) {
        super();
        this.news = news;

        involvedUserList = new ArrayList<>();
        involvedUserIdList = (ArrayList) news.getList("involvedList");
        for (String parseUserId : involvedUserIdList) {

            ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
            query.whereEqualTo("objectId", parseUserId);
            try {
                involvedUserList.add(query.getFirst());
            } catch (Exception e) {
            }
        }
    }

    public int getNumInvolved() {
        return involvedUserList.size();
    }

    public String getUpdate(){
        return news.get("update").toString();
    }

    public ParseFile getProfileImage(int index) {

        if (index >= involvedUserList.size()) {
            return null;
        }

        ParseUser parseUser = involvedUserList.get(index);
        ParseQuery<ParseObject> queryData;

        if (parseUser.getBoolean("isBoss")) {
            queryData = new ParseQuery<ParseObject>("EmployerData");
        } else {
            queryData = new ParseQuery<ParseObject>("EmployeeData");
        }
        queryData.whereEqualTo("objectId", parseUser.getString("dataId"));
        try {
            dataObject = queryData.getFirst();
            parseFile = dataObject.getParseFile("profileImage");
        } catch (Exception e) {
        }

        return parseFile;
    }

    public String getParseObjectId() {
        return news.getObjectId();
    }

}
