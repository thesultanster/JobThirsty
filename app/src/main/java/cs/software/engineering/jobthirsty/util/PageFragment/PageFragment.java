package cs.software.engineering.jobthirsty.util.PageFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs.software.engineering.jobthirsty.R;

import java.util.ArrayList;

/** PageFragment
 *      Description: fragment implementation for each tab
 *
 */
public class PageFragment extends Fragment
{
    //PRIVATE VARIABLES
    // the fragment initialization parameters
    private static final String ARG_PAGE = "ARG_PAGE";

    int page;
    RecyclerView rv;
    //ArrayList<Song> rvData;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate(R.layout.activity_newsfeed, container, false);

//        rv = (RecyclerView)view.findViewById(R.id.recyclerView);
//        rvData = new ArrayList<>();
//        rvData.add(new Song("", "Hello World", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Welcome to Testing", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Enjoy the App", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Music for life", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Running Away", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Fire in the hole", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Hello World", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Welcome to Testing", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Enjoy the App", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Music for life", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Running Away", "", "", "", "", "", "", "", "", 0));
//        rvData.add(new Song("", "Fire in the hole", "", "", "", "", "", "", "", "", 0));

//        setupRV();

        return view;
    }

    void setupRV()
    {
//        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
//        rv.setLayoutManager(llm);
//
//        RecyclerViewAdapter RVAdapter = new RecyclerViewAdapter(this.getContext(), rvData);
//        rv.setAdapter(RVAdapter);
    }
}
