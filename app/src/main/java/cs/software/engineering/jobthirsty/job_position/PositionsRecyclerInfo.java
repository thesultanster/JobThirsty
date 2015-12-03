package cs.software.engineering.jobthirsty.job_position;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class PositionsRecyclerInfo {

    ParseObject position;

    public PositionsRecyclerInfo() {
        super();

    }

    public PositionsRecyclerInfo(ParseObject position) {
        super();
        this.position = position;
    }

    public String getPositionTitle(){
        return position.get("positionTitle").toString();
    }
    public String getSubject(){
        return position.get("companyTitle").toString();
    }
    public String getBody(){
        return position.get("location").toString();
    }

    public String getParseObjectId() {
        return position.getObjectId();
    }

}
