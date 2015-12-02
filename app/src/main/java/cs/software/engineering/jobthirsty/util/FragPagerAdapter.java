package cs.software.engineering.jobthirsty.util;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cs.software.engineering.jobthirsty.connections.Connections;
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
    private String tabTitles[] = {"Newsfeed", "Profile", "Connections"};
    private List<Fragment> fragments;


    //OVERRIDE FUNCTIONS [START] -------------------------------------------------------------------
    @Override
    public int getCount(){ return PAGE_COUNT; }

    @Override
    public Fragment getItem(int position)
    {
        Newsfeed tab1 = new Newsfeed();
        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab1;
            case 2:
                return new Connections();
            default:
                return null;
        }
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
    }
    //[END] ----------------------------------------------------------------------------------------




}
