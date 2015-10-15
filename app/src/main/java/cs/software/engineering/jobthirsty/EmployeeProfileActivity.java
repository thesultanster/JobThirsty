package cs.software.engineering.jobthirsty;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class EmployeeProfileActivity extends NavigationDrawerFramework{

    //PRIVATE VARIABLES
    // Layout Variables
    LinearLayout skillsSection;
    LinearLayout experienceSection;
    LinearLayout projectsSection;
    LinearLayout educationSection;

    boolean skillsExpanded;
    boolean experienceExpanded;
    boolean projectsExpanded;
    boolean educationExpanded;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    //OVERRIDE [START] -----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        initialize();
        setListeners();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        toolbar = getToolbar();
        toolbar.getBackground().setAlpha(100);

        collapsingToolbarLayout.setTitle("Sultan Khan");

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

        //initialize flags
        experienceExpanded = false;
        skillsExpanded = false;
        projectsExpanded = false;
        educationExpanded = false;
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
    }
    //[END] ----------------------------------------------------------------------------------------




}
