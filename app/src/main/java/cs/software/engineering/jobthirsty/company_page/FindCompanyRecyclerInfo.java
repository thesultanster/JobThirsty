package cs.software.engineering.jobthirsty.company_page;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class FindCompanyRecyclerInfo {

    ParseObject company;

    public FindCompanyRecyclerInfo(ParseObject company) {
        super();
        this.company = company;
    }

    public String getCompanyTitle(){
        return company.get("companyTitle").toString();
    }
    public String getCompanyTagline(){
        return company.get("companyTagline").toString();
    }
    public String getLocation(){
        return company.get("location").toString();
    }
    public String getParseObjectId() {
        return company.getObjectId();
    }

}
