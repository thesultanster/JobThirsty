package cs.software.engineering.jobthirsty.applied_workers;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by sultankhan on 10/14/15.
 */
public class AppliedWorkersRecyclerInfo {

    ParseObject position;
    ParseUser user;

    public AppliedWorkersRecyclerInfo() {
        super();

    }

    public AppliedWorkersRecyclerInfo(ParseObject position) {
        super();
        this.position = position;

    }

    public String getName(){ return position.get("name").toString();}
    public String getPosition(){
        return position.get("position").toString();
    }
    public String getDegree(){return position.get("degree").toString();}
    public String getQuote(){
        return position.get("quote").toString();
    }

    public String getParseObjectId() {
        return position.getObjectId();
    }



    public String getUserId()
    {
        return position.get("applicantId").toString();
    }
}
