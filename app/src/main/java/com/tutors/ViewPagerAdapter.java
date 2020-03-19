package com.tutors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Princess on 7/27/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> my_fragmentList=new ArrayList<>();
    private List<String> my_tabMenu = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return   my_fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return my_fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return my_tabMenu.get(position);
    }

    void myset(Fragment f, String title){
        my_fragmentList.add(f);
        my_tabMenu.add(title);
    }
}


