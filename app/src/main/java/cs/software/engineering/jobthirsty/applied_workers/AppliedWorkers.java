package cs.software.engineering.jobthirsty.applied_workers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class AppliedWorkers extends NavigationDrawerFramework {

    private RecyclerView recyclerView;
    private AppliedWorkersRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_workers);

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new AppliedWorkersRecyclerAdapter(AppliedWorkers.this, new ArrayList<AppliedWorkersRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppliedWorkers.this));

        // Parse Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AppliedWorkers");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> positions, ParseException e) {
                if (e == null) {

                    Toast.makeText(getApplicationContext(), String.valueOf(positions.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject position : positions) {
                        adapter.addRow(new AppliedWorkersRecyclerInfo(position));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
