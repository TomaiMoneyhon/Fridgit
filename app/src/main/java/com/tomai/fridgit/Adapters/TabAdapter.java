package com.tomai.fridgit.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tomai.fridgit.Fragments.FridgeListFragment;
import com.tomai.fridgit.Fragments.ShoppingListFragment;

/**
 * Created by admin on 9/5/15.
 */
public class TabAdapter extends FragmentPagerAdapter {


    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ShoppingListFragment();
            case 1:
                return new FridgeListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
