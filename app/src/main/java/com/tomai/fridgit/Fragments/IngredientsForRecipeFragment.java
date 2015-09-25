package com.tomai.fridgit.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.tomai.fridgit.Adapters.IngredientsForRecipeListAdapter;
import com.tomai.fridgit.ChoosenRecipeActivity;
import com.tomai.fridgit.R;
import com.tomai.fridgit.RecipeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 9/22/15.
 */
public class IngredientsForRecipeFragment extends Fragment {
    private JSONArray ingredientsjsonArray;
    private ArrayList<JSONObject> ingredientsArrayList = new ArrayList<>();
    private JSONObject recipe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.ingredients_for_recipe_fragment, container, false);
        ListView ingredientsList = (ListView)listFragment.findViewById(R.id.ingredients_for_recipe_list);

        //TODO Create list of ingrigients needed (showing which you already have and which you don't) using a custom adapter

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    recipe =  new RecipeAPI(getContext()).getRecipe(ChoosenRecipeActivity.recipeID + "");
                    try {
                        ingredientsjsonArray = recipe.getJSONArray("extendedIngredients");
                        for (int i = 0 ; ingredientsjsonArray.length() > i ; i++) {
                            ingredientsArrayList.add((JSONObject)ingredientsjsonArray.get(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();


            IngredientsForRecipeListAdapter customAdapter = new IngredientsForRecipeListAdapter(getContext(),ingredientsArrayList);
            ingredientsList.setAdapter(customAdapter);
        }

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
