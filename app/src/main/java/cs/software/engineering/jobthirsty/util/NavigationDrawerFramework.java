package cs.software.engineering.jobthirsty.util;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.Login;
import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.SettingsActivity;
import cs.software.engineering.jobthirsty.applied_workers.AppliedWorkers;
import cs.software.engineering.jobthirsty.company_page.CreateCompanyPage;
import cs.software.engineering.jobthirsty.company_page.FindCompany;
import cs.software.engineering.jobthirsty.connections.ConnectionRequest;
import cs.software.engineering.jobthirsty.find_workers.FindWorker;
import cs.software.engineering.jobthirsty.job_position.CreateJobPosition;
import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;
import cs.software.engineering.jobthirsty.profile.EmployerProfileActivity;

public class NavigationDrawerFramework extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //NAVIGATION DRAWER VARIABLES
    //TOOLBAR VARIABLES
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private final Handler mDrawerActionHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private View mHeaderLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private NavigationView navigationView;

    protected FrameLayout mContent;
    protected Toolbar toolbar;

    boolean isEmployer = false;

    @Override
    public void setContentView(final int layoutResID) {


        // Hide menu items if employee
        if ( (Boolean) ParseUser.getCurrentUser().get("isBoss")) {
            isEmployer = true;
        }

        // Your base layout here
        if (isEmployer) {
            mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.framework_navigation_drawer, null);
        } else {
            mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.framework_navigation_drawer_employee, null);
        }
        mContent = (FrameLayout) mDrawerLayout.findViewById(R.id.content);

        // Setting the content of layout your provided to the content frame
        getLayoutInflater().inflate(layoutResID, mContent, true);
        super.setContentView(mDrawerLayout);

        // Setting header variables
        mHeaderLayout = getLayoutInflater().inflate(R.layout.framework_navigation_header, null);
        String firstName = ParseUser.getCurrentUser().get("firstName").toString();
        String lastName = ParseUser.getCurrentUser().get("lastName").toString();

        final String temp = firstName +" " + lastName;
        Log.d("name", temp);


        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Update TextView here
                TextView fullname = (TextView) mHeaderLayout.findViewById(R.id.fullName);
                fullname.setText(temp);
            }
        });


        // Setting toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if( toolbar != null )
            setSupportActionBar(toolbar);

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


        // Hide menu items
        if (!isEmployer) {
            MenuItem positions = (MenuItem) findViewById(R.id.createPosition);
            if (positions != null) {
                positions.setVisible(false);
                invalidateOptionsMenu();
            }
        }

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_profile, menu);

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("EmployerData");
//        query.getInBackground(objectId, new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject p, ParseException e) {
//                if (e == null) {
//                    Log.d("JobThirsty", "Employer!");
//                    isEmployer = true;
//                } else {
//                    Log.d("JobThirsty", "Employee...");
//                }
//            }
//        });

        return true;
    }

    // Navigation Drawer Code /===========================================================================
    //====================================================================================================

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // Update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // Allow some time after closing the drawer before performing real navigation
        // So the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }


    // Navigation Logic Goes Here
    private void navigate(final int itemId) {

        Intent intent = new Intent();

        switch(itemId){
            case R.id.profile:
                // If user exist and authenticated, send user to Welcome.class
                //  Need to check flag to send user to appropriate activty (worker or boss)
                boolean isBoss = (boolean) ParseUser.getCurrentUser().get("isBoss");

                intent = isBoss ? new Intent(this, EmployerProfileActivity.class)
                                : new Intent(this, EmployeeProfileActivity.class);


                intent.putExtra("userId", ParseUser.getCurrentUser().getObjectId());
                intent.putExtra("isYourself", true);
                break;
            case R.id.connection_request:
                intent = new Intent(this, ConnectionRequest.class);
                break;
            case R.id.find_worker:
                intent = new Intent(this, FindWorker.class);
                break;
            case R.id.createPosition:
                intent = new Intent(this, CreateJobPosition.class);
                break;
            case R.id.applied_workers:
                intent = new Intent(this, AppliedWorkers.class);
                break;
            case R.id.create_company:
                intent = new Intent(this, CreateCompanyPage.class);
                break;
            case R.id.find_company:
                intent = new Intent(this, FindCompany.class);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.logout:
                ParseUser.logOut();
                intent = new Intent(this, Login.class);
                finish();
                break;
        }

        startActivity(intent);
    }

    //====================================================================================================


    protected Toolbar getToolbar(){
        return toolbar;
    }



}
