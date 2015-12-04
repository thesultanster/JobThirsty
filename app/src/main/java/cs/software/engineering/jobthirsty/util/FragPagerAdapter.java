package cs.software.engineering.jobthirsty.util;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cs.software.engineering.jobthirsty.connections.Connections;
import cs.software.engineering.jobthirsty.job_position.Positions;
import cs.software.engineering.jobthirsty.newsfeed.Newsfeed;

/** FragmentPagerAdapter
 *      Description: Adapter for managing fragmentPager
 *
 */
public class FragPagerAdapter extends FragmentPagerAdapter
{
    //PRIVATE VARIABLES
    // fixed static variables
    final int PAGE_COUNT = 3;
    private String tabTitles[] = {"Newsfeed", "Positions", "Connections"};
    List<Fragment> frags;


    //OVERRIDE FUNCTIONS [START] -------------------------------------------------------------------
    @Override
    public int getCount(){ return PAGE_COUNT; }

    @Override
    public Fragment getItem(int position)
    {
        return frags.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
    //[END] ----------------------------------------------------------------------------------------



    //CONSTRUCTORS [START] -------------------------------------------------------------------------
    public FragPagerAdapter(FragmentManager fm) {
        super(fm);

        //initialize
        frags = new ArrayList<>();
        frags.add(new Newsfeed());
        frags.add(new Positions());
        frags.add(new Connections());
    }
    //[END] ----------------------------------------------------------------------------------------




}
