package cs.software.engineering.jobthirsty.connections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cs.software.engineering.jobthirsty.util.PageFragment.PageFragment;

public class Connections extends Fragment {

    private RecyclerView recyclerView;
    private ConnectionsRecyclerAdapter adapter;
    private static final String ARG_PAGE = "ARG_PAGE";
    private Map<String, String> connObjectIDs;
    int page;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.activity_connections, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        // RecyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new ConnectionsRecyclerAdapter(getContext(), new ArrayList<ConnectionsRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Parse Query
        ParseQuery<ParseObject> intenderQuery = ParseQuery.getQuery("Connections");
        intenderQuery.whereEqualTo("handshake", true);
        intenderQuery.whereContains("intenderName", ParseUser.getCurrentUser().getUsername());

        ParseQuery<ParseObject> recieverQuery = ParseQuery.getQuery("Connections");
        recieverQuery.whereEqualTo("handshake", true);
        recieverQuery.whereContains("receiverName", ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> mainQuery = new ArrayList<ParseQuery<ParseObject>>();
        mainQuery.add(intenderQuery);
        mainQuery.add(recieverQuery);

        ParseQuery<ParseObject> query = ParseQuery.or(mainQuery);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> connections, ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), String.valueOf(connections.size()), Toast.LENGTH_SHORT).show();

                    for (ParseObject c : connections) {
                        //duplication check (only add if it doesn't exists
                        if(!connObjectIDs.containsKey(c.getObjectId())) {
                            adapter.addRow(new ConnectionsRecyclerInfo(c));
                            connObjectIDs.put(c.getObjectId(), "");
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        connObjectIDs = new HashMap<>();
    }

}
