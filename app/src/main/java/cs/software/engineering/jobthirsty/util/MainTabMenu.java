package cs.software.engineering.jobthirsty.util;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import cs.software.engineering.jobthirsty.R;


public class MainTabMenu extends NavigationDrawerFramework {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager()));

        //sliding strip
        TabLayout tabLayout = (TabLayout) findViewById(R.id.slidingTabStrip);
        tabLayout.setupWithViewPager(viewPager);
    }
}
