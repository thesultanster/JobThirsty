package cs.software.engineering.jobthirsty.newsfeed;

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

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.PageFragment.PageFragment;

public class Newsfeed extends Fragment {

    private RecyclerView recyclerView;
    private NewsfeedRecyclerAdapter adapter;
    List<ParseObject> feed;
    private static final String ARG_PAGE = "ARG_PAGE";
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
        return inflater.inflate(R.layout.activity_newsfeed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new NewsfeedRecyclerAdapter(getContext(), new ArrayList<NewsfeedRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ToDo: Need to load the page according to the user (worker or boss)
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Newsfeed");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> newsfeed, ParseException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), String.valueOf(newsfeed.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject news : newsfeed) {
                        adapter.addRow(new NewsfeedRecyclerInfo(news));
                    }

                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
