package cs.software.engineering.jobthirsty.connections;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class ConnectionRequest extends NavigationDrawerFramework {

    private RecyclerView recyclerView;
    private ConnectionRequestRecyclerAdapter adapter;
    List<ParseObject> feed;
    int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_request);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ConnectionRequestRecyclerAdapter(getApplicationContext(), new ArrayList<ConnectionRequestRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        //ToDo: Need to load the page according to the user (worker or boss)
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Connections");
        query.whereEqualTo("receiverId", ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo("handshake", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> newsfeed, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), String.valueOf(newsfeed.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject news : newsfeed) {
                        adapter.addRow(new ConnectionRequestRecyclerInfo(news));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }





}
