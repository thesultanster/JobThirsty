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

import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class EmployerProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    private static String bullet = "\u2022";

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

    //Edit button for each section
    private ImageButton jobsEditBtn;

    //Employer Data Variables
    private String firstName;
    private String lastName;

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        initialize();
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
        jobsSection = new JobsSection(getApplicationContext(), firstName, lastName);

        //Section edit buttons
        jobsEditBtn = (ImageButton) findViewById(R.id.jobsEditBtn);
        
        // Populate dynamic layout
//        jobsParent.removeView(jobsSection);
//        jobsSection.addElement("", "", "", false);
//        jobsParent.addView(jobsSection);
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
                    location.setInputType(InputType.TYPE_CLASS_TEXT);
                    biography.setInputType(InputType.TYPE_CLASS_TEXT);

                    //show edit buttons for sections
                    showEditButtons();

                    //enable inside materials
                    jobsSection.enableEdit();
                    
                } else {
                    //disable edits for EditTexts
                    location.setEnabled(false);
                    biography.setEnabled(false);

                    //hide edit buttons for sections
                    hideEditButtons();

                    //disable inside materials
                    jobsSection.disableEdit();

//                    sendDataToParse();
                }
            }
        });

        jobsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobsParent.removeView(jobsSection);
                jobsSection.addElement("", "", true);
                jobsParent.addView(jobsSection);
            }
        });
    }

    private void showEditButtons()
    {
        jobsEditBtn.setVisibility(View.VISIBLE);
    }

    private void hideEditButtons()
    {
        jobsEditBtn.setVisibility(View.INVISIBLE);
    }
    //[END] ----------------------------------------------------------------------------------------
}
