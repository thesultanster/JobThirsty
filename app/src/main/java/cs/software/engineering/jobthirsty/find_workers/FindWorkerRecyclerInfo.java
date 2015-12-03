package cs.software.engineering.jobthirsty.find_workers;

import android.location.Location;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FindWorkerRecyclerInfo {

    ParseUser parseUser;
    ParseObject dataObject;
    ParseFile parseFile;

    public FindWorkerRecyclerInfo(ParseUser user)
    {
        super();
        this.parseUser = user;
    }
    public String getUsername() { return parseUser.get("username").toString(); }
    public String getFirstName()
    {
        return parseUser.get("firstName").toString();
    }
    public String getLastName()
    {
        return parseUser.get("lastName").toString();
    }
    public String getParseObjectId()
    {
        return parseUser.getObjectId();
    }
    public String getDataId()
    {
        return parseUser.get("dataId").toString();
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

}
