package cs.software.engineering.jobthirsty.applied_workers;

import com.parse.GetCallback;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by sultankhan on 10/14/15.
 */
public class AppliedWorkersRecyclerInfo {

    ParseObject position;
    ParseUser parseUser;
    ParseObject dataObject;
    ParseFile parseFile;

    public AppliedWorkersRecyclerInfo(ParseObject position) {
        super();
        this.position = position;
        ParseQuery<ParseUser> q = ParseUser.getQuery();
        q.whereEqualTo("objectId", position.getString("applicantId"));
        try {
            parseUser = q.getFirst();
        } catch (Exception e) {
        }
    }

    public String getName(){ return position.get("name").toString();}
    public String getPosition(){
        return position.get("position").toString();
    }

    public String getParseObjectId() {
        return position.getObjectId();
    }

    public String getUserId()
    {
        return position.get("applicantId").toString();
    }

    public ParseFile getProfileImage() {
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


    public void Accept(){
        ParseObject connection = new ParseObject("Connections");
        connection.put("handshake",true);
        connection.put("intenderFullName", getName());
        connection.put("intenderId", getParseObjectId());
        connection.put("recieverId", ParseUser.getCurrentUser().getObjectId());
        connection.saveInBackground();
        position.deleteInBackground();

        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("userId", getUserId());
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery);
        push.setMessage("Your Job Application is Accepted!");
        push.sendInBackground();
    }

    public void Decline(){
        position.deleteInBackground();
        ParseQuery pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("userId", getUserId());
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery);
        push.setMessage("Your Have Been Rejected :(");
        push.sendInBackground();
    }
}
