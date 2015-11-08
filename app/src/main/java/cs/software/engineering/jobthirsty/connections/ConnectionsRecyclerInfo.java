package cs.software.engineering.jobthirsty.connections;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class ConnectionsRecyclerInfo {

    ParseObject position;

    public ConnectionsRecyclerInfo() {
        super();

    }

    public ConnectionsRecyclerInfo(ParseObject position) {
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

}
