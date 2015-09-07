package com.tomai.fridgit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomai.fridgit.R;

/**
 * Created by admin on 9/4/15.
 */
public class ShoppingListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.fragment_list, container, false);
        //JUST A TEST! TODO add a check list to fragment.
//        TextView testText = (TextView) listFragment.findViewById(R.id.test_text);
//        testText.setText("Shopping List");
        //////////////
        return listFragment;
    }
}
