package cs.software.engineering.jobthirsty.find;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.mail.MailRecyclerAdapter;
import cs.software.engineering.jobthirsty.mail.MailRecyclerInfo;

public class FindPositions extends AppCompatActivity {


    private RecyclerView recyclerView;
    private FindPositionRecyclerAdapter adapter;
    List<ParseObject> positions;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_positions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new FindPositionRecyclerAdapter(FindPositions.this, new ArrayList<FindPositionRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FindPositions.this));

    }

}
