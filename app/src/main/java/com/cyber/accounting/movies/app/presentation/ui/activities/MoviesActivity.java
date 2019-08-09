package com.cyber.accounting.movies.app.presentation.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cyber.accounting.movies.app.BuildConfig;
import com.cyber.accounting.movies.app.R;
import com.cyber.accounting.movies.app.presentation.ui.fragments.MoviesFragment;
import com.cyber.accounting.movies.app.presentation.ui.utils.BottomNavigationViewHelper;
import com.cyber.accounting.movies.app.presentation.ui.utils.Constants;
import com.cyber.accounting.movies.app.presentation.ui.utils.DashboardNavigationController;
import com.cyber.accounting.movies.app.presentation.ui.views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private DashboardNavigationController controller;
    private MenuItem previousMenuItem;
    private boolean doubleBackToExitPressedOnce;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        pager.addOnPageChangeListener(this);
        navigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        controller = new DashboardNavigationController(navigation);
        setupSupportedActionBar(toolbar);
        loadFragments();

        setActionBarTitle(R.string.title_popular);
        if (getIntent().getBooleanExtra(Constants.KEY_OPEN_NOTIFICATION, false)) {
            navigation.setSelectedItemId(R.id.navigation_top_rated);
        }
    }

    @Override
    protected View getSnackBarAnchorView() {
        return pager;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.message_exit, Toast.LENGTH_SHORT).show();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.navigation_popular:
                pager.setCurrentItem(0);
                return true;
            case R.id.navigation_top_rated:
                pager.setCurrentItem(1);
                return true;
            case R.id.navigation_upcoming:
                pager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    public void loadFragments() {
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.titles.clear();
        sectionsPagerAdapter.fragments.clear();
        sectionsPagerAdapter.notifyDataSetChanged();

        sectionsPagerAdapter.addFrag(MoviesFragment.newInstance(BuildConfig.FILTER_POPULAR), getString(R.string.title_popular));
        sectionsPagerAdapter.addFrag(MoviesFragment.newInstance(BuildConfig.FILTER_TOP_RATED), getString(R.string.title_top_rated));
        sectionsPagerAdapter.addFrag(MoviesFragment.newInstance(BuildConfig.FILTER_UPCOMING), getString(R.string.title_upcoming));
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(sectionsPagerAdapter);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments;
        private final List<String> titles;

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
            clearFragments(manager);
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFrag(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        void clearFragments(FragmentManager manager) {
            List<Fragment> fragments = manager.getFragments();
            if (fragments != null) {
                FragmentTransaction transaction = manager.beginTransaction();
                for (Fragment fragment : fragments) {
                }
                transaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (previousMenuItem != null) {
            previousMenuItem.setChecked(false);
        } else {
            navigation.getMenu().getItem(0).setChecked(false);
        }

        controller.setNavigationTab(position);
        previousMenuItem = navigation.getMenu().getItem(position);
        setActionBarTitle(sectionsPagerAdapter.titles.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
