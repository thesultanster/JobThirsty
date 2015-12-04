package cs.software.engineering.jobthirsty.main_tab_menu;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cs.software.engineering.jobthirsty.R;
import cs.software.engineering.jobthirsty.util.NavigationDrawerFramework;


public class MainTabMenu extends NavigationDrawerFramework {
    private ViewPager viewPager;
    private SearchView searchView;
    private MenuItem searchItem;
    private FragPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragPagerAdapter(fm);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        //sliding strip
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.slidingTabStrip);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //hide the search if you are not on positions tab
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (searchView != null) {
                    int currentPage = tab.getPosition();
                    switch (currentPage) {
                        case 0:
                            viewPager.setCurrentItem(0);
                            searchItem.setVisible(false);
                            break;
                        case 2:
                            viewPager.setCurrentItem(2);
                            searchItem.setVisible(false);
                            break;
                        case 1:
                            viewPager.setCurrentItem(1);
                            searchItem.setVisible(true);
                            break;
                    }
                    tabLayout.requestLayout();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_find_worker, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);

        SearchManager searchManager = (SearchManager) MainTabMenu.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainTabMenu.this.getComponentName()));
        }
        adapter.setSearchView(searchView);

        return super.onCreateOptionsMenu(menu);
    }
}
