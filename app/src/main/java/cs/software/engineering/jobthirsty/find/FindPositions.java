package cs.software.engineering.jobthirsty.find;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.mail.MailRecyclerAdapter;
import cs.software.engineering.jobthirsty.mail.MailRecyclerInfo;

public class FindPositions extends AppCompatActivity {


    private RecyclerView recyclerView;
    private FindPositionRecyclerAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_positions);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new FindPositionRecyclerAdapter(FindPositions.this, new ArrayList<FindPositionRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FindPositions.this));

        // Parse Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Position");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> positions, ParseException e) {
                if (e == null) {

                    Toast.makeText(getApplicationContext(), String.valueOf(positions.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject position : positions) {
                        adapter.addRow(new FindPositionRecyclerInfo(position));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
