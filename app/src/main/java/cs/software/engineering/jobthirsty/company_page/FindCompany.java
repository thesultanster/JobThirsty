package cs.software.engineering.jobthirsty.company_page;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class FindCompany extends NavigationDrawerFramework {


    private RecyclerView recyclerView;
    private FindCompanyRecyclerAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_find_company);

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new FindCompanyRecyclerAdapter(FindCompany.this, new ArrayList<FindCompanyRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FindCompany.this));

        // Parse Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Company");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> positions, ParseException e) {
                if (e == null) {

                    Toast.makeText(getApplicationContext(), String.valueOf(positions.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject position : positions) {
                        adapter.addRow(new FindCompanyRecyclerInfo(position));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
