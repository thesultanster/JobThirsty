package cs.software.engineering.jobthirsty.profile;

import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;

public class EmployeeProfileActivity extends NavigationDrawerFramework {

    //PRIVATE VARIABLES
    //Toolbar Variables
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    ImageButton editProfileBtn;

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
        //Edit button
        editProfileBtn = (ImageButton) findViewById(R.id.editProfileBtn);

        //Tool bar
        toolbar = getToolbar();
        toolbar.getBackground().setAlpha(100);
        toolbar.setTitleTextColor(0xFFffffff);
        toolbar.requestLayout();

        firstName = ParseUser.getCurrentUser().get("firstName").toString();
        lastName = ParseUser.getCurrentUser().get("lastName").toString();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setTitle(firstName + " " + lastName);
    }

    private void setListeners()
    {
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(this, )
            }
        });
    }
    //[END] ----------------------------------------------------------------------------------------




}
