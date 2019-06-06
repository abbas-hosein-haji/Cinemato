package com.abhj.cinemato.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abhj.cinemato.fragments.CastFragment;
import com.abhj.cinemato.fragments.InfoFragment;
import com.abhj.cinemato.fragments.ReviewsFragment;

public class DetailFragmentPagerAdapter extends FragmentPagerAdapter {

    private int mId;

    private String tabTitles[] = new String[]{"INFO", "CAST", "REVIEWS"};


    public DetailFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return InfoFragment.newInstance(mId);
        } else if (position == 1) {
            return CastFragment.newInstance(mId);
        } else {
            return ReviewsFragment.newInstance(mId);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

}
