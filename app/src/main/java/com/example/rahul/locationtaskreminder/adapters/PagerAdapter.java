package com.example.rahul.locationtaskreminder.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rahul.locationtaskreminder.fragments.CompletedTask;
import com.example.rahul.locationtaskreminder.fragments.DashboardFragment;
import com.example.rahul.locationtaskreminder.fragments.PendingTaskFragment;

/**
 * Created by Rahul on 12/24/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:
                return new PendingTaskFragment();
            case 2:
                return new CompletedTask();
            default:
                return new DashboardFragment();
        }
    }

}
