package cs.software.engineering.jobthirsty.job_position;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.R;

public class ViewJobPosition extends AppCompatActivity {

    private TextView editPositionsBtn;
    FloatingActionButton fab;
    private EditText jobTitle;
    private EditText companyTitle;
    private EditText location;
    private EditText description;

    boolean editable = false;
    ParseObject positionObj = null;

    String positionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_position);

        // Setup layout
        Bundle extras = getIntent().getExtras();
        String positionTitle="Example Title";
        String positionDescription="This is the description";
        String positionLocation="Example Location";
        String positionCompany="Example Company";


        boolean isOwner = false;

        if (extras != null) {
            positionTitle = extras.getString("positionTitle");
            positionCompany = extras.getString("positionCompany");
            positionDescription = extras.getString("positionDescription");
            positionLocation = extras.getString("positionLocation");
            positionId = extras.getString("positionId");
            isOwner = extras.getBoolean("isOwner");
        }

        // Get Parse Object
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Position");
        query.getInBackground(positionId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject p, ParseException e) {
                if (e == null) {
                    positionObj = p;
                    // object will be your game score
                } else {
                    // something went wrong
                }
            }
        });

        // Find all View objects
        jobTitle = (EditText) findViewById(R.id.jobTitle);
        location = (EditText) findViewById(R.id.location);
        companyTitle = (EditText) findViewById(R.id.companyTitle);
        description = (EditText) findViewById(R.id.descriptionText);

        jobTitle.setText(positionTitle);
        location.setText(positionLocation);
        companyTitle.setText(positionCompany);
        description.setText(positionDescription);

        description.setEnabled(false);
        description.setSingleLine(false);

        // If user is owner, display edit button, else show the apply button
        if (isOwner) {
            editPositionsBtn = (TextView) findViewById(R.id.editPositionBtn);
            editPositionsBtn.setVisibility(View.VISIBLE);
        }
        else {
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setListeners();
    }

    private void setListeners() {

        if (editPositionsBtn != null) {
            editPositionsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toggle
                    editable = !editable;

                    //if editable
                    if (editable) {
                        jobTitle.setEnabled(true);
                        jobTitle.setInputType(InputType.TYPE_CLASS_TEXT);

                        companyTitle.setEnabled(true);
                        companyTitle.setInputType(InputType.TYPE_CLASS_TEXT);

                        location.setEnabled(true);
                        location.setInputType(InputType.TYPE_CLASS_TEXT);

                        description.setEnabled(true);

                        description.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                        description.setSingleLine(false);

                        editPositionsBtn.setText("Save");

                    } else {

                        //disable edits for EditTexts
                        jobTitle.setEnabled(false);
                        companyTitle.setEnabled(false);
                        location.setEnabled(false);
                        description.setEnabled(false);

                        //hide edit buttons for sections
                        editPositionsBtn.setText("Edit");
                        sendDataToParse();
                    }
                }
            });
        }
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Thank You For Applying", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Apply();
                }
            });
        }
    }

    public void Apply(){

        ParseUser user = ParseUser.getCurrentUser();

        ParseObject applicant = new ParseObject("AppliedWorkers");
        applicant.put("name", user.get("firstName").toString() + user.get("lastName").toString());
        applicant.put("position", jobTitle.getText().toString());
        applicant.put("applicantId", user.getObjectId().toString());
        applicant.put("positionId", positionId);
        applicant.saveInBackground();

        ParseObject news = new ParseObject("Newsfeed");
        news.put("title", ParseUser.getCurrentUser().get("firstName") );
        news.put("update", ParseUser.getCurrentUser().get("firstName") + " Applied for " + jobTitle );
        news.saveInBackground();

    }

    private void sendDataToParse() {
        final String jobData = jobTitle.getText().toString();
        final String companyData = companyTitle.getText().toString();
        final String locationData = location.getText().toString();
        final String descriptionData = description.getText().toString();

        positionObj.put("positionTitle", jobData);
        positionObj.put("companyTitle", companyData);
        positionObj.put("description", descriptionData);
        positionObj.put("location", locationData);
        positionObj.saveInBackground();
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
