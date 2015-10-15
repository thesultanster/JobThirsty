package cs.software.engineering.jobthirsty.newsfeed;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class Newsfeed extends NavigationDrawerFramework {

    private RecyclerView recyclerView;
    private NewsfeedRecyclerAdapter adapter;
    List<ParseObject> feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new NewsfeedRecyclerAdapter(Newsfeed.this, new ArrayList<NewsfeedRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Newsfeed.this));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Newsfeed");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> newsfeed, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), String.valueOf(newsfeed.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject news : newsfeed) {
                        adapter.addRow(new NewsfeedRecyclerInfo(news));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
