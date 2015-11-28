package cs.software.engineering.jobthirsty.connections;

import com.parse.ParseObject;
import com.parse.ParseUser;

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
        String intenderFullName = connection.get("intenderFullName").toString();
        String receiverFullName = connection.get("receiverFullName").toString();

        //compare username
        String intenderName = connection.get("intenderName").toString();

        return ParseUser.getCurrentUser().getUsername().equals(intenderName) ? receiverFullName : intenderFullName;
    }

    public String getParseObjectId() {
        return connection.getObjectId();
    }

}
