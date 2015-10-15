package cs.software.engineering.jobthirsty.mail;

import com.parse.ParseObject;

/**
 * Created by sultankhan on 10/14/15.
 */
public class MailRecyclerInfo {

    ParseObject mail;

    public MailRecyclerInfo() {
        super();

    }

    public MailRecyclerInfo(ParseObject mail) {
        super();
        this.mail = mail;
    }

    public String getTitle(){
        return mail.get("title").toString();
    }

    public String getParseObjectId() {
        return mail.getObjectId();
    }

}
