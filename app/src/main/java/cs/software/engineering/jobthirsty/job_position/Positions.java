package cs.software.engineering.jobthirsty.job_position;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs.software.engineering.jobthirsty.R;

public class Positions extends Fragment {


    private RecyclerView recyclerView;
    private PositionsRecyclerAdapter adapter;
    private static final String ARG_PAGE = "ARG_PAGE";
    private Map<String, String> positionObjectIDs;
    int page;

    //OVERRIDE FUNCTIONS [START] -------------------------------------------------------------------
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

        queryAll();
    }
    //[END] ----------------------------------------------------------------------------------------


    //OPERATION FUNCTIONS [START] ------------------------------------------------------------------
    public void setSearch(String search) {
        if (adapter != null) {
            adapter.clearData();
            positionObjectIDs.clear();
        }

        // RecyclerView
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        adapter = new PositionsRecyclerAdapter(getActivity(), new ArrayList<PositionsRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (search.length() != 0) {
            // Parse Query
            ParseQuery<ParseObject> companyTitle = ParseQuery.getQuery("Position");
            companyTitle.whereContains("companyTitle", search);
            ParseQuery<ParseObject> description = ParseQuery.getQuery("Position");
            description.whereContains("description", search);
            ParseQuery<ParseObject> location = ParseQuery.getQuery("Position");
            location.whereContains("location", search);
            ParseQuery<ParseObject> positionTitle = ParseQuery.getQuery("Position");
            positionTitle.whereContains("positionTitle", search);

            List<ParseQuery<ParseObject>> queryList = new ArrayList<ParseQuery<ParseObject>>();
            queryList.add(companyTitle);
            queryList.add(description);
            queryList.add(location);
            queryList.add(positionTitle);

            ParseQuery<ParseObject> query = ParseQuery.or(queryList);
            if((boolean) ParseUser.getCurrentUser().get("isBoss")) {
                query.whereEqualTo("bossId", ParseUser.getCurrentUser().get("isBoss"));
            }
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> positions, ParseException e) {
                    if (e == null) {
                        for (ParseObject position : positions) {
                            String objectID = position.getObjectId();
                            if (!positionObjectIDs.containsKey(objectID)) {
                                adapter.addRow(new PositionsRecyclerInfo(position));
                                positionObjectIDs.put(objectID, "");
                            }
                        }

                    } else {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void queryAll() {
        if (adapter != null) {
            adapter.clearData();
            positionObjectIDs.clear();
        }

        View v = getView();
        final Context c = getContext();

        // RecyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new PositionsRecyclerAdapter(getActivity(), new ArrayList<PositionsRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Parse Query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Position");
        if((boolean) ParseUser.getCurrentUser().get("isBoss")) {
            query.whereEqualTo("bossId", ParseUser.getCurrentUser().getObjectId());
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> positions, ParseException e) {
                if (e == null) {
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
    //[END] ----------------------------------------------------------------------------------------
}

