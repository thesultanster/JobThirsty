package cs.software.engineering.jobthirsty.connections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    ParseUser currentUser;
    String currentUserId;

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
        connObjectIDs = new HashMap<>();
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
        currentUser = ParseUser.getCurrentUser();
        currentUserId = currentUser.getObjectId();

        // RecyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new ConnectionsRecyclerAdapter(getContext(), new ArrayList<ConnectionsRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ParseQuery<ParseObject> queryIntender = ParseQuery.getQuery("Connections");
        queryIntender.whereEqualTo("intenderId", currentUserId);

        ParseQuery<ParseObject> queryReceiver = ParseQuery.getQuery("Connections");
        queryReceiver.whereEqualTo("receiverId", currentUserId);

        List<ParseQuery<ParseObject>> queryList = new ArrayList<ParseQuery<ParseObject>>();
        queryList.add(queryIntender);
        queryList.add(queryReceiver);

        final ParseQuery<ParseObject> queryConnections = ParseQuery.or(queryList);
        queryConnections.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> connections, ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), String.valueOf(connections.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject c : connections) {
                        if (c.getBoolean("handshake")) {
                            String connectionId = (c.getString("intenderId").equals(currentUserId))
                                    ? (c.getString("receiverId")) : (c.getString("intenderId"));
                            if(!connObjectIDs.containsKey(connectionId)) {
                                adapter.addRow(new ConnectionsRecyclerInfo(connectionId));
                                connObjectIDs.put(connectionId, "");
                            }
                        }
                    }
                    connObjectIDs.clear();
                }
            }
        });
    }

}
