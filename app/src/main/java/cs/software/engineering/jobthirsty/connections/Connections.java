package cs.software.engineering.jobthirsty.connections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import cs.software.engineering.jobthirsty.R;

public class Connections extends Fragment {

    private RecyclerView recyclerView;
    private ConnectionsRecyclerAdapter adapter;
    private static final String ARG_PAGE = "ARG_PAGE";
    private ArrayList<String> connections;
    int page;
    ParseObject dataObject;

    ParseUser currentUser;
    String currentUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
        connections = new ArrayList<>();
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

        ParseQuery<ParseObject> queryData;

        if (currentUser.getBoolean("isBoss")) {
            queryData = new ParseQuery<ParseObject>("EmployerData");
        } else {
            queryData = new ParseQuery<ParseObject>("EmployeeData");
        }
        queryData.whereEqualTo("objectId", currentUser.getString("dataId"));
        queryData.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    dataObject = parseObject;
                    connections = (ArrayList) dataObject.getList("connections");
//                    Toast.makeText(getActivity(), String.valueOf(connections.size()), Toast.LENGTH_SHORT).show();
                    for (String connectionId : connections) {
                        adapter.addRow(new ConnectionsRecyclerInfo(connectionId));
                    }
                }
            }
        });
    }

}
