package cs.software.engineering.jobthirsty.connections;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sultankhan on 10/14/15.
 */
public class ConnectionRequestRecyclerInfo {

    ParseObject connection;
    private ParseObject intenderObject;
    private ParseObject receiverObject;


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
        final String intenderId = (String)connection.get("intenderId");
        final String receiverId = (String)connection.get("receiverId");

        //get the dataObject for both the receiver and the intender for their connections list
        ParseQuery<ParseObject> queryIntender = ParseQuery.getQuery("EmployeeData");
        queryIntender.whereEqualTo("userId", intenderId);

        ParseQuery<ParseObject> queryReceiver = ParseQuery.getQuery("EmployeeData");
        queryReceiver.whereEqualTo("userId", receiverId);

        List<ParseQuery<ParseObject>> queryList = new ArrayList<ParseQuery<ParseObject>>();
        queryList.add(queryIntender);
        queryList.add(queryReceiver);

        ParseQuery<ParseObject> queryConnections = ParseQuery.or(queryList);
        queryConnections.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> connections, ParseException e) {
                if(connections.size() == 2 && connections.get(0) != null && connections.get(1) != null ){
                    intenderObject = connections.get(1);
                    receiverObject = connections.get(0);

                    //add to each others connectionlist
                    intenderObject.addUnique("connections", receiverId);
                    intenderObject.saveInBackground();
                    receiverObject.addUnique("connections", intenderId);
                    receiverObject.saveInBackground();

                    //create involved and visible lists for newsfeed
                    ArrayList<String> involvedList = new ArrayList<String>();
                    involvedList.add((String)connection.get("intenderId"));
                    involvedList.add((String)connection.get("receiverId"));

                    Set<String> connectionsList = new HashSet<String>();
                    connectionsList.addAll((ArrayList) intenderObject.getList("connections"));
                    connectionsList.addAll((ArrayList) receiverObject.getList("connections"));
                    connectionsList.add(intenderId);
                    connectionsList.add(receiverId);

                    //push the event to Newsfeed
                    ParseObject newNewsfeedPost = new ParseObject("Newsfeed");
                    newNewsfeedPost.put("update", connection.get("receiverFullName") + " and " + connection.get("intenderFullName")
                            + " are now connected");
                    newNewsfeedPost.put("involvedList", involvedList);
                    newNewsfeedPost.put("visibleList", new ArrayList<String>(connectionsList));
                    newNewsfeedPost.saveInBackground();
                }
                else {
                    Log.d("<ConnectionRequest>", " ***Could not find intender or receiver!***");
                    return;
                }
            }
        });

    }

    public void Decline(){
        connection.deleteInBackground();
    }
}
