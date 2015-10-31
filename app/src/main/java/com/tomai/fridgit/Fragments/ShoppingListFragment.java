package com.tomai.fridgit.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tomai.fridgit.Adapters.ShoppingListAdapter;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

/**
 * Created by admin on 9/4/15.
 */
public class ShoppingListFragment extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.shopping_list_fragment, container, false);

        ShoppingListAdapter todoAdapter = new ShoppingListAdapter(getContext(), MainActivity.shoppingItems);

        listView = (ListView)listFragment.findViewById(R.id.listView);
        listView.setAdapter(todoAdapter);

        return listFragment;
    }
}
