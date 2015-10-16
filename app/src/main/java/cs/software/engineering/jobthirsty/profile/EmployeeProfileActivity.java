package cs.software.engineering.jobthirsty.profile;

import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class EmployeeProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    // Layout Variables
    LinearLayout skillsSection;
    LinearLayout experienceSection;
    LinearLayout projectsSection;
    LinearLayout educationSection;
    LinearLayout activitiesSection;
    LinearLayout awardsSection;

    boolean skillsExpanded;
    boolean experienceExpanded;
    boolean projectsExpanded;
    boolean educationExpanded;
    boolean activitiesExpanded;
    boolean awardsExpanded;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    //Employee Data Variables
    String firstName;
    String lastName;

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        initialize();
        setListeners();

    }



    //[END] ----------------------------------------------------------------------------------------


    //HELPER FUNCTIONS[START] ----------------------------------------------------------------------
    private void initialize()
    {
        //initialize sections
        experienceSection = (LinearLayout) findViewById(R.id.experienceSection);
        skillsSection = (LinearLayout) findViewById(R.id.skillsSection);
        projectsSection = (LinearLayout) findViewById(R.id.projectsSection);
        educationSection = (LinearLayout) findViewById(R.id.educationSection);
        activitiesSection = (LinearLayout) findViewById(R.id.activitiesSection);
        awardsSection = (LinearLayout) findViewById(R.id.awardsSection);

        //initialize flags
        experienceExpanded = false;
        skillsExpanded = false;
        projectsExpanded = false;
        educationExpanded = false;
        activitiesExpanded = false;
        awardsExpanded = false;

        //Tool bar
        toolbar = getToolbar();
        toolbar.getBackground().setAlpha(100);

        firstName = ParseUser.getCurrentUser().get("firstName").toString();
        lastName = ParseUser.getCurrentUser().get("lastName").toString();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(firstName + " " + lastName);
    }

    private void setListeners()
    {
        experienceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expand
                //set the new size
                experienceSection.getLayoutParams().height = !experienceExpanded ? 600 : 120;
                experienceExpanded = !experienceExpanded; //flag toggle

                //update the layout
                experienceSection.requestLayout();
            }
        });

        skillsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expand
                //set the new size
                skillsSection.getLayoutParams().height = !skillsExpanded ? 600 : 120;
                skillsExpanded = !skillsExpanded; //flag toggle

                //update the layout
                skillsSection.requestLayout();
            }
        });

        projectsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expand
                //set the new size
                projectsSection.getLayoutParams().height = !projectsExpanded ? 600 : 120;
                projectsExpanded = !projectsExpanded; //flag toggle

                //update the layout
                projectsSection.requestLayout();
            }
        });

        educationSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expand
                //set the new size
                educationSection.getLayoutParams().height = !educationExpanded ? 600 : 120;
                educationExpanded = !educationExpanded; //flag toggle

                //update the layout
                educationSection.requestLayout();
            }
        });


        activitiesSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expand
                //set the new size
                activitiesSection.getLayoutParams().height = !activitiesExpanded ? 600 : 120;
                activitiesExpanded = !activitiesExpanded; //flag toggle

                //update the layout
                activitiesSection.requestLayout();
            }
        });

        awardsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expand
                //set the new size
                awardsSection.getLayoutParams().height = !awardsExpanded ? 600 : 120;
                awardsExpanded = !awardsExpanded; //flag toggle

                //update the layout
                awardsSection.requestLayout();
            }
        });
    }
    //[END] ----------------------------------------------------------------------------------------




}
