package cs.software.engineering.jobthirsty.profile;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;
import cs.software.engineering.jobthirsty.util.StringParser;

public class EmployerProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    //Toolbar Variables
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private ImageButton editProfileBtn;
    private boolean editable;

    //UI Variables
    private EditText location;
    private EditText biography;

    // Parent layout
    private RelativeLayout jobsParent;

    // Child layout
    private JobsSection jobsSection;

    //Employer Data Variables
    private String firstName;
    private String lastName;

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        initialize();
        loadProfilePage();
        setListeners();
    }
    //[END] ----------------------------------------------------------------------------------------


    //HELPER FUNCTIONS[START] ----------------------------------------------------------------------
    private void initialize()
    {
        //set minimum height for bottom half to enable scrolling
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;

        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLinear);
        mainLayout.setMinimumHeight(screenHeight - actionBarHeight);

        //Tool bar
        toolbar = getToolbar();
        toolbar.getBackground().setAlpha(100);

        firstName = ParseUser.getCurrentUser().get("firstName").toString();
        lastName = ParseUser.getCurrentUser().get("lastName").toString();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setTitle(firstName + " " + lastName);
        collapsingToolbarLayout.setExpandedTitleColor(0xFFffffff);


        //Edit button
        editProfileBtn = (ImageButton) findViewById(R.id.editProfileBtn);
        editable = false;

        //Editable views
        location = (EditText) findViewById(R.id.location);
        biography = (EditText) findViewById(R.id.biography);

        //Parent layouts
        jobsParent = (RelativeLayout) findViewById(R.id.jobsParent);

        //Section layouts
        jobsSection = new JobsSection(getApplicationContext());
    }

    private void setListeners()
    {
        //Overall edit button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggle
                editable = !editable;

                //if editable
                if (editable) {
                    //enable edits for EditTexts
                    location.setEnabled(true);
                    location.setInputType(InputType.TYPE_CLASS_TEXT);
                    biography.setEnabled(true);

                } else {
                    //disable edits for EditTexts
                    location.setEnabled(false);
                    biography.setEnabled(false);
                    sendDataToParse();
                }
            }
        });
    }

    private void sendDataToParse()
    {
        final String locationData = location.getText().toString();
        final String biographyData = biography.getText().toString();

        final ArrayList<String> jobsData = jobsSection.getData();

        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployerData");
        q.getInBackground(ParseUser.getCurrentUser().get("dataId").toString(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                dataRow.put("location", locationData);
                dataRow.put("biography", biographyData);

                dataRow.put("jobPostings", jobsData);
                dataRow.saveInBackground();
            }
        });
    }

    //dataId need to be passed in to distinguish who's profile to load up
    private void retrieveDataFromParse(String dataId)
    {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployerData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                String locationData = dataRow.get("location") == null ? "" : dataRow.get("location").toString();
                String biographyData = dataRow.get("biography") == null ? "" : dataRow.get("biography").toString();

                final ArrayList<String> jobsData = new ArrayList<>();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Position");
                query.whereEqualTo("bossId", ParseUser.getCurrentUser().getObjectId());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> rows, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < rows.size(); ++i) {
                                ParseObject po = rows.get(i);
                                String jobTitle = po.get("positionTitle").toString();
                                jobsData.add(jobTitle);
                            }


                            jobsParent.removeView(jobsSection);
                            jobsSection.setData(jobsData);
                            jobsParent.addView(jobsSection);
                        } else {
                            // error
                        }
                    }
                });

                location.setText(locationData);
                location.setEnabled(false);
                biography.setText(biographyData);
                biography.setEnabled(false);

            }
        });
    }

    //Load profile page
    private void loadProfilePage()
    {
        Bundle extras = getIntent().getExtras();
        firstName = extras.getString("firstName");
        lastName = extras.getString("lastName");
        String dataId = extras.getString("dataId");

        //display profile's name
        collapsingToolbarLayout.setTitle(firstName + " " + lastName);

        retrieveDataFromParse(dataId);
    }
    //[END] ----------------------------------------------------------------------------------------
}
