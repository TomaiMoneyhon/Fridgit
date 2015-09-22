package com.tomai.fridgit.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.tomai.fridgit.R;

/**
 * Created by admin on 9/22/15.
 */
public class IngredientsForRecipeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.ingredients_for_recipe_fragment, container, false);
        ListView ingredientsList = (ListView)listFragment.findViewById(R.id.ingredients_for_recipe_list);

        //TODO Create list of ingrigients needed (showing which you already have and which you don't) using a custom adapter

        Button changeServingsBTN = (Button)listFragment.findViewById(R.id.change_servings_btn);
        changeServingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create a custom dialog to change serving size of all ingredients.
            }
        });
        Button addToShoppingListBTN = (Button)listFragment.findViewById(R.id.add_to_shopping_list_btn);
        addToShoppingListBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Add all missing ingredients to shopping list.
            }
        });
//        ShoppingListAdapter todoAdapter = new ShoppingListAdapter(getContext(), MainActivity.shoppingItems);
//
//        final ListView listView = (ListView)listFragment.findViewById(R.id.listView);
//        listView.setAdapter(todoAdapter);

        return listFragment;
    }
}
