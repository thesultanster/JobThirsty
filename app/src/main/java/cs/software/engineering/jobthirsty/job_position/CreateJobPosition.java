package cs.software.engineering.jobthirsty.job_position;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import cs.software.engineering.jobthirsty.R;

public class CreateJobPosition extends AppCompatActivity {


    EditText companyTitle;
    EditText positionTitle;
    EditText positionDescription;
    EditText location;

    Button postPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_position);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        companyTitle = (EditText) findViewById(R.id.companyTitle);
        positionTitle = (EditText) findViewById(R.id.positionTitle);
        positionDescription = (EditText) findViewById(R.id.positionDescription);
        location = (EditText) findViewById(R.id.location);
        postPosition = (Button) findViewById(R.id.postPosition);

        postPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject position = new ParseObject("Position");
                position.put("companyTitle", companyTitle.getText().toString());
                position.put("positionTitle",positionTitle.getText().toString());
                position.put("description",positionDescription.getText().toString());
                position.put("location", location.getText().toString());
                position.put("bossId", ParseUser.getCurrentUser().getObjectId().toString());
                position.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        onBackPressed();
                    }
                });

            }
        });




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
