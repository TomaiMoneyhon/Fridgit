package com.tomai.fridgit.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomai.fridgit.R;

/**
 * Created by admin on 9/22/15.
 */
public class DirectionsForRecipeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.directions_for_recipe_fragment, container, false);

        //TODO Connect a webView to show the recipe directions.
        return listFragment;
    }
}
