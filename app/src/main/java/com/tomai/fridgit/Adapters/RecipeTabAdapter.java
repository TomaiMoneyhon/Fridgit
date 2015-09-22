package com.tomai.fridgit.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tomai.fridgit.Fragments.DirectionsForRecipeFragment;
import com.tomai.fridgit.Fragments.IngredientsForRecipeFragment;

/**
 * Created by admin on 9/22/15.
 */
public class RecipeTabAdapter extends FragmentPagerAdapter {

    public RecipeTabAdapter(FragmentManager fm) {super(fm);}

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IngredientsForRecipeFragment();
            case 1:
                return new DirectionsForRecipeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
