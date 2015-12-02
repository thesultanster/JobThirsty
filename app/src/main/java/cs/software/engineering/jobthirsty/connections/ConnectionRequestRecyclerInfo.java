package cs.software.engineering.jobthirsty.connections;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class ConnectionRequestRecyclerInfo {

    ParseObject connection;

    public ConnectionRequestRecyclerInfo() {
        super();

    }

    public ConnectionRequestRecyclerInfo(ParseObject news) {
        super();
        this.connection = news;
    }

    public String getTitle(){
        return connection.get("intenderId").toString();
    }

    public String getParseObjectId() {
        return connection.getObjectId();
    }

    public void Accept(){
        connection.put("handshake", true);
        connection.saveInBackground();
    }

    public void Decline(){
        connection.deleteInBackground();
    }
}
