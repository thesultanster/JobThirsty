package cs.software.engineering.jobthirsty.find_workers;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class FindWorker extends NavigationDrawerFramework {

    RecyclerView recyclerView;
    private FindWorkerRecyclerAdapter adapter;
    SearchView searchView;
    Button designer;
    Button law;
    Button business;
    Button all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_worker);

        getToolbar().setTitle("Find Tutors");

        handleIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new FindWorkerRecyclerAdapter(FindWorker.this, new ArrayList<FindWorkerRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FindWorker.this));

        designer = (Button) findViewById(R.id.designer);
        law = (Button) findViewById(R.id.law);
        business = (Button) findViewById(R.id.business);
        all = (Button) findViewById(R.id.all);

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("business", true);
            }
        });

        law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("law", true);
            }
        });

        designer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("designer", true);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter = new FindWorkerRecyclerAdapter(FindWorker.this, new ArrayList<FindWorkerRecyclerInfo>());
                recyclerView.setAdapter(adapter);

                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereNotEqualTo("isBoss", true);
                query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                query.findInBackground(new FindCallback<ParseUser>() {


                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null)
                        {

                            //ParseObject.pinAllInBackground(users);

                            Toast.makeText(FindWorker.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                            Log.d("username", "Retrieved " + users.size() + " username");
                            for (int i = 0; i < users.size(); i++)
                            {
                                adapter.addRow(new FindWorkerRecyclerInfo(users.get(i)));
                            }
                        }
                        else
                        {
                            Log.d("score", "Error: " + e.getMessage());
                        }

                    }
                });

            }
        });


/*
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.whereNotEqualTo("isBoss", true);
        query.findInBackground(new FindCallback<ParseUser>() {


            public void done(List<ParseUser> users, ParseException e) {
                if (e == null)
                {

                    //ParseObject.pinAllInBackground(users);

                    Toast.makeText(FindWorker.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < users.size(); i++)
                    {
                        adapter.addRow(new FindWorkerRecyclerInfo(users.get(i)));
                    }
                }
                else
                {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });
        */



    }


    // Toolbar Overrides
    /***************************************************************************************************************/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_find_worker, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) FindWorker.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(FindWorker.this.getComponentName()));
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String search = intent.getStringExtra(SearchManager.QUERY);

            if(adapter!=null){
                adapter.clearData();
            }

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            adapter = new FindWorkerRecyclerAdapter(FindWorker.this, new ArrayList<FindWorkerRecyclerInfo>());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(FindWorker.this));


            if( search.length() != 0) {
                ParseQuery<ParseUser> q1 = ParseUser.getQuery();
                q1.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                q1.whereNotEqualTo("isBoss", true);
                q1.whereContains("username", search);
                q1.findInBackground(new FindCallback<ParseUser>() {


                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null) {

                            //ParseObject.pinAllInBackground(users);

                            Toast.makeText(FindWorker.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < users.size(); i++) {
                                adapter.addRow(new FindWorkerRecyclerInfo(users.get(i)));
                            }
                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }

                    }
                });
            }



        }
    }



    /***************************************************************************************************************/



}
