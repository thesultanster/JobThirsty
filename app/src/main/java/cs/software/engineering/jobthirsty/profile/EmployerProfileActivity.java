package cs.software.engineering.jobthirsty.profile;

import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
                }
                else
                {
                    //disable edits for EditTexts
                    location.setEnabled(false);
                    biography.setEnabled(false);
                }
            }
        });
    }
    //[END] ----------------------------------------------------------------------------------------
}
