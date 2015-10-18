package cs.software.engineering.jobthirsty.profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.Login;
import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class EmployeeProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    //Toolbar Variables
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private ImageButton editProfileBtn;
    private boolean editable;

    //UI Variables
    private RelativeLayout skillsSection;
    private RelativeLayout experienceSection;
    private RelativeLayout projectsSection;
    private RelativeLayout educationSection;
    private RelativeLayout activitiesSection;
    private RelativeLayout awardsSection;
    private ImageButton skillsEditBtn;
    private ImageButton experienceEditBtn;
    private ImageButton projectsEditBtn;
    private ImageButton educationEditBtn;
    private ImageButton activitiesEditBtn;
    private ImageButton awardsEditBtn;
    private EditText location;
    private EditText biography;

    //Employee Data Variables
    private String firstName;
    private String lastName;

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

        //Section layouts
        skillsSection = (RelativeLayout) findViewById(R.id.skillsSection);
        experienceSection = (RelativeLayout) findViewById(R.id.experienceSection);
        projectsSection = (RelativeLayout) findViewById(R.id.projectsSection);
        educationSection = (RelativeLayout) findViewById(R.id.educationSection);
        activitiesSection = (RelativeLayout) findViewById(R.id.activitiesSection);
        awardsSection = (RelativeLayout) findViewById(R.id.awardsSection);

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
                    location.setInputType(InputType.TYPE_CLASS_TEXT);
                    biography.setInputType(InputType.TYPE_CLASS_TEXT);

                    //show edit buttons for sections
                    showEditButtons();
                }
                else
                {
                    //disable edits for EditTexts
                    location.setEnabled(false);
                    biography.setEnabled(false);

                    //hide edit buttons for sections
                    hideEditButtons();
                }
            }
        });

        //Section edit buttons
        skillsEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Expand layout
                skillsSection.getLayoutParams().height += 100;
                skillsSection.requestLayout();

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                //set up Edit Text
                EditText et = new EditText(getApplicationContext());
                et.setLayoutParams(layoutParams);
                et.setBackgroundColor(0xFFffffff);
                et.setText(String.format("%s", "assdfasd"));
                et.setTextColor(0xFF000000);
                et.requestFocus(); //put on cursor

                //add the skill
                skillsSection.addView(et);
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
    //[END] ----------------------------------------------------------------------------------------
}
