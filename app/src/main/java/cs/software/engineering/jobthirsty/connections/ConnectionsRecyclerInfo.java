package cs.software.engineering.jobthirsty.connections;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.R;

public class ConnectionsRecyclerInfo {

    ParseUser parseUser;
    ParseObject dataObject;
    ParseFile parseFile;

    public ConnectionsRecyclerInfo() {
        super();
    }

    public ConnectionsRecyclerInfo(String parseUserId) {
        super();

        ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", parseUserId);
        try {
            parseUser = query.getFirst();
        } catch (Exception e) {
        }
    }

    public String getName(){
        String fullName = parseUser.getString("firstName") + " " + parseUser.getString("lastName");
        return fullName;
    }

    public ParseFile getProfileImage() {
        ParseQuery<ParseObject> queryData;

        if (parseUser.getBoolean("isBoss")) {
            Log.d("<CONNECTION IMAGE>", getName() + " >> BOSS");
            queryData = new ParseQuery<ParseObject>("EmployerData");
        } else {
            Log.d("<CONNECTION IMAGE>", getName() + " >> WORKER");
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
        return parseUser.getObjectId();
    }

}
