package com.playground.skypass.app.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.playground.skypass.fragment.EventFragment;
import com.playground.skypass.fragment.MyCheckInFragment;
import com.playground.skypass.fragment.MyRewardFragment;
import com.playground.skypass.fragment.PromotionFragment;

/**
 * Created by Edwin on 15/02/2015.
 */
public class PagerPromoAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int NumbOfTabs;

    public PagerPromoAdapter(FragmentManager fm, CharSequence mTitles[],
                             int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0){
            return new EventFragment();
        } else {
            return new PromotionFragment();
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