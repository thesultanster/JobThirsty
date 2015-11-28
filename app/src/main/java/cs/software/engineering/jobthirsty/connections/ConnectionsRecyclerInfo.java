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
        String intenderName = connection.get("intenderFullName").toString();
        String receiverName = connection.get("receiverFullName").toString();

        return ParseUser.getCurrentUser().getUsername() == intenderName ? intenderName : receiverName;
    }

    public String getParseObjectId() {
        return connection.getObjectId();
    }

}
