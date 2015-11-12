package cs.software.engineering.jobthirsty.company_page;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class FindCompanyRecyclerInfo {

    ParseObject position;

    public FindCompanyRecyclerInfo() {
        super();

    }

    public FindCompanyRecyclerInfo(ParseObject position) {
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
