package com.example.cinemato.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.cinemato.fragments.MainFragment;
import com.example.cinemato.fragments.SearchActorFragment;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {

    private String mMovieQuery;


    private String tabTitles[] = new String[]{"MOVIES", "ACTORS"};

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            int mPositiom = 4;
            return MainFragment.newInstance(mPositiom, mMovieQuery);
        } else {
            return SearchActorFragment.newInstance(mMovieQuery);

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


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }


    public void setmMovieQuery(String mMovieQuery) {
        this.mMovieQuery = mMovieQuery;
    }

}
