package cs.software.engineering.jobthirsty.job_position;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs.software.engineering.jobthirsty.R;

public class Positions extends Fragment {


    private RecyclerView recyclerView;
    private PositionsRecyclerAdapter adapter;
    private FloatingActionButton fab;
    private static final String ARG_PAGE = "ARG_PAGE";
    private Map<String, String> positionObjectIDs;
    int page;
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
        positionObjectIDs = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_find_positions, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        final Context c = getContext();

        // RecyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new PositionsRecyclerAdapter(getActivity(), new ArrayList<PositionsRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Parse Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Position");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> positions, ParseException e) {
                if (e == null) {

                    Toast.makeText(c, String.valueOf(positions.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject position : positions) {
                        String objectID = position.getObjectId();
                        if (!positionObjectIDs.containsKey(objectID)) {
                            adapter.addRow(new PositionsRecyclerInfo(position));
                            positionObjectIDs.put(objectID, "");
                        }
                    }

                } else {
                    Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

