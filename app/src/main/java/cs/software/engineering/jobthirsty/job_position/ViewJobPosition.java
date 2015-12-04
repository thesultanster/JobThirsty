package cs.software.engineering.jobthirsty.job_position;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import cs.software.engineering.jobthirsty.R;

public class ViewJobPosition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_position);

        Bundle extras = getIntent().getExtras();
        String positionTitle="Example Title";
        String positionDescription="This is the description";
        String positionLocation="Example Location";
        String positionCompany="Example Company";

        if (extras != null) {
            positionTitle = extras.getString("positionTitle");
            positionCompany = extras.getString("positionCompany");
            positionDescription = extras.getString("positionDescription");
            positionLocation = extras.getString("positionLocation");

            // and get whatever type user account id is
        }

        EditText jobTitle = (EditText) findViewById(R.id.jobTitle);
        EditText location = (EditText) findViewById(R.id.location);
        EditText companyTitle = (EditText) findViewById(R.id.companyTitle);
        EditText description = (EditText) findViewById(R.id.descriptionText);

        jobTitle.setText(positionTitle);
        location.setText(positionLocation);
        companyTitle.setText(positionCompany);
        description.setText(positionDescription);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Thank You For Applying", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
