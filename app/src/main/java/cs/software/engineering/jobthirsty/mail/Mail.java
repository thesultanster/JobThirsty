package cs.software.engineering.jobthirsty.mail;

import android.os.Bundle;
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

public class Mail extends NavigationDrawerFramework {

    private RecyclerView recyclerView;
    private MailRecyclerAdapter adapter;
    List<ParseObject> feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MailRecyclerAdapter(Mail.this, new ArrayList<MailRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Mail.this));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mail");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> mail, ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), String.valueOf(mail.size()), Toast.LENGTH_SHORT).show();
                    for (ParseObject news : mail) {
                        adapter.addRow(new MailRecyclerInfo(news));
                    }

                } else {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
