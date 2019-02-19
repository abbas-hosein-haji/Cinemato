package com.example.cinemato.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cinemato.fragments.PersonInfoFragment;
import com.example.cinemato.fragments.PersonMovieFragment;

public class PersonFragmentPagerAdapter extends FragmentPagerAdapter {

    private int mId;

    private Bundle mBundle;

    private String tabTitles[] = new String[]{"INFO", "MOVIES"};


    public PersonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PersonInfoFragment.newInstance(mBundle);
        } else {
            return PersonMovieFragment.newInstance(mBundle);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }
}
