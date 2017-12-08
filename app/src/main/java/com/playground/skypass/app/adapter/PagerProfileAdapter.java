package com.playground.skypass.app.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.playground.skypass.fragment.MyCheckInFragment;
import com.playground.skypass.fragment.MyRewardFragment;

/**
 * Created by Edwin on 15/02/2015.
 */
public class PagerProfileAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int NumbOfTabs;

    public PagerProfileAdapter(FragmentManager fm, CharSequence mTitles[],
                               int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new MyCheckInFragment();
        } else {
            return new MyRewardFragment();
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}