package com.example.cinemato.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.cinemato.fragments.MainFragment;

public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int mColumnCount = 1;

    private String tabTitles[] = new String[]{"NOW PLAYING", "POPULAR",
            "UPCOMING", "TOP RATED"};


    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(mColumnCount, position);

    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
    }
}
