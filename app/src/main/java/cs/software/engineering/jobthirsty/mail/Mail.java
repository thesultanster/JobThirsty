package cs.software.engineering.jobthirsty.mail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.ComposeMail;
import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class Mail extends NavigationDrawerFramework {

    private RecyclerView recyclerView;
    private MailRecyclerAdapter adapter;
    List<ParseObject> mail;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MailRecyclerAdapter(Mail.this, new ArrayList<MailRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Mail.this));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mail");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> mails, ParseException e) {
                if (e == null) {

                    mail = mails;
                    Toast.makeText(getApplicationContext(), String.valueOf(mails.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject news : mails) {
                        adapter.addRow(new MailRecyclerInfo(news));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComposeMail.class);
                startActivity(intent);
            }
        });



    }
}
