package cs.software.engineering.jobthirsty.connections;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import cs.software.engineering.jobthirsty.R;

public class Connections extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConnectionsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ConnectionsRecyclerAdapter(Connections.this, new ArrayList<ConnectionsRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Connections.this));

        // Parse Query
        ParseQuery<ParseObject> intenderQuery = ParseQuery.getQuery("Connections");
        //intenderQuery.whereEqualTo("handshake", true);
        intenderQuery.whereContains("intenderName", ParseUser.getCurrentUser().getUsername());

        ParseQuery<ParseObject> recieverQuery = ParseQuery.getQuery("Connections");
        //recieverQuery.whereEqualTo("handshake", true);
        recieverQuery.whereContains("recieverName", ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> mainQuery = new ArrayList<ParseQuery<ParseObject>>();
        mainQuery.add(intenderQuery);
        mainQuery.add(recieverQuery);

        ParseQuery<ParseObject> query = ParseQuery.or(mainQuery);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> connections, ParseException e) {
                if (e == null) {

                    Toast.makeText(getApplicationContext(), String.valueOf(connections.size()), Toast.LENGTH_SHORT).show();
                    Map<String, String> map = new HashMap<String, String>();

                    for (ParseObject c : connections) {
                        String intenderName = c.get("intenderName").toString();
                        String recievername = c.get("recieverName").toString();

                        if (!map.containsKey(intenderName) && !map.containsKey(recievername)) {
                            adapter.addRow(new ConnectionsRecyclerInfo(c));
                            if (!map.containsKey(intenderName)) {
                                map.put(intenderName, " ");
                            }
                            if (!map.containsKey(recievername)) {
                                map.put(recievername, "");
                            }
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
