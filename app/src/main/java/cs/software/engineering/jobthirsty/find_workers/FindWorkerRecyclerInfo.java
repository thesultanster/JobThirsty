package cs.software.engineering.jobthirsty.find_workers;

import android.location.Location;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class FindWorkerRecyclerInfo {

    ParseObject user;
    public FindWorkerRecyclerInfo(ParseObject user)
    {
        super();
        this.user = user;
    }
    public String getUsername()
    {
        return user.get("username").toString();
    }

    public String getFirstName()
    {
        return user.get("firstName").toString();
    }
    public String getLastName()
    {
        return user.get("lastName").toString();
    }
    public String getParseObjectId()
    {
        return user.getObjectId();
    }
    public String getDataId()
    {
        return user.get("dataId").toString();
    }



}
