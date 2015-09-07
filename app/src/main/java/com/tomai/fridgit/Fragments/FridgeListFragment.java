package com.tomai.fridgit.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tomai.fridgit.Adapters.TodoAdapter;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.R;

import java.util.ArrayList;

/**
 * Created by admin on 9/4/15.
 */
public class FridgeListFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.fragment_list,container,false);
        //JUST A TEST! TODO add a check list to fragment.
        Item ham = new Item("Ham",6, Item.amounts.Amounts);

        ArrayList<Item> items = new ArrayList<>();
        items.add(ham);

        ListAdapter todoAdapter = new TodoAdapter(getContext(),items);

        ListView listView = (ListView)listFragment.findViewById(R.id.listView);
        listView.setAdapter(todoAdapter);
        //////////////
        return listFragment;
    }
}
