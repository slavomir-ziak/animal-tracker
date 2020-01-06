package com.wecode.animaltracker.activity;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wecode.animaltracker.fragment.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    private final Activity activity;

    public ViewPagerAdapter(FragmentManager manager, Activity activity) {
        super(manager);
        this.activity = activity;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return (android.support.v4.app.Fragment) mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Fragment fragment = mFragmentList.get(position);
        if (fragment.getName() != null) {
            return fragment.getName();
        }
        return activity.getResources().getString(fragment.getNameResourceId());
    }
}