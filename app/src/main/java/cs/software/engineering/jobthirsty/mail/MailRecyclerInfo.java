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

    public String getSender(){
        return mail.get("sender").toString();
    }
    public String getSubject(){
        return mail.get("subject").toString();
    }
    public String getBody(){
        return mail.get("body").toString();
    }

    public String getParseObjectId() {
        return mail.getObjectId();
    }

}
