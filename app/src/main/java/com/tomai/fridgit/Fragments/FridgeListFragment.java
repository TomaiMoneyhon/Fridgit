package com.tomai.fridgit.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tomai.fridgit.Adapters.FridgeListAdapter;
import com.tomai.fridgit.Adapters.ShoppingListAdapter;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

/**
 * Created by admin on 9/4/15.
 */
public class FridgeListFragment extends Fragment{
    FridgeListAdapter todoAdapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.fragment_list,container,false);
        listFragment.setTag("fridge_frag");
        //JUST A TEST! TODO add a check list to fragment.
        todoAdapter = new FridgeListAdapter(getContext(), MainActivity.fridgeItems);

        listView = (ListView)listFragment.findViewById(R.id.listView);
        listView.setAdapter(todoAdapter);
        //////////////
        return listFragment;
    }

    public void refreshList () {
        todoAdapter = new FridgeListAdapter(getContext(), MainActivity.fridgeItems);
        listView.setAdapter(todoAdapter);
    }

}
