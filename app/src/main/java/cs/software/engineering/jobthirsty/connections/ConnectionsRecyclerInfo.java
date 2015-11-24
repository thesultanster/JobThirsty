package cs.software.engineering.jobthirsty.connections;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by sultankhan on 10/14/15.
 */
public class ConnectionsRecyclerInfo {

    ParseObject connection;

    public ConnectionsRecyclerInfo() {
        super();

    }

    public ConnectionsRecyclerInfo(ParseObject connection) {
        super();
        this.connection = connection;
    }

    public String getName(){
        String intenderName = connection.get("intenderName").toString();
        String recievername = connection.get("receiverName").toString();
        return ParseUser.getCurrentUser().getUsername() == intenderName ? intenderName : recievername;
    }

    public String getParseObjectId() {
        return connection.getObjectId();
    }

}
