package com.playground.skypass.app.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.playground.skypass.fragment.PoiListFragment;
import com.playground.skypass.model.ModelPoiAll;

import java.util.List;

/**
 * Created by Edwin on 15/02/2015.
 */
public class PagerPoiAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    List<ModelPoiAll.Data> data;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public PagerPoiAdapter(FragmentManager fm, List<ModelPoiAll.Data> data, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.data = data;

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        PoiListFragment tab = new PoiListFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("places", data.get(position));

        tab.setArguments(bundle);

        return tab;

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}