package cs.software.engineering.jobthirsty.profile;

import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;
import cs.software.engineering.jobthirsty.util.StringParser;

public class EmployeeProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    //Toolbar Variables
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;


    //UI Variables
    //Parent layout
    private RelativeLayout skillsParent;
    private RelativeLayout experienceParent;
    private RelativeLayout projectsParent;
    private RelativeLayout educationParent;
    private RelativeLayout activitiesParent;
    private RelativeLayout awardsParent;

    //Child layout
    private SkillsSection skillsSection;
    private ExperienceSection experienceSection;
    private ProjectsSection projectsSection;
    private EducationSection educationSection;
    private ActivitiesSection activitiesSection;
    private AwardSection awardsSection;

    //Entire Edit button
    private ImageButton editProfileBtn;
    private boolean editable;

    //Edit button for each section
    private ImageButton skillsEditBtn;
    private ImageButton experienceEditBtn;
    private ImageButton projectsEditBtn;
    private ImageButton educationEditBtn;
    private ImageButton activitiesEditBtn;
    private ImageButton awardsEditBtn;


    //Employee Data Variables
    private String firstName;
    private String lastName;
    private EditText location;
    private EditText biography;


    //Debug Variables
    String TAG = "datacheck";

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

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

        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinear);
        mainLinearLayout.setMinimumHeight(screenHeight - actionBarHeight);

        //Tool bar
        toolbar = getToolbar();
        toolbar.getBackground().setAlpha(100);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setExpandedTitleColor(0xFFffffff);


        //Edit button
        editProfileBtn = (ImageButton) findViewById(R.id.editProfileBtn);
        editable = false;


        //Parent layouts
        skillsParent = (RelativeLayout) findViewById(R.id.skillsParent);
        experienceParent = (RelativeLayout) findViewById(R.id.experienceParent);
        projectsParent = (RelativeLayout) findViewById(R.id.projectsParent);
        educationParent = (RelativeLayout) findViewById(R.id.educationParent);
        activitiesParent = (RelativeLayout) findViewById(R.id.activitiesParent);
        awardsParent = (RelativeLayout) findViewById(R.id.awardsParent);


        //Section layouts
        skillsSection = new SkillsSection(getApplicationContext());
        experienceSection = new ExperienceSection(getApplicationContext());
        projectsSection = new ProjectsSection(getApplicationContext());
        educationSection = new EducationSection(getApplicationContext());
        activitiesSection = new ActivitiesSection(getApplicationContext());
        awardsSection = new AwardSection(getApplicationContext());

        //Section edit buttons
        skillsEditBtn = (ImageButton) findViewById(R.id.skillsEditBtn);
        experienceEditBtn = (ImageButton) findViewById(R.id.experienceEditBtn);
        projectsEditBtn = (ImageButton) findViewById(R.id.projectsEditBtn);
        educationEditBtn = (ImageButton) findViewById(R.id.educationEditBtn);
        activitiesEditBtn = (ImageButton) findViewById(R.id.activitiesEditBtn);
        awardsEditBtn = (ImageButton) findViewById(R.id.awardsEditBtn);


        //Editable views
        location = (EditText) findViewById(R.id.location);
        biography = (EditText) findViewById(R.id.biography);
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
                if(editable)
                {
                    //enable edits for EditTexts
                    location.setEnabled(true);
                    location.setInputType(InputType.TYPE_CLASS_TEXT);
                    biography.setEnabled(true);

                    //show edit buttons for sections
                    showEditButtons();

                    //enable inside materials
                    skillsSection.enableEdit();
                    experienceSection.enableEdit();
                    projectsSection.enableEdit();
                    educationSection.enableEdit();
                    awardsSection.enableEdit();
                    activitiesSection.enableEdit();
                }
                else
                {
                    //disable edits for EditTexts
                    location.setEnabled(false);
                    biography.setEnabled(false);

                    //hide edit buttons for sections
                    hideEditButtons();

                    //disable inside materials
                    skillsSection.disableEdit();
                    experienceSection.disableEdit();
                    projectsSection.disableEdit();
                    educationSection.disableEdit();
                    awardsSection.disableEdit();
                    activitiesSection.disableEdit();

                    sendDataToParse();
                }
            }
        });

        //Section edit buttons
        skillsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillsParent.removeView(skillsSection);
                skillsSection.addElement("", "0", true);
                skillsParent.addView(skillsSection);
            }
        });

        experienceEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                experienceParent.removeView(experienceSection);
                experienceSection.addElement("", "", true);
                experienceParent.addView(experienceSection);
            }
        });

        projectsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectsParent.removeView(projectsSection);
                projectsSection.addElement("", "", true);
                projectsParent.addView(projectsSection);
            }
        });

        educationEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationParent.removeView(educationSection);
                educationSection.addElement("", "", "", true);
                educationParent.addView(educationSection);
            }
        });

        activitiesEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activitiesParent.removeView(activitiesSection);
                activitiesSection.addElement("", true);
                activitiesParent.addView(activitiesSection);
            }
        });

        awardsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                awardsParent.removeView(awardsSection);
                awardsSection.addElement("", true);
                awardsParent.addView(awardsSection);
            }
        });
    }

    private void showEditButtons()
    {
        skillsEditBtn.setVisibility(View.VISIBLE);
        experienceEditBtn.setVisibility(View.VISIBLE);
        projectsEditBtn.setVisibility(View.VISIBLE);
        educationEditBtn.setVisibility(View.VISIBLE);
        activitiesEditBtn.setVisibility(View.VISIBLE);
        awardsEditBtn.setVisibility(View.VISIBLE);
    }

    private void hideEditButtons()
    {
        skillsEditBtn.setVisibility(View.INVISIBLE);
        experienceEditBtn.setVisibility(View.INVISIBLE);
        projectsEditBtn.setVisibility(View.INVISIBLE);
        educationEditBtn.setVisibility(View.INVISIBLE);
        activitiesEditBtn.setVisibility(View.INVISIBLE);
        awardsEditBtn.setVisibility(View.INVISIBLE);
    }

    private void sendDataToParse()
    {
        final String locationData = location.getText().toString();
        final String biographyData = biography.getText().toString();

        final ArrayList<String> skillsData = (new StringParser(skillsSection.getData())).getConcated();
        final ArrayList<String> experienceData = (new StringParser(experienceSection.getData())).getConcated();
        final ArrayList<String> projectData = (new StringParser(projectsSection.getData())).getConcated();
        final ArrayList<String> educationData = (new StringParser(educationSection.getData())).getConcated();
        final ArrayList<String> activitiesData = activitiesSection.getData();
        final ArrayList<String> awardsData = awardsSection.getData();


        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
        q.getInBackground(ParseUser.getCurrentUser().get("dataId").toString(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                dataRow.put("location", locationData);
                dataRow.put("biography", biographyData);

                dataRow.put("skills", skillsData);
                dataRow.put("experience", experienceData);
                dataRow.put("projects", projectData);
                dataRow.put("education", educationData);
                dataRow.put("activities", activitiesData);
                dataRow.put("awards", awardsData);
                dataRow.saveInBackground();
            }
        });
    }

    //dataId need to be passed in to distinguish who's profile to load up
    private void retrieveDataFromParse(String dataId)
    {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("EmployeeData");
        q.getInBackground(dataId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dataRow, ParseException e) {
                String locationData = dataRow.get("location").toString();
                String biographyData = dataRow.get("biography").toString();

                ArrayList<String> skillsData = (ArrayList<String>) dataRow.get("skills");
                ArrayList<String> experienceData = (ArrayList<String>) dataRow.get("experience");
                ArrayList<String> projectData = (ArrayList<String>) dataRow.get("projects");
                ArrayList<String> educationData = (ArrayList<String>) dataRow.get("education");
                ArrayList<String> activitiesData = (ArrayList<String>) dataRow.get("activities");
                ArrayList<String> awardsData = (ArrayList<String>) dataRow.get("awards");

                location.setText(locationData);
                location.setEnabled(false);
                biography.setText(biographyData);
                biography.setEnabled(false);

                skillsParent.removeView(skillsSection);
                skillsSection.setData(skillsData);
                skillsParent.addView(skillsSection);

                experienceParent.removeView(experienceSection);
                experienceSection.setData(experienceData);
                experienceParent.addView(experienceSection);

                projectsParent.removeView(projectsSection);
                projectsSection.setData(projectData);
                projectsParent.addView(projectsSection);

                educationParent.removeView(educationSection);
                educationSection.setData(educationData);
                educationParent.addView(educationSection);

                activitiesParent.removeView(activitiesSection);
                activitiesSection.setData(activitiesData);
                activitiesParent.addView(activitiesSection);

                awardsParent.removeView(awardsSection);
                awardsSection.setData(awardsData);
                awardsParent.addView(awardsSection);
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

    private static void disableTouchTheft(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }
    //[END] ----------------------------------------------------------------------------------------
}
