package cs.software.engineering.jobthirsty.util;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import cs.software.engineering.jobthirsty.find.Find;
import cs.software.engineering.jobthirsty.find.FindPositions;
import cs.software.engineering.jobthirsty.mail.Mail;
import cs.software.engineering.jobthirsty.newsfeed.Newsfeed;
import cs.software.engineering.jobthirsty.profile.EmployeeProfileActivity;
import cs.software.engineering.jobthirsty.R;

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

    protected FrameLayout mContent;
    protected Toolbar toolbar;

    @Override
    public void setContentView(final int layoutResID) {
        // Your base layout here
        mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.framework_navigation_drawer, null);
        mContent = (FrameLayout) mDrawerLayout.findViewById(R.id.content);

        // Setting the content of layout your provided to the content frame
        getLayoutInflater().inflate(layoutResID, mContent, true);
        super.setContentView(mDrawerLayout);

        // Setting header variables
        mHeaderLayout = getLayoutInflater().inflate(R.layout.framework_navigation_header, null);
        String firstName = ParseUser.getCurrentUser().get("firstName").toString();
        String lastName = ParseUser.getCurrentUser().get("lastName").toString();
        final String temp = firstName + lastName;
        Log.d("name", temp);


        runOnUiThread(new Runnable() {
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
 /*
        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.profile;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }
        */

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        //navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

       // navigate(mNavItemId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_profile, menu);
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
                //TODO: Find if employer or employee and go to respective activity. Ex: if( parseUser != Employee)
                intent = new Intent(this, EmployeeProfileActivity.class);
                break;
            case R.id.find_position:
                intent = new Intent(this, FindPositions.class);
                break;
            case R.id.mail:
                intent = new Intent(this, Mail.class);
                break;
            case R.id.newsfeed:
                intent = new Intent(this, Newsfeed.class);
                break;
        }

        startActivity(intent);

    }

    //====================================================================================================


    protected Toolbar getToolbar(){
        return toolbar;
    }



}
